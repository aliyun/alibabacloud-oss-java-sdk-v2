package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class DoMetaQuery implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // Prepare the meta query request
            String queryCondition = "{\"Field\": \"Size\",\"Value\": \"0\",\"Operation\": \"gt\"}";
            
            MetaQueryAggregation agg1 = MetaQueryAggregation.newBuilder()
                    .field("Size")
                    .operation("sum")
                    .build();
            MetaQueryAggregation agg2 = MetaQueryAggregation.newBuilder()
                    .field("Size")
                    .operation("max")
                    .build();
            
            MetaQueryAggregations aggregationsContainer = MetaQueryAggregations.newBuilder()
                    .aggregation(Arrays.asList(agg1, agg2))
                    .build();
            
            MetaQuery metaQuery = MetaQuery.newBuilder()
                    .maxResults(5)
                    .query(queryCondition)
                    .sort("Size")
                    .order(OrderType.ASC.toString())
                    .aggregations(aggregationsContainer)
                    .build();

            // Execute the meta query
            DoMetaQueryResult result = client.doMetaQuery(
                    DoMetaQueryRequest.newBuilder()
                            .bucket(bucket)
                            .mode("basic")
                            .metaQuery(metaQuery)
                            .build());

            System.out.printf("Status Code: %d%n", result.statusCode());
            System.out.printf("Request ID: %s%n", result.requestId());

            // Access the query results
            if (result.metaQuery() != null) {
                System.out.printf("Next Token: %s%n", result.metaQuery().nextToken());
                if (result.metaQuery().files() != null) {
                    System.out.printf("Number of files returned: %d%n", 
                            result.metaQuery().files().file().size());
                    
                    // Print details of first few files if available
                    for (int i = 0; i < Math.min(3, result.metaQuery().files().file().size()); i++) {
                        MetaQueryFile file = result.metaQuery().files().file().get(i);
                        System.out.printf("File %d: %s (Size: %d)%n", 
                                i + 1, 
                                file.filename(), 
                                file.size());
                    }
                }
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

    private static void executeVector(
            String endpoint,
            String region,
            String bucket) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            // Prepare the vector meta query request
            String simpleQuery = "{\"Operation\":\"gt\", \"Field\": \"Size\", \"Value\": \"30\"}";
            
            MetaQueryMediaTypes mediaTypes = MetaQueryMediaTypes.newBuilder()
                    .mediaTypes(java.util.Arrays.asList("image"))
                    .build();
            
            MetaQuery metaQuery = MetaQuery.newBuilder()
                    .maxResults(5)
                    .query("俯瞰白雪覆盖的森林")
                    .mediaTypes(mediaTypes)
                    .simpleQuery(simpleQuery)
                    .build();

            // Execute the vector meta query
            DoMetaQueryResult result = client.doMetaQuery(
                    DoMetaQueryRequest.newBuilder()
                            .bucket(bucket)
                            .mode("semantic")  // Use semantic mode for vector queries
                            .metaQuery(metaQuery)
                            .build());

            System.out.printf("Status Code: %d%n", result.statusCode());
            System.out.printf("Request ID: %s%n", result.requestId());

            // Access the query results
            if (result.metaQuery() != null) {
                System.out.printf("Next Token: %s%n", result.metaQuery().nextToken());
                if (result.metaQuery().files() != null) {
                    System.out.printf("Number of files returned: %d%n", 
                            result.metaQuery().files().file().size());
                    
                    // Print details of first few files if available
                    for (int i = 0; i < Math.min(3, result.metaQuery().files().file().size()); i++) {
                        MetaQueryFile file = result.metaQuery().files().file().get(i);
                        System.out.printf("File %d: %s (Size: %d)%n", 
                                i + 1, 
                                file.filename(), 
                                file.size());
                    }
                }
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
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        execute(endpoint, region, bucket);  // Run scalar query by default
        // Uncomment the line below to run vector query instead
        // executeVector(endpoint, region, bucket);
    }
}