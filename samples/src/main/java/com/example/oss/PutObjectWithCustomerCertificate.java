package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.transport.HttpClientOptions;
import com.aliyun.sdk.service.oss2.transport.apache5client.Apache5HttpClientBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

public class PutObjectWithCustomerCertificate implements Example {

    private static void execute(
            String endpoint,
            String region,
            String bucket,
            String key,
            String keyStorePath,
            String keyStorePassword,
            String trustStorePath,
            String trustStorePassword,
            String filePath) {

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();

        try {
            // Verify that certificate files exist
            File keyStoreFile = new File(keyStorePath);
            File trustStoreFile = new File(trustStorePath);

            if (!keyStoreFile.exists()) {
                System.err.println("KeyStore file not found: " + keyStorePath);
                return;
            }

            if (!trustStoreFile.exists()) {
                System.err.println("TrustStore file not found: " + trustStorePath);
                return;
            }

            System.out.println("KeyStore file exists: " + keyStoreFile.getAbsolutePath());
            System.out.println("TrustStore file exists: " + trustStoreFile.getAbsolutePath());

            // Initialize SSL certificate configuration
            System.out.println("Loading KeyStore...");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            // First try loading with empty password to test if the file is valid
            try (FileInputStream keyStoreStream = new FileInputStream(keyStoreFile)) {
                keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
                System.out.println("KeyStore loaded successfully with provided password");
            } catch (Exception e) {
                System.err.println("Failed to load KeyStore with provided password: " + e.getMessage());
                // Try with empty password
                try (FileInputStream keyStoreStream = new FileInputStream(keyStoreFile)) {
                    keyStore.load(keyStoreStream, new char[0]);
                    System.out.println("KeyStore loaded successfully with empty password");
                    keyStorePassword = "";
                } catch (Exception e2) {
                    System.err.println("Failed to load KeyStore with empty password as well: " + e2.getMessage());
                    throw e; // Re-throw the original exception
                }
            }

            System.out.println("Loading TrustStore...");
            KeyStore trustStore = KeyStore.getInstance("JKS");
            try (FileInputStream trustStoreStream = new FileInputStream(trustStoreFile)) {
                trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
                System.out.println("TrustStore loaded successfully");
            } catch (Exception e) {
                System.err.println("Failed to load TrustStore: " + e.getMessage());
                throw e;
            }

            // Create KeyManagerFactory
            System.out.println("Initializing KeyManagerFactory...");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

            // Create TrustManagerFactory
            System.out.println("Initializing TrustManagerFactory...");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            // Get TrustManagers
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // Create HttpClient with certificate configuration
            System.out.println("Building HTTP client with SSL configuration...");

            // Configure HttpClient options
            HttpClientOptions httpClientOptions = HttpClientOptions.custom()
                    .build();

            Apache5HttpClientBuilder httpClientBuilder = Apache5HttpClientBuilder.create()
                    .keyManagers(keyManagerFactory.getKeyManagers())
                    .x509TrustManagers(new X509TrustManager[]{(X509TrustManager) trustManagers[0]})
                    .options(httpClientOptions);

            // Build OSS client
            System.out.println("Building OSS client...");
            OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                    .credentialsProvider(provider)
                    .region(region)
                    // If the SDK also enables mutual certificate authentication
                    .httpClient(httpClientBuilder.build());

            if (endpoint != null) {
                clientBuilder.endpoint(endpoint);
            }

            try (OSSClient client = clientBuilder.build()) {
                File uploadFile = new File(filePath);
                if (!uploadFile.exists()) {
                    System.err.println("Upload file not found: " + filePath);
                    return;
                }

                // Use SDK's putObject method to upload file directly
                System.out.println("Uploading file using putObject...");
                PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                        .bucket(bucket)
                        .key(key)
                        .body(BinaryData.fromStream(new FileInputStream(uploadFile)))
                        .build());

                System.out.printf("Upload status code: %d\n", result.statusCode());
                System.out.printf("Request ID: %s\n", result.requestId());
                System.out.printf("ETag: %s\n", result.eTag());
                System.out.println("Upload successful!");
            }
        } catch (Exception e) {
            System.err.printf("Error occurred: %s\n", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Options getOptions() {
        Options opts = new Options();
        opts.addOption(Option.builder().longOpt("endpoint").desc("The domain names that other services can use to access OSS.").hasArg().get());
        opts.addOption(Option.builder().longOpt("region").desc("The region in which the bucket is located.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("bucket").desc("The name of the bucket.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key").desc("The name of the object.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key-store").desc("Path to the key store file.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("key-store-password").desc("Password for the key store.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("trust-store").desc("Path to the trust store file.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("trust-store-password").desc("Password for the trust store.").hasArg().required().get());
        opts.addOption(Option.builder().longOpt("file").desc("Path to the file to upload.").hasArg().required().get());
        return opts;
    }

    @Override
    public void runCmd(CommandLine cmd) throws ParseException {
        String endpoint = cmd.getOptionValue("endpoint");
        String region = cmd.getOptionValue("region");
        String bucket = cmd.getOptionValue("bucket");
        String key = cmd.getOptionValue("key");
        String keyStorePath = cmd.getOptionValue("key-store");
        String keyStorePassword = cmd.getOptionValue("key-store-password");
        String trustStorePath = cmd.getOptionValue("trust-store");
        String trustStorePassword = cmd.getOptionValue("trust-store-password");
        String filePath = cmd.getOptionValue("file");

        execute(endpoint, region, bucket, key, keyStorePath, keyStorePassword,
                trustStorePath, trustStorePassword, filePath);
    }
}