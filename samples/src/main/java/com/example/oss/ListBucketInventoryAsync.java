package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.concurrent.CompletableFuture;

public class ListBucketInventoryAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            ListBucketInventoryRequest request = ListBucketInventoryRequest.newBuilder()
                    .bucket(bucket)
                    .build();

            CompletableFuture<ListBucketInventoryResult> future = client.listBucketInventoryAsync(request);
            ListBucketInventoryResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

            ListInventoryConfigurationsResult listResult = result.listInventoryConfigurationsResult();
            if (listResult != null) {
                System.out.printf("Is Truncated: %s%n", listResult.isTruncated());
                System.out.printf("Next Continuation Token: %s%n", listResult.nextContinuationToken());
                
                if (listResult.inventoryConfigurations() != null) {
                    System.out.printf("Number of Inventory Configurations: %d%n", listResult.inventoryConfigurations().size());
                    
                    for (int i = 0; i < listResult.inventoryConfigurations().size(); i++) {
                        InventoryConfiguration config = listResult.inventoryConfigurations().get(i);
                        System.out.printf("Configuration %d:%n", i+1);
                        System.out.printf("  ID: %s%n", config.id());
                        System.out.printf("  Is Enabled: %s%n", config.isEnabled());
                        System.out.printf("  Included Object Versions: %s%n", config.includedObjectVersions());
                        
                        if (config.destination() != null && config.destination().oSSBucketDestination() != null) {
                            InventoryOSSBucketDestination dest = config.destination().oSSBucketDestination();
                            System.out.printf("  Format: %s%n", dest.format());
                            System.out.printf("  Account ID: %s%n", dest.accountId());
                            System.out.printf("  Role ARN: %s%n", dest.roleArn());
                            System.out.printf("  Bucket: %s%n", dest.bucket());
                            System.out.printf("  Prefix: %s%n", dest.prefix());
                            
                            if (dest.encryption() != null) {
                                if (dest.encryption().sseKms() != null) {
                                    System.out.printf("  SSE-KMS Key ID: %s%n", dest.encryption().sseKms().keyId());
                                }
                                if (dest.encryption().sseOss() != null) {
                                    System.out.printf("  SSE-OSS: %s%n", dest.encryption().sseOss());
                                }
                            }
                        }
                        
                        if (config.schedule() != null) {
                            System.out.printf("  Schedule Frequency: %s%n", config.schedule().frequency());
                        }
                        
                        if (config.filter() != null) {
                            System.out.printf("  Filter Prefix: %s%n", config.filter().prefix());
                        }
                        
                        if (config.optionalFields() != null && config.optionalFields().fields() != null) {
                            System.out.printf("  Optional Fields Count: %d%n", config.optionalFields().fields().size());
                            for (String field : config.optionalFields().fields()) {
                                System.out.printf("    Field: %s%n", field);
                            }
                        }
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            System.out.printf("error:%n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(String endpoint, String region, CredentialsProvider provider) {
        return OSSAsyncClient.newBuilder()
                .region(region)
                .endpoint(endpoint)
                .credentialsProvider(provider)
                .build();
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can access to.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);
    }
}