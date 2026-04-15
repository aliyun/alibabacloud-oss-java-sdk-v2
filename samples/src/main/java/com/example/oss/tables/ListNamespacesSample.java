package com.example.oss.tables;

import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClient;
import com.aliyun.sdk.service.oss2.tables.OSSTablesClientBuilder;
import com.aliyun.sdk.service.oss2.tables.models.ListNamespacesRequest;
import com.aliyun.sdk.service.oss2.tables.models.ListNamespacesResult;
import com.aliyun.sdk.service.oss2.tables.models.NamespaceSummary;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ListNamespacesSample implements com.example.oss.Example {

    private static void execute(
            String endpoint,
            String region,
            String tableBucketARN,
            String continuationToken,
            Integer maxNamespaces,
            String prefix) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSTablesClientBuilder clientBuilder = OSSTablesClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSTablesClient client = clientBuilder.build()) {

            ListNamespacesRequest request = ListNamespacesRequest.newBuilder()
                    .tableBucketARN(tableBucketARN)
                    .continuationToken(continuationToken)
                    .maxNamespaces(maxNamespaces)
                    .prefix(prefix)
                    .build();

            ListNamespacesResult result = client.listNamespaces(request);

            System.out.printf("Status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());
            System.out.printf("Successfully listed namespaces for table bucket: %s%n", tableBucketARN);
            System.out.printf("Continuation token: %s%n", result.continuationToken());


            for (int i = 0; i < result.namespaces().size(); i++) {
                NamespaceSummary ns = result.namespaces().get(i);
                if (ns.namespace() != null && !ns.namespace().isEmpty()) {
                    System.out.printf("Namespace %%d: %%s (ID: %%s)%%n", i+1, ns.namespace().get(0), ns.namespaceId());
                } else {
                    System.out.printf("Namespace %%d: (no namespace name) (ID: %%s)%%n", i+1, ns.namespaceId());
                }
            }


        } catch (Exception e) {
            System.out.printf("Error:%n%s", e);
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS Tables.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the table bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tableBucketARN").desc("The ARN of the table bucket to list namespaces from.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("continuationToken").desc("A token to continue pagination.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxNamespaces").desc("The maximum number of namespaces to return.").hasArg().type(Number.class).get());
        opts.addOption(Option.builder().longOpt("prefix").desc("Filter namespaces by prefix.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String tableBucketARN = cmd.getParsedOptionValue("tableBucketARN");
        String continuationToken = cmd.getParsedOptionValue("continuationToken");
        Integer maxNamespaces = null;
        try {
            String maxNamespacesStr = cmd.getParsedOptionValue("maxNamespaces");
            if (maxNamespacesStr != null) {
                maxNamespaces = Integer.parseInt(maxNamespacesStr);
            }
        } catch (Exception e) {
            throw new ParseException("maxNamespaces must be a valid integer");
        }
        String prefix = cmd.getParsedOptionValue("prefix");
        execute(endpoint, region, tableBucketARN, continuationToken, maxNamespaces, prefix);
    }
}