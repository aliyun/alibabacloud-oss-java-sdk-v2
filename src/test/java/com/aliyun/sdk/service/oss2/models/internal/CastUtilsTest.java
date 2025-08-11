package com.aliyun.sdk.service.oss2.models.internal;

import com.aliyun.sdk.service.oss2.models.ObjectSummary;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CastUtilsTest {

    @Test
    public void ensureList() {
        List<String> strList = null;
        assertThat(CastUtils.ensureList(strList)).isNotNull();
        assertThat(CastUtils.ensureList(strList)).isEmpty();

        ListBucketResultXml xmlResult = new ListBucketResultXml();
        assertThat(xmlResult.contents).isNull();
        for (ObjectSummary ignore: CastUtils.ensureList(xmlResult.contents)) {
            fail("should not here");
        }
    }

    @Test
    public void toMetadata() {
        // null
        assertThat(CastUtils.toMetadata(null)).isNull();

        // empty
        assertThat(CastUtils.toMetadata(new HashMap<>())).isEmpty();

        // has values
        Map<String, String> headers1 = new HashMap<>();
        headers1.put("x-oss-value1", "value1");
        headers1.put("x-oss-value2", "value2");
        assertThat(CastUtils.toMetadata(headers1)).isEmpty();
        assertThat(headers1).hasSize(2);

        headers1.put("x-oss-meta-value3", "value3");
        headers1.put("x-oss-meta-Value4", "value4");
        Map<String, String> metadata = CastUtils.toMetadata(headers1);
        assertThat(headers1).hasSize(4);
        assertThat(metadata).isNotEmpty();
        assertThat(metadata).hasSize(2);
        assertThat(metadata.get("value3")).isEqualTo("value3");
        assertThat(metadata.get("VALUE3")).isEqualTo("value3");
        assertThat(metadata.get("Value4")).isEqualTo("value4");
        assertThat(metadata.get("value4")).isEqualTo("value4");
    }
}