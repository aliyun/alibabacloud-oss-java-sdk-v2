package com.aliyun.sdk.service.oss2.transfermanager;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.*;

import java.io.*;
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

        DownloaderOptions opts = overrideOptions != null ? overrideOptions : this.options;

        // HeadObject to get source info
        HeadObjectResult headResult;
        try {
            HeadObjectRequest.Builder headBuilder = HeadObjectRequest.newBuilder()
                    .bucket(request.bucket())
                    .key(request.key());
            if (request.versionId() != null) {
                headBuilder.versionId(request.versionId());
            }
            HeadObjectRequest headRequest = headBuilder.build();
            headResult = client.headObject(headRequest);
        } catch (Exception e) {
            throw new DownloadError("oss://" + request.bucket() + "/" + request.key(), e);
        }

        long sizeInBytes = headResult.contentLength() != null ? headResult.contentLength() : 0;

        // Handle range
        long rangeStart = 0;
        long rangeEnd = sizeInBytes;
        String rangeHeader = request.range();
        if (rangeHeader != null && !rangeHeader.isEmpty()) {
            long[] range = parseRange(rangeHeader, sizeInBytes);
            rangeStart = range[0];
            rangeEnd = range[1];
        }

        long totalDownloadSize = rangeEnd - rangeStart;
        if (totalDownloadSize <= 0) {
            // Empty file
            try {
                new FileOutputStream(filePath).close();
            } catch (IOException e) {
                throw new DownloadError(filePath, e);
            }
            return new DownloadResult(0);
        }

        // Determine temp file path
        String tempFilePath = filePath;
        if (opts.useTempFile()) {
            tempFilePath = filePath + Defaults.TEMP_FILE_SUFFIX;
        }

        long partSize = opts.partSize();
        int parallelNum = opts.parallelNum();

        // Checkpoint support
        DownloadCheckpoint checkpoint = null;
        long resumeOffset = 0;
        if (opts.enableCheckpoint()) {
            checkpoint = DownloadCheckpoint.create(
                    request.bucket(), request.key(),
                    request.versionId(), request.range(),
                    tempFilePath, opts.checkpointDir(),
                    sizeInBytes,
                    headResult.lastModified(),
                    headResult.eTag(),
                    partSize);
            try {
                checkpoint.load();
            } catch (IOException e) {
                throw new DownloadError("oss://" + request.bucket() + "/" + request.key(), e);
            }
            if (checkpoint.loaded) {
                resumeOffset = checkpoint.info.data.downloadInfo.offset;
            } else {
                checkpoint.info.data.downloadInfo.offset = rangeStart;
            }
        }

        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(tempFilePath, "rw");
            // Only truncate if not resuming or if file is too large
            if (resumeOffset == 0) {
                raf.setLength(totalDownloadSize);
            }

            long effectiveStart = rangeStart;
            if (resumeOffset > 0) {
                effectiveStart = resumeOffset;
            }

            long effectiveDownloadSize = rangeEnd - effectiveStart;

            if (effectiveDownloadSize <= partSize || parallelNum <= 1) {
                // Single-threaded download
                long fileOffset = effectiveStart - rangeStart;
                long written = downloadRange(request, raf, effectiveStart, rangeEnd, fileOffset);
                raf.close();
                raf = null;

                // Remove checkpoint on success
                if (checkpoint != null) {
                    checkpoint.remove();
                }

                // Rename temp file
                renameTempFile(tempFilePath, filePath, opts.useTempFile());

                long totalWritten = (resumeOffset > 0 ? resumeOffset - rangeStart : 0) + written;
                return new DownloadResult(totalWritten);
            }

            // Parallel download
            return parallelDownload(request, raf, effectiveStart, rangeEnd, rangeStart,
                    partSize, parallelNum, tempFilePath, filePath, opts.useTempFile(),
                    checkpoint, resumeOffset);
        } catch (DownloadError e) {
            throw e;
        } catch (Exception e) {
            if (checkpoint == null) {
                cleanupTempFile(tempFilePath, filePath, opts.useTempFile());
            }
            throw new DownloadError(filePath, e);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private void validateRequest(GetObjectRequest request) {
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

    private long[] parseRange(String rangeStr, long totalSize) {
        // Parse "bytes=start-end" format
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

    private long downloadRange(GetObjectRequest originalRequest, RandomAccessFile raf,
                                long rangeStart, long rangeEnd, long fileOffset) throws DownloadError {
        String rangeStr = "bytes=" + rangeStart + "-" + (rangeEnd - 1);

        GetObjectRequest.Builder builder = GetObjectRequest.newBuilder()
                .bucket(originalRequest.bucket())
                .key(originalRequest.key())
                .range(rangeStr)
                .rangeBehavior("standard");
        if (originalRequest.versionId() != null) {
            builder.versionId(originalRequest.versionId());
        }

        GetObjectResult result = null;
        try {
            result = client.getObject(builder.build());
            InputStream body = result.body();
            if (body == null) {
                return 0;
            }

            byte[] buffer = new byte[8192];
            long totalWritten = 0;
            int read;
            while ((read = body.read(buffer)) != -1) {
                synchronized (raf) {
                    raf.seek(fileOffset + totalWritten);
                    raf.write(buffer, 0, read);
                }
                totalWritten += read;
            }
            return totalWritten;
        } catch (Exception e) {
            throw new DownloadError("oss://" + originalRequest.bucket() + "/" + originalRequest.key(), e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private DownloadResult parallelDownload(GetObjectRequest request, RandomAccessFile raf,
                                             long effectiveStart, long rangeEnd, long rangeStart,
                                             long partSize, int parallelNum,
                                             String tempFilePath, String filePath, boolean useTempFile,
                                             DownloadCheckpoint checkpoint, long resumeOffset) throws DownloadError {
        AtomicReference<Exception> firstError = new AtomicReference<>();
        AtomicLong totalWritten = new AtomicLong(0);

        // Track completed chunks for checkpoint updates
        final DownloadCheckpoint cp = checkpoint;

        ExecutorService executor = Executors.newFixedThreadPool(parallelNum);
        List<Future<?>> futures = new ArrayList<>();

        try {
            long pos = effectiveStart;
            while (pos < rangeEnd && firstError.get() == null) {
                long chunkEnd = Math.min(pos + partSize, rangeEnd);
                final long chunkStart = pos;
                final long fileOffset = pos - rangeStart;

                futures.add(executor.submit(() -> {
                    if (firstError.get() != null) return;
                    try {
                        long written = downloadRange(request, raf, chunkStart, chunkEnd, fileOffset);
                        totalWritten.addAndGet(written);

                        // Update checkpoint offset
                        if (cp != null) {
                            synchronized (cp) {
                                long newOffset = chunkEnd;
                                if (newOffset > cp.info.data.downloadInfo.offset) {
                                    cp.info.data.downloadInfo.offset = newOffset;
                                    try {
                                        cp.dump();
                                    } catch (IOException ignored) {
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        firstError.compareAndSet(null, e);
                    }
                }));

                pos = chunkEnd;
            }

            // Wait for all parts to complete
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    if (firstError.get() == null) {
                        firstError.set(e.getCause() != null ? (Exception) e.getCause() : e);
                    }
                }
            }
        } finally {
            executor.shutdown();
        }

        // Close RAF before rename
        try {
            raf.close();
        } catch (IOException ignored) {
        }

        if (firstError.get() != null) {
            if (checkpoint == null) {
                cleanupTempFile(tempFilePath, filePath, useTempFile);
            }
            if (firstError.get() instanceof DownloadError) {
                throw (DownloadError) firstError.get();
            }
            throw new DownloadError(filePath, firstError.get());
        }

        // Remove checkpoint on success
        if (checkpoint != null) {
            checkpoint.remove();
        }

        // Rename temp file
        renameTempFile(tempFilePath, filePath, useTempFile);

        long previouslyWritten = resumeOffset > 0 ? resumeOffset - rangeStart : 0;
        return new DownloadResult(previouslyWritten + totalWritten.get());
    }

    private void renameTempFile(String tempFilePath, String filePath, boolean useTempFile) throws DownloadError {
        if (useTempFile && !tempFilePath.equals(filePath)) {
            File tempFile = new File(tempFilePath);
            File destFile = new File(filePath);
            if (destFile.exists()) {
                destFile.delete();
            }
            if (!tempFile.renameTo(destFile)) {
                throw new DownloadError(filePath, new IOException("Failed to rename temp file " + tempFilePath + " to " + filePath));
            }
        }
    }

    private void cleanupTempFile(String tempFilePath, String filePath, boolean useTempFile) {
        if (useTempFile && !tempFilePath.equals(filePath)) {
            new File(tempFilePath).delete();
        }
    }
}
