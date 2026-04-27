package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.hash.CRC64;
import com.aliyun.sdk.service.oss2.io.RangeInputStream;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.progress.ProgressListener;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Downloader {
    private final OSSClient client;
    private final DownloaderOptions options;

    public Downloader(OSSClient client) {
        this(client, DownloaderOptions.defaults());
    }

    public Downloader(OSSClient client, DownloaderOptions options) {
        this.client = Objects.requireNonNull(client, "client must not be null");
        this.options = Objects.requireNonNull(options, "options must not be null");
    }

    public DownloaderOptions options() {
        return options;
    }

    public DownloadResult downloadFile(GetObjectRequest request, String filePath) throws DownloadError {
        return downloadFile(request, filePath, null);
    }

    public DownloadResult downloadFile(GetObjectRequest request, String filePath, DownloaderOptions overrideOptions) throws DownloadError {
        validateRequest(request);
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("filePath is required");
        }

        DownloaderDelegate delegate = new DownloaderDelegate(client, request, filePath, options, overrideOptions);
        delegate.checkSource();
        delegate.adjustRange();
        delegate.checkCheckpoint();
        delegate.updateCRCFlag();
        return delegate.download();
    }

    private static void validateRequest(GetObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("null field, request");
        }
        if (request.bucket() == null) {
            throw new IllegalArgumentException("null field, request.Bucket");
        }
        if (request.key() == null) {
            throw new IllegalArgumentException("null field, request.Key");
        }
    }

    // -------------------------------------------------------------------------
    // DownloaderDelegate — per-download state and logic
    // -------------------------------------------------------------------------

    private static class DownloaderDelegate {
        // Input
        private final OSSClient client;
        private final GetObjectRequest request;
        private final DownloaderOptions opts;
        private final String filePath;

        // Source info (from HeadObject)
        private long sizeInBytes;
        private String etag;
        private String serverCRC;
        private String lastModified;

        // Range
        private long rangeStart;
        private long rangeEnd;

        // Download state
        private String tempFilePath;
        private DownloadCheckpoint checkpoint;
        private long resumeOffset;
        private boolean calcCRC;
        private boolean checkCRC;

        // Progress
        private final ProgressListener progressListener;
        private final AtomicLong transferred = new AtomicLong(0);
        private long totalDownloadSize;

        // CRC tracking (used in parallel download)
        private final List<long[]> completedChunks = new ArrayList<>();
        private final AtomicLong combinedCRC = new AtomicLong(0);
        private final AtomicLong trackerOffset = new AtomicLong(0);

        DownloaderDelegate(OSSClient client, GetObjectRequest request, String filePath,
                           DownloaderOptions defaultOpts, DownloaderOptions overrideOptions) {
            this.client = client;
            this.request = request;
            this.filePath = filePath;
            this.opts = overrideOptions != null ? overrideOptions : defaultOpts;
            this.progressListener = request.progressListener();
        }

        /**
         * HeadObject to get source info: size, ETag, CRC.
         */
        void checkSource() throws DownloadError {
            try {
                HeadObjectRequest.Builder headBuilder = HeadObjectRequest.newBuilder()
                        .bucket(request.bucket())
                        .key(request.key());
                if (request.versionId() != null) {
                    headBuilder.versionId(request.versionId());
                }
                if (request.requestPayer() != null) {
                    headBuilder.requestPayer(request.requestPayer());
                }
                HeadObjectResult headResult = client.headObject(headBuilder.build());

                this.sizeInBytes = headResult.contentLength() != null ? headResult.contentLength() : 0;
                this.etag = headResult.eTag();
                this.serverCRC = headResult.hashCrc64ecma();
                this.lastModified = headResult.lastModified();
            } catch (Exception e) {
                throw new DownloadError(ossPath(), e);
            }
        }

        /**
         * Parse Range header and set rangeStart/rangeEnd.
         */
        void adjustRange() {
            this.rangeStart = 0;
            this.rangeEnd = sizeInBytes;
            String rangeHeader = request.range();
            if (rangeHeader != null && !rangeHeader.isEmpty()) {
                long[] range = parseRange(rangeHeader, sizeInBytes);
                this.rangeStart = range[0];
                this.rangeEnd = range[1];
            }
            this.totalDownloadSize = rangeEnd - rangeStart;
        }

        /**
         * Load or create checkpoint if enabled.
         */
        void checkCheckpoint() throws DownloadError {
            // Determine temp file path
            this.tempFilePath = filePath;
            if (opts.useTempFile()) {
                this.tempFilePath = filePath + Defaults.TEMP_FILE_SUFFIX;
            }

            if (opts.enableCheckpoint()) {
                this.checkpoint = DownloadCheckpoint.create(
                        request.bucket(), request.key(),
                        request.versionId(), request.range(),
                        tempFilePath, opts.checkpointDir(),
                        sizeInBytes,
                        lastModified,
                        etag,
                        opts.partSize());
                try {
                    checkpoint.load();
                } catch (IOException e) {
                    throw new DownloadError(ossPath(), e);
                }
                if (checkpoint.loaded) {
                    this.resumeOffset = checkpoint.info.data.downloadInfo.offset;
                } else {
                    checkpoint.info.data.downloadInfo.offset = rangeStart;
                }
            }
        }

        /**
         * Set calcCRC/checkCRC flags.
         */
        void updateCRCFlag() {
            this.calcCRC = opts.enableCRC64Check();
            this.checkCRC = calcCRC && (request.range() == null || request.range().isEmpty());
        }

        /**
         * Execute the download: create file, write chunks, verify CRC, cleanup.
         */
        DownloadResult download() throws DownloadError {
            if (totalDownloadSize <= 0) {
                return handleEmptyFile();
            }

            FileChannel channel = null;
            try {
                channel = FileChannel.open(
                        new File(tempFilePath).toPath(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE);

                long effectiveStart = rangeStart;
                if (resumeOffset > 0) {
                    effectiveStart = resumeOffset;
                } else {
                    channel.truncate(totalDownloadSize);
                }

                long effectiveDownloadSize = rangeEnd - effectiveStart;
                long previouslyWritten = resumeOffset > 0 ? resumeOffset - rangeStart : 0;

                // Report resumed progress
                if (progressListener != null && previouslyWritten > 0) {
                    transferred.set(previouslyWritten);
                    progressListener.onProgress(previouslyWritten, previouslyWritten, totalDownloadSize);
                }

                long resumeCRC64 = 0;
                if (checkpoint != null && checkpoint.loaded) {
                    resumeCRC64 = checkpoint.info.data.downloadInfo.crc64;
                }
                combinedCRC.set(resumeCRC64);
                trackerOffset.set(checkpoint != null ? checkpoint.info.data.downloadInfo.offset : effectiveStart);

                if (effectiveDownloadSize <= opts.partSize() || opts.parallelNum() <= 1) {
                    // Single-threaded download
                    ChunkResult result = downloadChunk(channel, effectiveStart, rangeEnd - effectiveStart);
                    channel.close();
                    channel = null;

                    if (checkCRC && serverCRC != null) {
                        long finalCRC = CRC64.combine(resumeCRC64, result.crc64, result.written);
                        verifyCRC64(finalCRC);
                    }

                    finishSuccess();
                    return new DownloadResult(previouslyWritten + result.written);
                }

                // Parallel download (closes channel internally)
                FileChannel ch = channel;
                channel = null;
                return parallelDownload(ch, effectiveStart, previouslyWritten);
            } catch (DownloadError e) {
                cleanupOnError();
                throw e;
            } catch (Exception e) {
                cleanupOnError();
                throw new DownloadError(filePath, e);
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        private DownloadResult handleEmptyFile() throws DownloadError {
            try {
                new FileOutputStream(filePath).close();
            } catch (IOException e) {
                throw new DownloadError(filePath, e);
            }
            if (progressListener != null) {
                progressListener.onFinish();
            }
            return new DownloadResult(0);
        }

        /**
         * Downloads a chunk using RangeInputStream with automatic reconnection.
         * Writes to FileChannel via position-based write (pwrite), which is thread-safe.
         */
        private ChunkResult downloadChunk(FileChannel channel, long chunkStart, long chunkSize) throws DownloadError {
            long fileOffset = chunkStart - rangeStart;
            int writeBufferSize = opts.writeBufferSize();

            RangeInputStream.RangeGetFunction getFn = (offset, count) -> {
                String rangeStr = count > 0
                        ? "bytes=" + offset + "-" + (offset + count - 1)
                        : "bytes=" + offset + "-";

                GetObjectRequest req = request.toBuilder()
                        .range(rangeStr)
                        .rangeBehavior("standard")
                        .progressListener(null)
                        .build();

                GetObjectResult result;
                try {
                    result = client.getObject(req);
                } catch (Exception e) {
                    throw new IOException("GetObject failed", e);
                }

                return new RangeInputStream.RangeGetOutput(
                        result.body(),
                        result.contentLength() != null ? result.contentLength() : 0,
                        result.contentRange(),
                        result.eTag()
                );
            };

            CRC64 hash = calcCRC ? new CRC64(0) : null;

            try (RangeInputStream ris = new RangeInputStream(getFn, chunkStart, chunkSize, etag)) {
                int bufSize = writeBufferSize > 0 ? writeBufferSize : 8192;
                ByteBuffer buf = ByteBuffer.allocate(bufSize);
                long totalWritten = 0;

                while (true) {
                    int read = ris.read(buf.array(), buf.position(), buf.remaining());
                    if (read == -1) {
                        break;
                    }
                    if (read <= 0) {
                        continue;
                    }

                    if (hash != null) {
                        hash.update(buf.array(), buf.position(), read);
                    }

                    buf.position(buf.position() + read);

                    if (!buf.hasRemaining() || writeBufferSize <= 0) {
                        buf.flip();
                        long pos = fileOffset + totalWritten;
                        int written = writeAll(channel, buf, pos);
                        totalWritten += written;
                        buf.clear();

                        if (progressListener != null) {
                            long total = transferred.addAndGet(written);
                            progressListener.onProgress(written, total, totalDownloadSize);
                        }
                    }
                }

                // Flush remaining buffered data
                if (buf.position() > 0) {
                    buf.flip();
                    long pos = fileOffset + totalWritten;
                    int written = writeAll(channel, buf, pos);
                    totalWritten += written;

                    if (progressListener != null) {
                        long total = transferred.addAndGet(written);
                        progressListener.onProgress(written, total, totalDownloadSize);
                    }
                }

                long crc64 = hash != null ? hash.getValue() : 0;
                return new ChunkResult(totalWritten, crc64);
            } catch (IOException e) {
                throw new DownloadError(ossPath(), e);
            }
        }

        private DownloadResult parallelDownload(FileChannel channel, long effectiveStart,
                                                 long previouslyWritten) throws DownloadError {
            AtomicReference<Exception> firstError = new AtomicReference<>();
            AtomicLong totalWritten = new AtomicLong(0);

            ExecutorService executor = Executors.newFixedThreadPool(opts.parallelNum());
            List<Future<?>> futures = new ArrayList<>();

            try {
                long pos = effectiveStart;
                while (pos < rangeEnd && firstError.get() == null) {
                    long chunkEnd = Math.min(pos + opts.partSize(), rangeEnd);
                    final long chunkStart = pos;
                    final long chunkSize = chunkEnd - chunkStart;

                    futures.add(executor.submit(() -> {
                        if (firstError.get() != null) return;
                        try {
                            ChunkResult result = downloadChunk(channel, chunkStart, chunkSize);
                            totalWritten.addAndGet(result.written);

                            if (checkpoint != null || calcCRC) {
                                updateCheckpoint(chunkStart, result.written, result.crc64);
                            }
                        } catch (Exception e) {
                            firstError.compareAndSet(null, e);
                        }
                    }));

                    pos = chunkEnd;
                }

                for (Future<?> future : futures) {
                    if (firstError.get() != null) {
                        future.cancel(false);
                        continue;
                    }
                    try {
                        future.get();
                    } catch (CancellationException ignored) {
                    } catch (InterruptedException | ExecutionException e) {
                        if (firstError.get() == null) {
                            firstError.set(e.getCause() != null ? (Exception) e.getCause() : e);
                        }
                    }
                }
            } finally {
                executor.shutdown();
            }

            try {
                channel.close();
            } catch (IOException ignored) {
            }

            if (firstError.get() != null) {
                cleanupOnError();
                if (firstError.get() instanceof DownloadError) {
                    throw (DownloadError) firstError.get();
                }
                throw new DownloadError(filePath, firstError.get());
            }

            if (checkCRC && serverCRC != null) {
                verifyCRC64(combinedCRC.get());
            }

            finishSuccess();
            return new DownloadResult(previouslyWritten + totalWritten.get());
        }

        /**
         * Merges completed chunk into contiguous offset tracking for checkpoint and CRC.
         */
        private void updateCheckpoint(long chunkStart, long chunkSize, long crc64) {
            synchronized (completedChunks) {
                completedChunks.add(new long[]{chunkStart, chunkSize, crc64});
                completedChunks.sort((a, b) -> Long.compare(a[0], b[0]));

                long contiguousOffset = trackerOffset.get();

                int merged = 0;
                long mergedCRC = combinedCRC.get();
                for (long[] chunk : completedChunks) {
                    if (chunk[0] == contiguousOffset) {
                        contiguousOffset += chunk[1];
                        if (calcCRC) {
                            mergedCRC = CRC64.combine(mergedCRC, chunk[2], chunk[1]);
                        }
                        merged++;
                    } else {
                        break;
                    }
                }

                if (merged > 0) {
                    completedChunks.subList(0, merged).clear();
                    trackerOffset.set(contiguousOffset);
                    if (calcCRC) {
                        combinedCRC.set(mergedCRC);
                    }
                    if (checkpoint != null) {
                        checkpoint.info.data.downloadInfo.offset = contiguousOffset;
                        if (calcCRC) {
                            checkpoint.info.data.downloadInfo.crc64 = mergedCRC;
                        }
                        try {
                            checkpoint.dump();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        }

        private void verifyCRC64(long clientCRC) throws DownloadError {
            String clientCRCStr = Long.toUnsignedString(clientCRC);
            if (!clientCRCStr.equals(serverCRC)) {
                throw new DownloadError(ossPath(),
                        new IOException("CRC64 mismatch, client: " + clientCRCStr + ", server: " + serverCRC));
            }
        }

        /**
         * Common success cleanup: remove checkpoint, rename temp file, notify progress finish.
         */
        private void finishSuccess() throws DownloadError {
            if (checkpoint != null) {
                checkpoint.remove();
            }
            renameTempFile();
            if (progressListener != null) {
                progressListener.onFinish();
            }
        }

        /**
         * Cleanup temp file on error (only when checkpoint is not enabled).
         */
        private void cleanupOnError() {
            if (checkpoint == null && opts.useTempFile() && !tempFilePath.equals(filePath)) {
                new File(tempFilePath).delete();
            }
        }

        private void renameTempFile() throws DownloadError {
            if (opts.useTempFile() && !tempFilePath.equals(filePath)) {
                File tempFile = new File(tempFilePath);
                File destFile = new File(filePath);
                if (destFile.exists()) {
                    destFile.delete();
                }
                if (!tempFile.renameTo(destFile)) {
                    throw new DownloadError(filePath,
                            new IOException("Failed to rename temp file " + tempFilePath + " to " + filePath));
                }
            }
        }

        private String ossPath() {
            return "oss://" + request.bucket() + "/" + request.key();
        }

        private static long[] parseRange(String rangeStr, long totalSize) {
            long start = 0;
            long end = totalSize;
            if (rangeStr.startsWith("bytes=")) {
                String range = rangeStr.substring(6);
                String[] parts = range.split("-", 2);
                if (parts.length == 2) {
                    if (!parts[0].isEmpty()) {
                        start = Long.parseLong(parts[0]);
                    }
                    if (!parts[1].isEmpty()) {
                        end = Math.min(Long.parseLong(parts[1]) + 1, totalSize);
                    }
                }
            }
            return new long[]{start, end};
        }

        private static int writeAll(FileChannel channel, ByteBuffer buf, long position) throws IOException {
            int totalWritten = 0;
            while (buf.hasRemaining()) {
                int n = channel.write(buf, position + totalWritten);
                totalWritten += n;
            }
            return totalWritten;
        }

        private static class ChunkResult {
            final long written;
            final long crc64;

            ChunkResult(long written, long crc64) {
                this.written = written;
                this.crc64 = crc64;
            }
        }
    }
}
