package com.aliyun.sdk.service.oss2.transport;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.models.InventoryConfiguration;
import com.aliyun.sdk.service.oss2.models.InventoryDestination;
import com.aliyun.sdk.service.oss2.models.InventoryFilter;
import com.aliyun.sdk.service.oss2.models.InventorySchedule;
import com.aliyun.sdk.service.oss2.models.PutBucketInventoryRequest;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketInventory;
import com.aliyun.sdk.service.oss2.transport.apache4client.Apache4HttpClient;
import com.sun.net.httpserver.HttpServer;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Apache4HttpClientStringBodyTest {

    private static final String PREFIX = "\u4e2d\u56fd/";

    @Test
    public void sendsNonAsciiStringBodyAsUtf8WithMatchingContentMd5() throws Exception {
        OperationInput input = newPutBucketInventoryInput();

        AtomicReference<byte[]> receivedBody = new AtomicReference<>();
        AtomicReference<String> receivedContentMd5 = new AtomicReference<>();

        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            receivedContentMd5.set(exchange.getRequestHeaders().getFirst("Content-MD5"));
            receivedBody.set(readFully(exchange.getRequestBody()));
            byte[] response = "ok".getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        });
        server.start();

        try {
            int port = server.getAddress().getPort();
            Map<String, String> headers = input.headers();
            RequestMessage request = RequestMessage.newBuilder()
                    .method("PUT")
                    .uri("http://127.0.0.1:" + port + "/?inventory")
                    .headers(headers)
                    .body(input.body().get())
                    .build();

            try (Apache4HttpClient client = Apache4HttpClient.custom().build()) {
                ResponseMessage response = client.send(request, RequestContext.empty());
                assertEquals(200, response.statusCode());
            }
        } finally {
            server.stop(0);
        }

        byte[] body = receivedBody.get();
        assertNotNull(body);

        // the transport must send the String body as UTF-8, identical to what the SDK digested
        assertArrayEquals(input.body().get().toBytes(), body);
        assertTrue(new String(body, StandardCharsets.UTF_8).contains(PREFIX));

        // the server-side Content-MD5 check must pass on the received body
        assertEquals(input.headers().get("Content-MD5"), receivedContentMd5.get());
        assertEquals(receivedContentMd5.get(), md5AsBase64(body));
    }

    private static OperationInput newPutBucketInventoryInput() {
        InventoryConfiguration configuration = InventoryConfiguration.newBuilder()
                .id("test-inventory")
                .isEnabled(Boolean.TRUE)
                .schedule(InventorySchedule.newBuilder().frequency("Daily").build())
                .filter(InventoryFilter.newBuilder().prefix(PREFIX).build())
                .includedObjectVersions("All")
                .destination(InventoryDestination.newBuilder().build())
                .build();
        return SerdeBucketInventory.fromPutBucketInventory(PutBucketInventoryRequest.newBuilder()
                .bucket("test-bucket")
                .inventoryConfiguration(configuration)
                .build());
    }

    private static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toByteArray();
    }

    private static String md5AsBase64(byte[] data) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest(data));
    }
}
