package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PostObjectCallbackV4 implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String localFilePath,
            String callbackUrl,
            String callbackHost) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            String accessKeyId = provider.getCredentials().accessKeyId();
            String accessKeySecret = provider.getCredentials().accessKeySecret();

            String urlStr = endpoint.replace("http://", "http://" + bucket + ".").replace("https://", "https://" + bucket + ".");

            Map<String, String> formFields = new LinkedHashMap<>();
            formFields.put("key", key);

            String date = Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String dateTime = Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

            String policy = "{\n" +
                    "  \"expiration\": \"2120-01-01T12:00:00.000Z\",\n" +
                    "  \"conditions\": [\n" +
                    "    {\"x-oss-signature-version\": \"OSS4-HMAC-SHA256\"},\n" +
                    "    {\"x-oss-credential\": \"" + accessKeyId + "/" + date + "/" + region + "/oss/aliyun_v4_request\"},\n" +
                    "    {\"x-oss-date\": \"" + dateTime + "\"},\n" +
                    "    [\"content-length-range\", 0, 104857600]\n" +
                    "  ]\n" +
                    "}";

            String encodePolicy = Base64Utils.encodeToString(policy.getBytes(StandardCharsets.UTF_8));
            formFields.put("policy", encodePolicy);

            String callback = "{\n" +
                    "  \"callbackUrl\": \"" + callbackUrl + "\",\n" +
                    "  \"callbackHost\": \"" + callbackHost + "\",\n" +
                    "  \"callbackBody\": \"{\\\"bucket\\\":${bucket},\\\"object\\\":${object},\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},\\\"my_var1\\\":${x:var1},\\\"my_var2\\\":${x:var2}}\",\n" +
                    "  \"callbackBodyType\": \"application/json\"\n" +
                    "}";

            String encodeCallback = Base64Utils.encodeToString(callback.getBytes(StandardCharsets.UTF_8));
            formFields.put("callback", encodeCallback);

            formFields.put("x:var1", "value1");
            formFields.put("x:var2", "value2");

            formFields.put("x-oss-signature-version", "OSS4-HMAC-SHA256");
            formFields.put("x-oss-credential", accessKeyId + "/" + date + "/" + region + "/oss/aliyun_v4_request");
            formFields.put("x-oss-date", dateTime);

            String signature = calculateSignature(accessKeySecret, date, region, encodePolicy);
            formFields.put("x-oss-signature", signature);

            String result = formUpload(urlStr, formFields, localFilePath);

            System.out.println("Post Object [" + key + "] to bucket [" + bucket + "]");
            System.out.println("post response:" + result);
            System.out.println("PostObjectCallbackV4 has been executed successfully.\n");

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    private static String calculateSignature(String accessKeySecret, String date, String region, String encodePolicy) {
        try {
            byte[] key = ("aliyun_v4" + accessKeySecret).getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key, "HmacSHA256"));
            byte[] dateKey = mac.doFinal(date.getBytes(StandardCharsets.UTF_8));

            mac.init(new SecretKeySpec(dateKey, "HmacSHA256"));
            byte[] regionKey = mac.doFinal(region.getBytes(StandardCharsets.UTF_8));

            mac.init(new SecretKeySpec(regionKey, "HmacSHA256"));
            byte[] serviceKey = mac.doFinal("oss".getBytes(StandardCharsets.UTF_8));

            mac.init(new SecretKeySpec(serviceKey, "HmacSHA256"));
            byte[] signingKey = mac.doFinal("aliyun_v4_request".getBytes(StandardCharsets.UTF_8));

            mac.init(new SecretKeySpec(signingKey, "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(encodePolicy.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : signatureBytes) {
                sb.append(String.format("%02x", b & 0xFF));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to calculate signature", e);
        }
    }

    private static String formUpload(String urlStr, Map<String, String> formFields, String localFile) throws Exception {
        String res = "";
        HttpURLConnection conn = null;
        String boundary = "9431149156168";

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            if (formFields != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = formFields.entrySet().iterator();
                int i = 0;

                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = entry.getKey();
                    String inputValue = entry.getValue();

                    if (inputValue == null) {
                        continue;
                    }

                    if (i == 0) {
                        strBuf.append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"")
                                .append(inputName).append("\"\r\n\r\n");
                        strBuf.append(inputValue);
                    } else {
                        strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\"")
                                .append(inputName).append("\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    i++;
                }
                out.write(strBuf.toString().getBytes());
            }

            File file = new File(localFile);
            String filename = file.getName();
            String contentType = "application/octet-stream";

            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(boundary)
                    .append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"file\"; ")
                    .append("filename=\"").append(filename).append("\"\r\n");
            strBuf.append("Content-Type: ").append(contentType).append("\r\n\r\n");
            out.write(strBuf.toString().getBytes());

            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];

            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }

            in.close();
            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;

            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }

            res = strBuf.toString();
            reader.close();
            reader = null;
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }

        return res;
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("localFilePath").desc("The path of the local file to upload.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("callbackUrl").desc("The callback server url.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("callbackHost").desc("The callback server host.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String localFilePath = cmd.getParsedOptionValue("localFilePath");
        String callbackUrl = cmd.getParsedOptionValue("callbackUrl");
        String callbackHost = cmd.getParsedOptionValue("callbackHost");
        execute(endpoint, region, bucket, key, localFilePath, callbackUrl, callbackHost);
    }
}
