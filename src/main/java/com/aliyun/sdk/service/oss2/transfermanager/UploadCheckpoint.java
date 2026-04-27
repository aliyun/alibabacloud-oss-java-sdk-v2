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
 * Checkpoint for resumable upload. Tracks the uploadId so that a multipart upload
 * can be resumed after failure without re-uploading already completed parts.
 */
class UploadCheckpoint {

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
        @JsonProperty("FilePath")
        String filePath;
        @JsonProperty("FileMeta")
        FileMeta fileMeta = new FileMeta();
        @JsonProperty("ObjectInfo")
        ObjectInfo objectInfo = new ObjectInfo();
        @JsonProperty("PartSize")
        long partSize;
        @JsonProperty("UploadInfo")
        UploadInfo uploadInfo = new UploadInfo();
    }

    static class FileMeta {
        @JsonProperty("Size")
        long size;
        @JsonProperty("LastModified")
        String lastModified;
    }

    static class ObjectInfo {
        @JsonProperty("Name")
        String name;
    }

    static class UploadInfo {
        @JsonProperty("UploadId")
        String uploadId = "";
    }

    /**
     * Create a new upload checkpoint.
     */
    static UploadCheckpoint create(String bucket, String key, String filePath,
                                    String baseDir, long fileSize, String fileLastModified, long partSize) {
        String name = bucket + "/" + key;
        String destHash = md5Hex("oss://" + name);

        String absPath;
        try {
            absPath = Paths.get(filePath).toAbsolutePath().toString();
        } catch (Exception e) {
            absPath = filePath;
        }
        String srcHash = md5Hex(absPath);

        String dir;
        if (baseDir == null || baseDir.isEmpty()) {
            dir = System.getProperty("java.io.tmpdir");
        } else {
            File f = new File(baseDir);
            dir = f.isDirectory() ? baseDir : f.getParent();
            if (dir == null) dir = ".";
        }

        UploadCheckpoint cp = new UploadCheckpoint();
        cp.cpDirPath = dir;
        cp.cpFilePath = new File(dir, srcHash + "-" + destHash + Defaults.CHECKPOINT_FILE_SUFFIX_UPLOADER).getPath();

        cp.info.magic = Defaults.CHECKPOINT_MAGIC;
        cp.info.data.filePath = filePath;
        cp.info.data.fileMeta.size = fileSize;
        cp.info.data.fileMeta.lastModified = fileLastModified;
        cp.info.data.objectInfo.name = "oss://" + name;
        cp.info.data.partSize = partSize;

        return cp;
    }

    /**
     * Load checkpoint from disk. If valid, sets {@code loaded = true} and restores the uploadId.
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
            if (!strEquals(info.data.filePath, diskInfo.data.filePath)) return false;
            if (info.data.fileMeta.size != diskInfo.data.fileMeta.size) return false;
            if (!strEquals(info.data.fileMeta.lastModified, diskInfo.data.fileMeta.lastModified)) return false;
            if (info.data.partSize != diskInfo.data.partSize) return false;

            if (diskInfo.data.uploadInfo.uploadId == null || diskInfo.data.uploadInfo.uploadId.isEmpty()) {
                return false;
            }

            info.data.uploadInfo = diskInfo.data.uploadInfo;
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
