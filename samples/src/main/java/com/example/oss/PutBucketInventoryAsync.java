package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PutBucketInventoryAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String inventoryId,
            String accountId,
            String roleArn,
            String destBucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            // Create bucket destination configuration
            InventoryOSSBucketDestination destination = InventoryOSSBucketDestination.newBuilder()
                    .format("CSV")
                    .accountId(accountId)
                    .roleArn(roleArn)
                    .bucket("acs:oss:::" + destBucket)
                    .prefix("prefix1/")
                    .build();

            // Create inventory destination
            InventoryDestination inventoryDestination = InventoryDestination.newBuilder()
                    .oSSBucketDestination(destination)
                    .build();

            // Create schedule configuration
            InventorySchedule schedule = InventorySchedule.newBuilder()
                    .frequency("Daily")
                    .build();

            // Create filter configuration
            InventoryFilter filter = InventoryFilter.newBuilder()
                    .prefix("Pics/")
                    .build();

            // Create optional fields
            List<String> fields = Arrays.asList(
                    InventoryOptionalFieldType.SIZE.toString(),
                    InventoryOptionalFieldType.LAST_MODIFIED_DATE.toString(),
                    InventoryOptionalFieldType.E_TAG.toString(),
                    InventoryOptionalFieldType.STORAGE_CLASS.toString(),
                    InventoryOptionalFieldType.IS_MULTIPART_UPLOADED.toString(),
                    InventoryOptionalFieldType.ENCRYPTION_STATUS.toString()
            );
            OptionalFields optionalFields = OptionalFields.newBuilder()
                    .fields(fields)
                    .build();

            // Create inventory configuration
            InventoryConfiguration inventoryConfiguration = InventoryConfiguration.newBuilder()
                    .id(inventoryId)
                    .isEnabled(true)
                    .destination(inventoryDestination)
                    .schedule(schedule)
                    .filter(filter)
                    .includedObjectVersions("All")
                    .optionalFields(optionalFields)
                    .build();

            // Create PutBucketInventory request
            PutBucketInventoryRequest request = PutBucketInventoryRequest.newBuilder()
                    .bucket(bucket)
                    .inventoryId(inventoryId)
                    .inventoryConfiguration(inventoryConfiguration)
                    .build();

            // Execute the request asynchronously
            CompletableFuture<PutBucketInventoryResult> future = client.putBucketInventoryAsync(request);
            PutBucketInventoryResult result = future.get();

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

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
        opts.addOption(Option.builder().longOpt("inventoryId").desc("The inventory configuration ID.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("accountId").desc("The account ID for the destination bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("roleArn").desc("The role ARN for the inventory configuration.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("destBucket").desc("The destination bucket name for inventory.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String inventoryId = cmd.getParsedOptionValue("inventoryId");
        String accountId = cmd.getParsedOptionValue("accountId");
        String roleArn = cmd.getParsedOptionValue("roleArn");
        String destBucket = cmd.getParsedOptionValue("destBucket");
        execute(endpoint, region, bucket, inventoryId, accountId, roleArn, destBucket);
    }
}