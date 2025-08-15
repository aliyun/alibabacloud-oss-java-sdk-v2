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

import java.util.List;

public class ListObjectVersions implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String prefix,
            String delimiter,
            String keyMarker,
            String versionIdMarker,
            Long maxKeys) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        if (endpoint != null) {
            clientBuilder.endpoint(endpoint);
        }

        try (OSSClient client = clientBuilder.build()) {
            
            ListObjectVersionsRequest.Builder requestBuilder = ListObjectVersionsRequest.newBuilder();

            if (bucket != null) {
                requestBuilder.bucket(bucket);
            }

            if (prefix != null) {
                requestBuilder.prefix(prefix);
            }
            
            if (delimiter != null) {
                requestBuilder.delimiter(delimiter);
            }
            
            if (keyMarker != null) {
                requestBuilder.keyMarker(keyMarker);
            }
            
            if (versionIdMarker != null) {
                requestBuilder.versionIdMarker(versionIdMarker);
            }
            
            if (maxKeys != null) {
                requestBuilder.maxKeys(maxKeys);
            }

            ListObjectVersionsResult result = client.listObjectVersions(requestBuilder.build());

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());
            
            System.out.printf("Bucket name: %s\n", result.name());
            System.out.printf("Is truncated: %s\n", result.isTruncated());
            
            List<ObjectVersion> versions = result.versions();
            if (versions != null && !versions.isEmpty()) {
                System.out.println("Object versions:");
                for (ObjectVersion version : versions) {
                    System.out.printf("- Key: %s, VersionId: %s, IsLatest: %s, Size: %d\n",
                            version.key(), version.versionId(), version.isLatest(), version.size());
                }
            } else {
                System.out.println("No object versions found.");
            }
            
            List<DeleteMarkerEntry> deleteMarkers = result.deleteMarkers();
            if (deleteMarkers != null && !deleteMarkers.isEmpty()) {
                System.out.println("Delete markers:");
                for (DeleteMarkerEntry marker : deleteMarkers) {
                    System.out.printf("- Key: %s, VersionId: %s, IsLatest: %s\n",
                            marker.key(), marker.versionId(), marker.isLatest());
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
        opts.addOption(Option.builder().longOpt("prefix").desc("The prefix that the names of returned objects must contain.").hasArg().get());
        opts.addOption(Option.builder().longOpt("delimiter").desc("The character that is used to group objects by name.").hasArg().get());
        opts.addOption(Option.builder().longOpt("keyMarker").desc("The name of the object after which the GetBucketVersions operation begins.").hasArg().get());
        opts.addOption(Option.builder().longOpt("versionIdMarker").desc("The version ID of the object specified in key-marker.").hasArg().get());
        opts.addOption(Option.builder().longOpt("maxKeys").desc("The maximum number of objects to be returned.").hasArg().type(Long.class).get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String prefix = cmd.getParsedOptionValue("prefix");
        String delimiter = cmd.getParsedOptionValue("delimiter");
        String keyMarker = cmd.getParsedOptionValue("keyMarker");
        String versionIdMarker = cmd.getParsedOptionValue("versionIdMarker");
        Long maxKeys = cmd.getParsedOptionValue("maxKeys");
        execute(endpoint, region, bucket, prefix, delimiter, keyMarker, versionIdMarker, maxKeys);
    }
}
