package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public class WriteGetObjectResponseAsync implements Example {

    private static void execute(
            String region,
            String route,
            String token) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(route, region, provider)) {
            // Prepare test data
            final int instreamLength = 128 * 1024;
            InputStream instream = genFixedLengthInputStream(instreamLength);
            String status = "200";
            
            // Create request object - Using new constructor
            WriteGetObjectResponseRequest writeGetObjectResponseRequest = 
                new WriteGetObjectResponseRequest(route, token, status, instream);
            
            // Optional: Add additional header information
            // writeGetObjectResponseRequest = writeGetObjectResponseRequest.toBuilder()
            //     .fwdHeaderAcceptRanges("bytes")
            //     .fwdHeaderCacheControl("no-cache")
            //     .fwdHeaderContentDisposition("attachment")
            //     .fwdHeaderContentEncoding("gzip")
            //     .fwdHeaderContentRange("bytes 0-9/100")
            //     .fwdHeaderContentType("text/html")
            //     .fwdHeaderEtag("test-etag")
            //     .fwdHeaderExpires("Fri, 10 Nov 2023 03:17:58 GMT")
            //     .fwdHeaderLastModified("Tue, 10 Oct 2023 03:17:58 GMT")
            //     .build();
            
            // Execute async request
            CompletableFuture<WriteGetObjectResponseResult> future = 
                client.writeGetObjectResponseAsync(writeGetObjectResponseRequest);
            
            WriteGetObjectResponseResult result = future.get();
            
            System.out.printf("WriteGetObjectResponse successful! status code:%d, request id:%s%n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            // If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //    System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            // }
            System.out.printf("Error occurred:\n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(String route, String region, CredentialsProvider provider) {
        return OSSAsyncClient.newBuilder()
                .region(region)
                .endpoint(route)
                .credentialsProvider(provider)
                .build();
    }
    
    public static InputStream genFixedLengthInputStream(long fixedLength) {
        byte[] buf = new byte[(int) fixedLength];
        for (int i = 0; i < buf.length; i++)
            buf[i] = 'a';
        return new ByteArrayInputStream(buf);
    }
    
    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("route").desc("The request route.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("token").desc("The request token.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String region = cmd.getParsedOptionValue("region");
        String route = cmd.getParsedOptionValue("route");
        String token = cmd.getParsedOptionValue("token");
        execute(region, route, token);
    }
}