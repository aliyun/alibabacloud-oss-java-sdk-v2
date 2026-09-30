package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class QuotaConfigurationTest {

    @Test
    public void testEmptyBuilder() {
        QuotaConfiguration config = QuotaConfiguration.newBuilder().build();
        assertThat(config).isNotNull();
        assertThat(config.storageQuota()).isNull();
        assertThat(config.mode()).isNull();
        assertThat(config.currentUsage()).isNull();
    }

    @Test
    public void testFullBuilder() {
        QuotaConfiguration config = QuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Strict")
                .currentUsage(1049600L)
                .build();

        assertThat(config.storageQuota()).isEqualTo(10737418240L);
        assertThat(config.mode()).isEqualTo("Strict");
        assertThat(config.currentUsage()).isEqualTo(1049600L);

        QuotaConfiguration copy = config.toBuilder().build();
        assertThat(copy.storageQuota()).isEqualTo(10737418240L);
        assertThat(copy.mode()).isEqualTo("Strict");
        assertThat(copy.currentUsage()).isEqualTo(1049600L);
    }

    @Test
    public void testXmlSerialization() throws Exception {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        QuotaConfiguration config = QuotaConfiguration.newBuilder()
                .storageQuota(10737418240L)
                .mode("Warning")
                .build();

        String xml = xmlMapper.writeValueAsString(config);
        assertThat(xml).contains("<QuotaConfiguration>");
        assertThat(xml).contains("<StorageQuota>10737418240</StorageQuota>");
        assertThat(xml).contains("<Mode>Warning</Mode>");
        assertThat(xml).contains("</QuotaConfiguration>");
        assertThat(xml).doesNotContain("CurrentUsage");
    }

    @Test
    public void testXmlDeserialization() throws Exception {
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String xml = "<QuotaConfiguration>"
                + "<Mode>Strict</Mode>"
                + "<StorageQuota>10737418240</StorageQuota>"
                + "<CurrentUsage>1049600</CurrentUsage>"
                + "</QuotaConfiguration>";

        QuotaConfiguration config = xmlMapper.readValue(xml, QuotaConfiguration.class);
        assertThat(config.mode()).isEqualTo("Strict");
        assertThat(config.storageQuota()).isEqualTo(10737418240L);
        assertThat(config.currentUsage()).isEqualTo(1049600L);
    }
}
