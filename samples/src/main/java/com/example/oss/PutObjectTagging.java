package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PutObjectTagging implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String tags,
            String versionId) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            
            // 解析标签参数
            List<Tag> tagList = new ArrayList<>();
            if (tags != null) {
                String[] tagPairs = tags.split(",");
                for (String tagPair : tagPairs) {
                    String[] parts = tagPair.split("=");
                    if (parts.length == 2) {
                        tagList.add(Tag.newBuilder()
                                .key(parts[0].trim())
                                .value(parts[1].trim())
                                .build());
                    }
                }
            }
            
            Tagging tagging = Tagging.newBuilder()
                    .tagSet(TagSet.newBuilder()
                            .tags(tagList)
                            .build())
                    .build();

            PutObjectTaggingRequest.Builder requestBuilder = PutObjectTaggingRequest.newBuilder()
                    .bucket(bucket)
                    .key(key)
                    .tagging(tagging);
            
            if (versionId != null) {
                requestBuilder.versionId(versionId);
            }

            PutObjectTaggingResult result = client.putObjectTagging(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            if (result.versionId() != null) {
                System.out.printf("Version ID: %s\n", result.versionId());
            }

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            //ServiceException se = ServiceException.asCause(e);
            //if (se != null) {
            //   System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tags").desc("The tags to be added to the object. Format: key1=value1,key2=value2.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("versionId").desc("The version id of the target object.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String key = cmd.getParsedOptionValue("key");
        String tags = cmd.getParsedOptionValue("tags");
        String versionId = cmd.getParsedOptionValue("versionId");
        execute(endpoint, region, bucket, key, tags, versionId);
    }
}
