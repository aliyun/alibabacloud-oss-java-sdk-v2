
# Alibaba Cloud OSS SDK for Java V2

[![GitHub version](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-java-sdk-v2.svg)](https://badge.fury.io/gh/aliyun%2Falibabacloud-oss-java-sdk-v2)

alibabacloud-oss-java-sdk-v2 是OSS在Java编译语言下的第二版SDK

## [README in English](README-CN.md)

## 关于
> - 此Java SDK基于[阿里云对象存储服务](http://www.aliyun.com/product/oss/)官方API构建。
> - 阿里云对象存储（Object Storage Service，简称OSS），是阿里云对外提供的海量，安全，低成本，高可靠的云存储服务。
> - OSS适合存放任意文件类型，适合各种网站、开发企业及开发者使用。
> - 使用此SDK，用户可以方便地在任何应用、任何时间、任何地点上传，下载和管理数据。

## 运行环境
> - Java 8 及以上。

## 安装方法
### 通过 Maven 安装
在您的项目中使用阿里云OSS Java SDK的推荐方法是通过Maven安装，导入如下：
```
<dependency>
    <groupId>com.aliyun</groupId>
    <artifactId>alibabacloud-oss-v2</artifactId>
    <version>latest version</version>
</dependency>
```

### 通过源码安装
当您从GitHub下载代码后，可以使用Maven命令进行构建，并安装：

```
mvn clean install -DskipTests
```

## 快速使用
#### 获取存储空间列表（List Bucket）
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

#### 获取文件列表（List Objects）
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

#### 上传文件（Put Object）
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

## 更多示例
请参看`samples`目录

### 运行示例
> - 进入示例程序目录 `samples`。
> - 编译示例代码 `mvn clean package`。
> - 通过环境变量，配置访问凭证, `export OSS_ACCESS_KEY_ID="your access key id"`, `export OSS_ACCESS_KEY_SECRET="your access key secrect"`。
> - 以 ListBuckets 为例，执行 `java -jar target/oss-example-1.0.jar ListBuckets --region cn-hangzhou`。


## 许可协议
> - Apache-2.0, 请参阅 [许可文件](LICENSE)
