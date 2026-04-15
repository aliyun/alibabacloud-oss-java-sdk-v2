package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.RenameTableRequest;
import com.aliyun.sdk.service.oss2.tables.models.RenameTableResult;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class RenameTableSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String namespace,
            String name,
            String newNamespaceName,
            String newName,
            String versionToken) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            RenameTableRequest.Builder requestBuilder = RenameTableRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .namespace(namespace)
                    .name(name);
            
            if (newNamespaceName != null) {
                requestBuilder.newNamespaceName(newNamespaceName);
            }
            
            if (newName != null) {
                requestBuilder.newName(newName);
            }
            
            if (versionToken != null) {
                requestBuilder.versionToken(versionToken);
            }

            RenameTableResult result = client.renameTable(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully renamed table from %s/%s to %s/%s%n", 
                    namespace, name, newNamespaceName != null ? newNamespaceName : namespace, newName != null ? newName : name);

        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket containing the table to rename.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("namespace").desc("The namespace of the table to rename.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("name").desc("The current name of the table to rename.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("newNamespaceName").desc("The new namespace name for the table.").hasArg().get());
        opts.addOption(Option.builder().longOpt("newName").desc("The new name for the table.").hasArg().get());
        opts.addOption(Option.builder().longOpt("versionToken").desc("The version token for the table.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String namespace = cmd.getParsedOptionValue("namespace");
        String name = cmd.getParsedOptionValue("name");
        String newNamespaceName = cmd.getParsedOptionValue("newNamespaceName");
        String newName = cmd.getParsedOptionValue("newName");
        String versionToken = cmd.getParsedOptionValue("versionToken");
        execute(endpoint, region, tableBucketARN, namespace, name, newNamespaceName, newName, versionToken);
    }
}