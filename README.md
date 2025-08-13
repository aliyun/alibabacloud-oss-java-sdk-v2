# Alibaba Cloud OSS SDK for Java V2

[![GitHub version](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-java-sdk-v2.svg)](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-java-sdk-v2)

alibabacloud-oss-java-sdk-v2 is the developer preview for the v2 of the OSS SDK for the Java programming language

## [README in Chinese](README-CN.md)

## About
> - This Java SDK is based on the official APIs of [Alibaba Cloud OSS](http://www.aliyun.com/product/oss/).
> - Alibaba Cloud Object Storage Service (OSS) is a cloud storage service provided by Alibaba Cloud, featuring massive capacity, security, a low cost, and high reliability. 
> - The OSS can store any type of files and therefore applies to various websites, development enterprises and developers.
> - With this SDK, you can upload, download and manage data on any app anytime and anywhere conveniently. 

## Running Environment
> - Java 8 or above. 

## Installing
### Install the version through maven
The recommended way to use the Alibaba Cloud OSS SDK for Java in your project is to consume it from Maven. Import as follows:
```
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>alibabacloud-oss-v2</artifactId>
    <version>latest version</version>
</dependency>
```

### Install from the source code
Once you check out the code from GitHub, you can build it using Maven. Use the following command to build:
```
mvn clean install -DskipTests
```

## Getting Started
#### List Bucket
```java
package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.paginator.ListBucketsIterable;

public class Example {
    public static void main(String[] args) {
        String region = "cn-hangzhou";

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        try (OSSClient client = clientBuilder.build()) {

            ListBucketsIterable paginator = client.listBucketsPaginator(
                    ListBucketsRequest.newBuilder()
                            .build());

            for (ListBucketsResult result : paginator) {
                for (BucketSummary info : result.buckets()) {
                    System.out.printf("bucket: name:%s, region:%s, storageClass:%s\n", info.name(), info.region(), info.storageClass());
                }
            }

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //    System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
    }
}
```

#### List Objects
```java
package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;
import com.aliyun.sdk.service.oss2.paginator.ListObjectsV2Iterable;

public class Example {
    public static void main(String[] args) {
        String region = "cn-hangzhou";
        String bucket = "your bucket name";

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        try (OSSClient client = clientBuilder.build()) {

            ListObjectsV2Iterable paginator = client.listObjectsV2Paginator(
                    ListObjectsV2Request.newBuilder()
                            .bucket(bucket)
                            .build());

            for (ListObjectsV2Result result : paginator) {
                for (ObjectSummary info : result.contents()) {
                    System.out.printf("bucket: name:%s, region:%s, storageClass:%s\n", info.key(), info.size(), info.lastModified());
                }
            }

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //    System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
    }
}

```

#### Put Object
```java
package com.example.oss;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.*;

public class Example {
    public static void main(String[] args) {
        String region = "cn-hangzhou";
        String bucket = "your bucket name";
        String key = "your object name";

        CredentialsProvider provider = new EnvironmentVariableCredentialsProvider();
        OSSClientBuilder clientBuilder = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(region);

        try (OSSClient client = clientBuilder.build()) {

            String data = "hello world";

            PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                            .bucket(bucket)
                            .key(key)
                            .body(BinaryData.fromString(data))
                    .build());

            System.out.printf("status code:%d, request id:%s, eTag:%s\n",
                    result.statusCode(), result.requestId(), result.eTag());

        } catch (Exception e) {
            //If the exception is caused by ServiceException, detailed information can be obtained in this way.
            // ServiceException se = ServiceException.asCause(e);
            // if (se != null) {
            //    System.out.printf("ServiceException: requestId:%s, errorCode:%s\n", se.requestId(), se.errorCode());
            //}
            System.out.printf("error:\n%s", e);
        }
    }
}

```

##  Complete Example
More example projects can be found in the `samples` folder 

### Running Example
> - Go to the sample code folder `samples`.
> - Compile the code `mvn clean package`.
> - Configure credentials values from the environment variables, like `export OSS_ACCESS_KEY_ID="your access key id"`, `export OSS_ACCESS_KEY_SECRET="your access key secrect"`.
> - Take ListBuckets as an example，run `java -jar target/oss-example-1.0.jar ListBuckets --region cn-hangzhou`.

## License
> - Apache-2.0, see [license file](LICENSE)