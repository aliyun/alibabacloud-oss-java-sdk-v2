package com.aliyun.sdk.service.oss2.tables;

import com.aliyun.sdk.service.oss2.TestBase;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.tables.models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;

public class TestBaseTables extends TestBase {
    private static final String TABLE_BUCKET_NAME_PREFIX = "test-table-";

    private static OSSTablesClient tablesClient;
    private static List<String> createdBuckets = new ArrayList<>();
    protected static String tableBucketName;


    @BeforeClass
    public static void oneTimeSetUp() {
        tablesClient = newTablesClient();
        tableBucketName = genTableBucketName();
        createTableBucket(tableBucketName);
    }

    @AfterClass
    public static void oneTimeSetDown() {
        // Clean up all created vector buckets
        cleanTableBuckets(TABLE_BUCKET_NAME_PREFIX);
    }

    protected static String genTableBucketName() {
        String bucketName = TABLE_BUCKET_NAME_PREFIX + System.currentTimeMillis() + "-" +
                (int) (Math.random() * 10000);
        createdBuckets.add(bucketName);
        return bucketName;
    }

    public static OSSTablesClient newTablesClient() {
        CredentialsProvider provider = new StaticCredentialsProvider(accessKeyId(), accessKeySecret());
        return OSSTablesClient.newBuilder()
                .region(region())
                .endpoint(tablesEndpoint())
                .credentialsProvider(provider)
                .build();
    }

    public static void createTableBucket(String name) {
        getTablesClient().createTableBucket(CreateTableBucketRequest.newBuilder()
                .name(name)
                .build());
        waitForCacheExpiration(1);
    }

    public static void cleanTableBuckets(String prefix) {
        OSSTablesClient client = getTablesClient();
        ListTableBucketsResult result = client.listTableBuckets(ListTableBucketsRequest.newBuilder()
                .prefix(prefix)
                .build());
        for (TableBucketSummary o: result.tableBuckets()) {
            cleanTableBucket(client, o.arn());
        }
    }

    private static void cleanNamespace(OSSTablesClient client, String namespace) {
        // TODO
    }

    private static void cleanTables(OSSTablesClient client, String table) {
        // TODO
    }

    private static void cleanTableBucket(OSSTablesClient client, String tableBucketArn) {
        //TODO

        // DELETE table bucket
        client.deleteTableBucket(DeleteTableBucketRequest.newBuilder().tableBucketARN(tableBucketArn).build());
    }

    protected static OSSTablesClient getTablesClient() {
        return tablesClient;
    }
}