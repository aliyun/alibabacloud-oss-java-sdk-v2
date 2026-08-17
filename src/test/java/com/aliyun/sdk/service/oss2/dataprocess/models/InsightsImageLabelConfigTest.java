package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for InsightsImageLabelConfig and related image label serialization.
 * Covers requirements from image-labels-sdk-requirements.md section 6.4.
 */
public class InsightsImageLabelConfigTest {

    private final ObjectMapper jsonMapper;
    private final ObjectMapper xmlMapper;

    public InsightsImageLabelConfigTest() {
        jsonMapper = new ObjectMapper();
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // -----------------------------------------------------------------------
    // 1. InsightsImageConfig.Builder can set and read label
    // -----------------------------------------------------------------------
    @Test
    public void testBuilderSetAndGetLabel() {
        InsightsImageLabelConfig labelConfig = InsightsImageLabelConfig.newBuilder()
                .system(EnableConfig.newBuilder().enable(true).build())
                .build();

        InsightsImageConfig imageConfig = InsightsImageConfig.newBuilder()
                .label(labelConfig)
                .build();

        assertThat(imageConfig.label()).isNotNull();
        assertThat(imageConfig.label().system()).isNotNull();
        assertThat(imageConfig.label().system().enable()).isEqualTo("true");
    }

    @Test
    public void testBuilderWithCaptionAndLabel() {
        InsightsCaptionConfig caption = InsightsCaptionConfig.newBuilder()
                .enable(true)
                .build();

        InsightsImageLabelConfig label = InsightsImageLabelConfig.newBuilder()
                .system(EnableConfig.newBuilder().enable(true).build())
                .build();

        InsightsImageConfig imageConfig = InsightsImageConfig.newBuilder()
                .caption(caption)
                .label(label)
                .build();

        assertThat(imageConfig.caption()).isNotNull();
        assertThat(imageConfig.label()).isNotNull();
        assertThat(imageConfig.label().system().enable()).isEqualTo("true");
    }

    // -----------------------------------------------------------------------
    // 2. toBuilder() does not lose System and UserDefined
    // -----------------------------------------------------------------------
    @Test
    public void testToBuilderPreservesSystemAndUserDefined() {
        InsightsImageLabelConfig original = InsightsImageLabelConfig.newBuilder()
                .system(EnableConfig.newBuilder().enable(true).build())
                .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                        .enable(true)
                        .mode("Enhanced")
                        .labels(Collections.singletonList(
                                InsightsLabelItem.newBuilder()
                                        .name("未佩戴安全帽")
                                        .description("画面中存在人员，且人员头部未佩戴安全帽")
                                        .build()))
                        .build())
                .build();

        InsightsImageLabelConfig copy = original.toBuilder().build();

        assertThat(copy.system()).isNotNull();
        assertThat(copy.system().enable()).isEqualTo("true");
        assertThat(copy.userDefined()).isNotNull();
        assertThat(copy.userDefined().enable()).isEqualTo("true");
        assertThat(copy.userDefined().mode()).isEqualTo("Enhanced");
        assertThat(copy.userDefined().labels()).hasSize(1);
        assertThat(copy.userDefined().labels().get(0).name()).isEqualTo("未佩戴安全帽");
    }

    @Test
    public void testInsightsImageConfigToBuilderPreservesLabel() {
        InsightsImageConfig original = InsightsImageConfig.newBuilder()
                .label(InsightsImageLabelConfig.newBuilder()
                        .system(EnableConfig.newBuilder().enable(true).build())
                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                .enable(true)
                                .mode("Simple")
                                .build())
                        .build())
                .build();

        InsightsImageConfig copy = original.toBuilder().build();

        assertThat(copy.label()).isNotNull();
        assertThat(copy.label().system().enable()).isEqualTo("true");
        assertThat(copy.label().userDefined().mode()).isEqualTo("Simple");
    }

    // -----------------------------------------------------------------------
    // 3. System-only, UserDefined-only, and both JSON serialization
    // -----------------------------------------------------------------------
    @Test
    public void testJsonSerializationSystemOnly() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder()
                                                .enable(true)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        assertThat(json).contains("\"Insights\"");
        assertThat(json).contains("\"Image\"");
        assertThat(json).contains("\"Label\"");
        assertThat(json).contains("\"System\"");
        assertThat(json).contains("\"Enable\":\"true\"");
        assertThat(json).doesNotContain("\"UserDefined\"");
        assertThat(json).doesNotContain("\"Highlight\"");
    }

    @Test
    public void testJsonSerializationUserDefinedOnly() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Collections.singletonList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("存在明火")
                                                                .description("画面中存在清晰可见的火焰")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        assertThat(json).contains("\"UserDefined\"");
        assertThat(json).contains("\"Enable\":\"true\"");
        assertThat(json).contains("\"Mode\":\"Enhanced\"");
        assertThat(json).contains("\"Labels\"");
        assertThat(json).contains("\"Name\":\"存在明火\"");
        assertThat(json).doesNotContain("\"System\"");
        assertThat(json).doesNotContain("\"Highlight\"");
    }

    @Test
    public void testJsonSerializationBothSystemAndUserDefined() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder()
                                                .enable(true)
                                                .build())
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Arrays.asList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("未佩戴安全帽")
                                                                .description("画面中存在人员，且人员头部未佩戴安全帽")
                                                                .build(),
                                                        InsightsLabelItem.newBuilder()
                                                                .name("存在明火")
                                                                .description("画面中存在清晰可见的火焰或正在燃烧的物体")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        assertThat(json).contains("\"System\"");
        assertThat(json).contains("\"UserDefined\"");
        assertThat(json).doesNotContain("\"Highlight\"");
    }

    // -----------------------------------------------------------------------
    // 4. Mode supports as-is serialization for Enhanced and Simple
    // -----------------------------------------------------------------------
    @Test
    public void testModeEnhancedSerialization() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);
        assertThat(json).contains("\"Mode\":\"Enhanced\"");
    }

    @Test
    public void testModeSimpleSerialization() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Simple")
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);
        assertThat(json).contains("\"Mode\":\"Simple\"");
    }

    @Test
    public void testModeOmittedWhenNotSet() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);
        assertThat(json).doesNotContain("\"Mode\"");
    }

    // -----------------------------------------------------------------------
    // 5. Labels serialized as array in JSON
    // -----------------------------------------------------------------------
    @Test
    public void testLabelsSerializedAsJsonArray() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .labels(Arrays.asList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("标签A")
                                                                .description("描述A")
                                                                .build(),
                                                        InsightsLabelItem.newBuilder()
                                                                .name("标签B")
                                                                .description("描述B")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        // Labels must appear as a JSON array
        assertThat(json).contains("\"Labels\":[");
        assertThat(json).contains("\"Name\":\"标签A\"");
        assertThat(json).contains("\"Name\":\"标签B\"");
    }

    // -----------------------------------------------------------------------
    // 6. XML: Labels must serialize as <Labels><Label>...</Label></Labels>
    // -----------------------------------------------------------------------
    @Test
    public void testXmlLabelsSerializedWithWrapper() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Collections.singletonList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("未佩戴安全帽")
                                                                .description("画面中存在人员，且人员头部未佩戴安全帽")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String xml = xmlMapper.writeValueAsString(config);

        assertThat(xml).contains("<Labels><Label>");
        assertThat(xml).contains("<Name>未佩戴安全帽</Name>");
        assertThat(xml).contains("<Description>画面中存在人员，且人员头部未佩戴安全帽</Description>");
        assertThat(xml).contains("</Label></Labels>");
    }

    @Test
    public void testXmlSystemOnlySerialization() throws JsonProcessingException {
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder()
                                                .enable(true)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String xml = xmlMapper.writeValueAsString(config);

        assertThat(xml).contains("<System>");
        assertThat(xml).contains("<Enable>true</Enable>");
        assertThat(xml).contains("</System>");
        assertThat(xml).doesNotContain("<UserDefined>");
        assertThat(xml).doesNotContain("<Highlight>");
    }

    // -----------------------------------------------------------------------
    // 7. DatasetConfig XML/JSON deserialization restores image label config
    // -----------------------------------------------------------------------
    @Test
    public void testJsonDeserializationRoundTrip() throws JsonProcessingException {
        DatasetConfig original = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder()
                                                .enable(true)
                                                .build())
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Collections.singletonList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("未佩戴安全帽")
                                                                .description("画面中存在人员，且人员头部未佩戴安全帽")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(original);
        DatasetConfig restored = jsonMapper.readValue(json, DatasetConfig.class);

        assertThat(restored.insights()).isNotNull();
        assertThat(restored.insights().image()).isNotNull();
        assertThat(restored.insights().image().label()).isNotNull();
        assertThat(restored.insights().image().label().system()).isNotNull();
        assertThat(restored.insights().image().label().system().enable()).isEqualTo("true");
        assertThat(restored.insights().image().label().userDefined()).isNotNull();
        assertThat(restored.insights().image().label().userDefined().enable()).isEqualTo("true");
        assertThat(restored.insights().image().label().userDefined().mode()).isEqualTo("Enhanced");
        assertThat(restored.insights().image().label().userDefined().labels()).hasSize(1);
        assertThat(restored.insights().image().label().userDefined().labels().get(0).name())
                .isEqualTo("未佩戴安全帽");
        assertThat(restored.insights().image().label().userDefined().labels().get(0).description())
                .isEqualTo("画面中存在人员，且人员头部未佩戴安全帽");
    }

    @Test
    public void testXmlDeserializationRoundTrip() throws JsonProcessingException {
        String xml = "<DatasetConfig>"
                + "<Insights>"
                + "<Image>"
                + "<Label>"
                + "<System><Enable>true</Enable></System>"
                + "<UserDefined>"
                + "<Enable>true</Enable>"
                + "<Mode>Enhanced</Mode>"
                + "<Labels>"
                + "<Label>"
                + "<Name>未佩戴安全帽</Name>"
                + "<Description>画面中存在人员，且人员头部未佩戴安全帽</Description>"
                + "</Label>"
                + "</Labels>"
                + "</UserDefined>"
                + "</Label>"
                + "</Image>"
                + "</Insights>"
                + "</DatasetConfig>";

        DatasetConfig restored = xmlMapper.readValue(xml, DatasetConfig.class);

        assertThat(restored.insights()).isNotNull();
        assertThat(restored.insights().image()).isNotNull();
        assertThat(restored.insights().image().label()).isNotNull();
        assertThat(restored.insights().image().label().system()).isNotNull();
        assertThat(restored.insights().image().label().system().enable()).isEqualTo("true");
        assertThat(restored.insights().image().label().userDefined()).isNotNull();
        assertThat(restored.insights().image().label().userDefined().enable()).isEqualTo("true");
        assertThat(restored.insights().image().label().userDefined().mode()).isEqualTo("Enhanced");
        assertThat(restored.insights().image().label().userDefined().labels()).hasSize(1);
        assertThat(restored.insights().image().label().userDefined().labels().get(0).name())
                .isEqualTo("未佩戴安全帽");

        // Verify re-serialization matches
        String reSerialized = xmlMapper.writeValueAsString(restored);
        assertThat(reSerialized).contains("<System>");
        assertThat(reSerialized).contains("<UserDefined>");
        assertThat(reSerialized).contains("<Mode>Enhanced</Mode>");
    }

    // -----------------------------------------------------------------------
    // 8. Unconfigured fields not output; Highlight absent from image Label
    // -----------------------------------------------------------------------
    @Test
    public void testUnconfiguredFieldsOmitted() throws JsonProcessingException {
        // Empty label config: no system, no userDefined
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder().build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        assertThat(json).doesNotContain("\"System\"");
        assertThat(json).doesNotContain("\"UserDefined\"");
        assertThat(json).doesNotContain("\"Highlight\"");
        assertThat(json).doesNotContain("\"Mode\"");
        assertThat(json).doesNotContain("\"Labels\"");
    }

    @Test
    public void testHighlightNeverAppearsInImageLabel() throws JsonProcessingException {
        // Full config: both system and userDefined set
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder().enable(true).build())
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Collections.singletonList(
                                                        InsightsLabelItem.newBuilder()
                                                                .name("test")
                                                                .build()))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);
        assertThat(json).doesNotContain("\"Highlight\"");

        String xml = xmlMapper.writeValueAsString(config);
        assertThat(xml).doesNotContain("<Highlight>");
    }

    @Test
    public void testNoLabelWhenOnlyCaptionConfigured() throws JsonProcessingException {
        // Only caption, no label
        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .caption(InsightsCaptionConfig.newBuilder()
                                        .enable(true)
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        assertThat(json).contains("\"Caption\"");
        assertThat(json).doesNotContain("\"Label\"");
        assertThat(json).doesNotContain("\"Highlight\"");
    }

    // -----------------------------------------------------------------------
    // Builder edge cases
    // -----------------------------------------------------------------------
    @Test
    public void testEmptyBuilderAllNulls() {
        InsightsImageLabelConfig config = InsightsImageLabelConfig.newBuilder().build();

        assertThat(config.system()).isNull();
        assertThat(config.userDefined()).isNull();
    }

    @Test
    public void testInsightsImageConfigLabelNullByDefault() {
        InsightsImageConfig config = InsightsImageConfig.newBuilder().build();

        assertThat(config.caption()).isNull();
        assertThat(config.label()).isNull();
    }

    @Test
    public void testUserDefinedWithMultipleLabels() throws JsonProcessingException {
        InsightsLabelItem label1 = InsightsLabelItem.newBuilder()
                .name("未佩戴安全帽")
                .description("画面中存在人员，且人员头部未佩戴安全帽")
                .build();
        InsightsLabelItem label2 = InsightsLabelItem.newBuilder()
                .name("存在明火")
                .description("画面中存在清晰可见的火焰或正在燃烧的物体")
                .build();

        DatasetConfig config = DatasetConfig.newBuilder()
                .insights(InsightsConfig.newBuilder()
                        .image(InsightsImageConfig.newBuilder()
                                .label(InsightsImageLabelConfig.newBuilder()
                                        .system(EnableConfig.newBuilder().enable(true).build())
                                        .userDefined(InsightsLabelUserDefinedConfig.newBuilder()
                                                .enable(true)
                                                .mode("Enhanced")
                                                .labels(Arrays.asList(label1, label2))
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();

        String json = jsonMapper.writeValueAsString(config);

        // Verify the full JSON structure matches the spec example
        assertThat(json).contains("\"System\":{\"Enable\":\"true\"}");
        assertThat(json).contains("\"Name\":\"未佩戴安全帽\"");
        assertThat(json).contains("\"Name\":\"存在明火\"");
        assertThat(json).doesNotContain("\"Highlight\"");
    }
}
