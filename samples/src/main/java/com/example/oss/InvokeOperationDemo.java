package com.example.oss;

import com.aliyun.sdk.service.oss2.*;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.Base64Utils;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class InvokeOperationDemo implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {

            String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<Tagging>\n" +
                    "  <TagSet>\n" +
                    "    <Tag>\n" +
                    "      <Key>testa</Key>\n" +
                    "      <Value>value1-test</Value>\n" +
                    "    </Tag>\n" +
                    "    <Tag>\n" +
                    "      <Key>testb</Key>\n" +
                    "      <Value>value2-test</Value>\n" +
                    "    </Tag>\n" +
                    "  </TagSet>\n" +
                    "</Tagging>";

            String contentMD5 = calculateContentMD5(xmlData);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/xml");
            headers.put("Content-MD5", contentMD5);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("tagging", "");

            OperationInput putOperationInput = OperationInput.newBuilder()
                    .opName("PutObjectTags")
                    .method("PUT")
                    .bucket(bucket)
                    .key(key)
                    .headers(headers)
                    .parameters(parameters)
                    .body(BinaryData.fromString(xmlData))
                    .build();

            OperationOutput putOperationOutput = client.invokeOperation(putOperationInput, OperationOptions.defaults());

            System.out.println("PutObjectTagging status code: " + putOperationOutput.statusCode() +
                    ", request id: " + putOperationOutput.headers().get("x-oss-request-id"));

            Map<String, String> getParameters = new HashMap<>();
            getParameters.put("tagging", "");

            OperationInput getOperationInput = OperationInput.newBuilder()
                    .opName("GetObjectTags")
                    .method("GET")
                    .bucket(bucket)
                    .key(key)
                    .parameters(getParameters)
                    .build();

            OperationOutput getOperationOutput = client.invokeOperation(getOperationInput, OperationOptions.defaults());

            System.out.println("GetObjectTagging status code: " + getOperationOutput.statusCode() +
                    ", request id: " + getOperationOutput.headers().get("x-oss-request-id") +
                    ", content: " + new String(getOperationOutput.body().get().toBytes(), StandardCharsets.UTF_8));

            Map<String, String> deleteParameters = new HashMap<>();
            deleteParameters.put("tagging", "");

            OperationInput deleteOperationInput = OperationInput.newBuilder()
                    .opName("DeleteObjectTags")
                    .method("DELETE")
                    .bucket(bucket)
                    .key(key)
                    .parameters(deleteParameters)
                    .build();

            OperationOutput deleteOperationOutput = client.invokeOperation(deleteOperationInput, OperationOptions.defaults());

            System.out.println("DeleteObjectTagging status code: " + deleteOperationOutput.statusCode() +
                    ", request id: " + deleteOperationOutput.headers().get("x-oss-request-id"));

            System.out.printf("InvokeOperationDemo has been executed successfully.\n");

        } catch (Exception e) {
            System.out.printf("error:\n%s", e);
        }
    }

    private static String calculateContentMD5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(content.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to calculate MD5", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        execute(endpoint, region, bucket, key);
    }
}
