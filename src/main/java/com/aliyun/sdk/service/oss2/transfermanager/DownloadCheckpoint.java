package com.aliyun.sdk.service.oss2.transfermanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Checkpoint for resumable download. Tracks the download offset so that a
 * parallel download can be resumed after failure without re-downloading
 * already completed ranges.
 */
class DownloadCheckpoint {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    String cpDirPath;
    String cpFilePath;
    boolean loaded;

    final Info info = new Info();

    static class Info {
        @JsonProperty("Magic")
        String magic;
        @JsonProperty("MD5")
        String md5;
        @JsonProperty("Data")
        Data data = new Data();
    }

    static class Data {
        @JsonProperty("ObjectInfo")
        ObjectInfo objectInfo = new ObjectInfo();
        @JsonProperty("ObjectMeta")
        ObjectMeta objectMeta = new ObjectMeta();
        @JsonProperty("FilePath")
        String filePath;
        @JsonProperty("PartSize")
        long partSize;
        @JsonProperty("DownloadInfo")
        DownloadInfo downloadInfo = new DownloadInfo();
    }

    static class ObjectInfo {
        @JsonProperty("Name")
        String name;
        @JsonProperty("VersionId")
        String versionId = "";
        @JsonProperty("Range")
        String range = "";
    }

    static class ObjectMeta {
        @JsonProperty("Size")
        long size;
        @JsonProperty("LastModified")
        String lastModified;
        @JsonProperty("ETag")
        String eTag;
    }

    static class DownloadInfo {
        @JsonProperty("Offset")
        long offset;
        @JsonProperty("CRC64")
        long crc64;
    }

    /**
     * Create a new download checkpoint.
     */
    static DownloadCheckpoint create(String bucket, String key, String versionId, String range,
                                      String filePath, String baseDir,
                                      long contentLength, String lastModified, String eTag,
                                      long partSize) {
        StringBuilder buf = new StringBuilder();
        buf.append("oss://").append(bucket).append("/").append(key);
        buf.append("\n").append(versionId != null ? versionId : "");
        buf.append("\n").append(range != null ? range : "");
        String srcHash = md5Hex(buf.toString());

        String absPath;
        try {
            absPath = Paths.get(filePath).toAbsolutePath().toString();
        } catch (Exception e) {
            absPath = filePath;
        }
        String destHash = md5Hex(absPath);

        String dir;
        if (baseDir == null || baseDir.isEmpty()) {
            dir = System.getProperty("java.io.tmpdir");
        } else {
            File f = new File(baseDir);
            dir = f.isDirectory() ? baseDir : f.getParent();
            if (dir == null) dir = ".";
        }

        DownloadCheckpoint cp = new DownloadCheckpoint();
        cp.cpDirPath = dir;
        cp.cpFilePath = new File(dir, srcHash + "-" + destHash + Defaults.CHECKPOINT_FILE_SUFFIX_DOWNLOADER).getPath();

        cp.info.magic = Defaults.CHECKPOINT_MAGIC;
        cp.info.data.objectInfo.name = "oss://" + bucket + "/" + key;
        cp.info.data.objectInfo.versionId = versionId != null ? versionId : "";
        cp.info.data.objectInfo.range = range != null ? range : "";
        cp.info.data.objectMeta.size = contentLength;
        cp.info.data.objectMeta.lastModified = lastModified != null ? lastModified : "";
        cp.info.data.objectMeta.eTag = eTag != null ? eTag : "";
        cp.info.data.filePath = filePath;
        cp.info.data.partSize = partSize;

        return cp;
    }

    /**
     * Load checkpoint from disk. If valid, sets {@code loaded = true} and restores the offset.
     */
    boolean load() throws IOException {
        File dir = new File(cpDirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Invalid checkpoint dir, " + cpDirPath);
        }

        File file = new File(cpFilePath);
        if (!file.exists()) {
            return false;
        }

        if (!valid()) {
            remove();
            return false;
        }

        loaded = true;
        return true;
    }

    boolean valid() {
        try {
            byte[] contents = Files.readAllBytes(Paths.get(cpFilePath));
            Info diskInfo = MAPPER.readValue(contents, Info.class);
            if (diskInfo == null) return false;

            if (!Defaults.CHECKPOINT_MAGIC.equals(diskInfo.magic)) return false;

            String dataMd5 = md5Hex(MAPPER.writeValueAsString(diskInfo.data));
            if (!dataMd5.equals(diskInfo.md5)) return false;

            if (!strEquals(info.data.objectInfo.name, diskInfo.data.objectInfo.name)) return false;
            if (!strEquals(info.data.objectInfo.versionId, diskInfo.data.objectInfo.versionId)) return false;
            if (!strEquals(info.data.objectInfo.range, diskInfo.data.objectInfo.range)) return false;

            if (info.data.objectMeta.size != diskInfo.data.objectMeta.size) return false;
            if (!strEquals(info.data.objectMeta.lastModified, diskInfo.data.objectMeta.lastModified)) return false;
            if (!strEquals(info.data.objectMeta.eTag, diskInfo.data.objectMeta.eTag)) return false;

            if (!strEquals(info.data.filePath, diskInfo.data.filePath)) return false;
            if (info.data.partSize != diskInfo.data.partSize) return false;

            if (diskInfo.data.downloadInfo.offset < 0) return false;
            if (diskInfo.data.downloadInfo.offset == 0 && diskInfo.data.downloadInfo.crc64 != 0) return false;

            long rangeOffset = 0;
            if (info.data.objectInfo.range != null && !info.data.objectInfo.range.isEmpty()) {
                rangeOffset = parseRangeOffset(info.data.objectInfo.range);
            }
            if (diskInfo.data.downloadInfo.offset < rangeOffset) return false;
            long remains = (diskInfo.data.downloadInfo.offset - rangeOffset) % diskInfo.data.partSize;
            if (remains != 0) return false;

            info.data.downloadInfo = diskInfo.data.downloadInfo;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    void dump() throws IOException {
        String dataJson = MAPPER.writeValueAsString(info.data);
        info.md5 = md5Hex(dataJson);
        String json = MAPPER.writeValueAsString(info);
        Files.write(Paths.get(cpFilePath), json.getBytes(StandardCharsets.UTF_8));
    }

    void remove() {
        try {
            Files.deleteIfExists(Paths.get(cpFilePath));
        } catch (IOException ignored) {
        }
    }

    private static long parseRangeOffset(String range) {
        if (range != null && range.startsWith("bytes=")) {
            String r = range.substring(6);
            String[] parts = r.split("-", 2);
            if (parts.length > 0 && !parts[0].isEmpty()) {
                try {
                    return Long.parseLong(parts[0]);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

    private static boolean strEquals(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    static String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
