package com.aliyun.sdk.service.oss2.internal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class TestUtils {

    private TestUtils() {
    }

    private static void assertEqualContent(final File f0, final File f1)
            throws IOException {
        try (InputStream is0 = Files.newInputStream(f0.toPath())) {
            try (InputStream is1 = Files.newInputStream(f1.toPath())) {
                final byte[] buf0 = new byte[1024];
                final byte[] buf1 = new byte[1024];
                int n0 = 0;
                int n1;

                while (-1 != n0) {
                    n0 = is0.read(buf0);
                    n1 = is1.read(buf1);
                    assertEquals(n0, n1,
                            "The files " + f0 + " and " + f1 +
                                    " have differing number of bytes available (" + n0 + " vs " + n1 + ")");

                    assertArrayEquals(buf0, buf1, "The files " + f0 + " and " + f1 + " have different content");
                }
            }
        }
    }

    public static void checkFile(final File file, final File referenceFile)
            throws Exception {
        assertTrue(file.exists(), "Check existence of output file");
        assertEqualContent(referenceFile, file);
    }

    public static void createFile(final File file, final long size) throws IOException {
        if (!file.getParentFile().exists()) {
            throw new IOException("Cannot create file " + file + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
            generateTestData(output, size);
        }
    }

    public static void createFile(final Path file, final long size) throws IOException {
        if (!Files.exists(file.getParent())) {
            throw new IOException("Cannot create file " + file + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(file))) {
            generateTestData(output, size);
        }
    }

    public static void generateTestData(final File file, final long size) throws IOException {
        try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
            generateTestData(output, size);
        }
    }

    public static byte[] generateTestData(final long size) {
        try {
            try (ByteArrayOutputStream baout = new ByteArrayOutputStream()) {
                generateTestData(baout, size);
                return baout.toByteArray();
            }
        } catch (final IOException ioe) {
            throw new IllegalStateException("This should never happen: " + ioe.getMessage(), ioe);
        }
    }

    public static void generateTestData(final OutputStream out, final long size) throws IOException {
        for (int i = 0; i < size; i++) {
            // output.write((byte)'X');
            // nice varied byte pattern compatible with Readers and Writers
            out.write((byte) (i % 127 + 1));
        }
    }

    public static File newFile(final File testDirectory, final String fileName) throws IOException {
        final File destination = new File(testDirectory, fileName);
        if (destination.exists()) {
            deleteFile(destination);
        }
        return destination;
    }

    public static void deleteFile(final File file) {
        if (file.exists()) {
            assertTrue(file.delete(), "Couldn't delete file: " + file);
        }
    }
}
