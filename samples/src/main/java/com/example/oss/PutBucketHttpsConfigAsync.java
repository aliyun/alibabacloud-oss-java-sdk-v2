package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSAsyncClient;
import com.aliyun.sdk.service.oss2.OSSAsyncClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.Arrays;

public class PutBucketHttpsConfigAsync implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String tlsEnable,
            String tlsVersions,
            String cipherSuiteEnable,
            String strongCipherSuite,
            String customCipherSuites,
            String tls13CustomCipherSuites) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try (OSSAsyncClient client = getDefaultAsyncClient(endpoint, region, provider)) {

            TLS tls = null;
            if (tlsEnable != null) {
                TLS.Builder tlsBuilder = TLS.newBuilder()
                        .enable(Boolean.parseBoolean(tlsEnable));
                if (tlsVersions != null) {
                    tlsBuilder.tLSVersions(Arrays.asList(tlsVersions.split(",")));
                }
                tls = tlsBuilder.build();
            }

            CipherSuite cipherSuite = null;
            if (cipherSuiteEnable != null) {
                CipherSuite.Builder csBuilder = CipherSuite.newBuilder()
                        .enable(Boolean.parseBoolean(cipherSuiteEnable));
                if (strongCipherSuite != null) {
                    csBuilder.strongCipherSuite(Boolean.parseBoolean(strongCipherSuite));
                }
                if (customCipherSuites != null) {
                    csBuilder.customCipherSuites(Arrays.asList(customCipherSuites.split(",")));
                }
                if (tls13CustomCipherSuites != null) {
                    csBuilder.tLS13CustomCipherSuites(Arrays.asList(tls13CustomCipherSuites.split(",")));
                }
                cipherSuite = csBuilder.build();
            }

            HttpsConfiguration httpsConfiguration = null;
            if (tls != null || cipherSuite != null) {
                HttpsConfiguration.Builder hcBuilder = HttpsConfiguration.newBuilder();
                if (tls != null) {
                    hcBuilder.tLS(tls);
                }
                if (cipherSuite != null) {
                    hcBuilder.cipherSuite(cipherSuite);
                }
                httpsConfiguration = hcBuilder.build();
            }

            PutBucketHttpsConfigResult result = client.putBucketHttpsConfigAsync(PutBucketHttpsConfigRequest.newBuilder()
                            .bucket(bucket)
                            .httpsConfiguration(httpsConfiguration)
                            .build()).get();

            System.out.printf("Status code:%d, request id:%s\n",
                    result.statusCode(), result.requestId());

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            //ServiceException se = ServiceException.asCause(e);
            //if (se != null) {
            //   System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
    }

    private static OSSAsyncClient getDefaultAsyncClient(String endpoint, String region, CredentialsProvider provider) {
        OSSAsyncClientBuilder builder = OSSAsyncClient.newBuilder()
                .region(region)
                .credentialsProvider(provider);
        if (endpoint != null) {
            builder.endpoint(endpoint);
        }
        return builder.build();
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("tlsEnable").desc("Specifies whether to enable TLS version management for the bucket.").hasArg().get());
        opts.addOption(Option.builder().longOpt("tlsVersions").desc("The TLS versions. Multiple values can be separated by commas.").hasArg().get());
        opts.addOption(Option.builder().longOpt("cipherSuiteEnable").desc("Specifies whether to enable cipher suite management for the bucket.").hasArg().get());
        opts.addOption(Option.builder().longOpt("strongCipherSuite").desc("Specifies whether to enable strong cipher suites.").hasArg().get());
        opts.addOption(Option.builder().longOpt("customCipherSuites").desc("The custom cipher suites. Multiple values can be separated by commas.").hasArg().get());
        opts.addOption(Option.builder().longOpt("tls13CustomCipherSuites").desc("The TLS 1.3 custom cipher suites. Multiple values can be separated by commas.").hasArg().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getParsedOptionValue("endpoint");
        String region = cmd.getParsedOptionValue("region");
        String bucket = cmd.getParsedOptionValue("bucket");
        String tlsEnable = cmd.getOptionValue("tlsEnable");
        String tlsVersions = cmd.getOptionValue("tlsVersions");
        String cipherSuiteEnable = cmd.getOptionValue("cipherSuiteEnable");
        String strongCipherSuite = cmd.getOptionValue("strongCipherSuite");
        String customCipherSuites = cmd.getParsedOptionValue("customCipherSuites");
        String tls13CustomCipherSuites = cmd.getParsedOptionValue("tls13CustomCipherSuites");
        execute(endpoint, region, bucket, tlsEnable, tlsVersions, cipherSuiteEnable, strongCipherSuite, customCipherSuites, tls13CustomCipherSuites);
    }
}
