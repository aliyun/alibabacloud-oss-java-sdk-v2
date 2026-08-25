package com.aliyun.sdk.service.oss2.agentic;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BucketSpaceHelperTest {

    @Test
    public void testToBucketName() {
        BucketSpaceHelper helper = new BucketSpaceHelper("1234567890", "cn-hangzhou");
        assertThat(helper.toBucketName("my-space")).isEqualTo("my-space-1234567890-cn-hangzhou-bs-apsr");
    }

    @Test
    public void testToBucketNameEmptyFields() {
        BucketSpaceHelper helper = new BucketSpaceHelper("", "");
        assertThat(helper.toBucketName("prefix")).isEqualTo("prefix---bs-apsr");
    }
}
