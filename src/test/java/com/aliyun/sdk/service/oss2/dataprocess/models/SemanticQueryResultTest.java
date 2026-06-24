package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.dataprocess.transform.SerdeDatasetBasic;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SemanticQueryResultTest {

    @Test
    public void testEmptyBuilder() {
        SemanticQueryResult result = SemanticQueryResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.files()).isNull();
    }

    @Test
    public void testFullBuilder() {
        AudioStream audioStream = AudioStream.newBuilder()
                .index(1L)
                .bitrate(128000L)
                .channelLayout("stereo")
                .channels(2L)
                .codecLongName("AAC (Advanced Audio Coding)")
                .codecName("aac")
                .codecTag("0x6134706d")
                .codecTagString("mp4a")
                .duration(16.021769)
                .frameCount(690L)
                .sampleFormat("fltp")
                .sampleRate(44100L)
                .timeBase("1/44100")
                .build();

        VideoStream videoStream = VideoStream.newBuilder()
                .averageFrameRate("21645000/721493")
                .bitDepth(8L)
                .bitrate(1521221L)
                .codecLongName("H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10")
                .codecName("h264")
                .codecTag("0x31637661")
                .codecTagString("avc1")
                .colorPrimaries("bt709")
                .colorRange("tv")
                .colorSpace("bt709")
                .colorTransfer("bt709")
                .displayAspectRatio("16:9")
                .duration(16.033178)
                .frameCount(481L)
                .frameRate("90000/2999")
                .height(1080L)
                .level(31L)
                .pixelFormat("yuv420p")
                .profile("High")
                .sampleAspectRatio("1:1")
                .timeBase("1/90000")
                .width(1920L)
                .build();

        VideoInsight videoInsight = VideoInsight.newBuilder()
                .caption("蓝衣男走向餐桌")
                .description("这是一段室内高角度监控录像，场景为一个客厅。")
                .build();

        Insights insights = Insights.newBuilder()
                .video(videoInsight)
                .build();

        Map<String, Object> ossTagging = new HashMap<>();
        ossTagging.put("routing-dataset", "test-dataset-sem-vid-1776774492");

        File file = File.newBuilder()
                .addresses(Collections.emptyList())
                .audioCovers(Collections.emptyList())
                .audioStreams(Arrays.asList(audioStream))
                .bitrate(1656706L)
                .contentMd5("5oJccWuBoqVXS8zrzckPlg==")
                .contentType("video/mp4")
                .createTime("2026-04-21T20:28:17.018858947+08:00")
                .croppingSuggestions(Collections.emptyList())
                .datasetName("test-dataset-sem-vid-1776774492")
                .duration(16.034)
                .eTag("\"E6825C716B81A2A5574BCCEBCDC90F96\"")
                .elements(Collections.emptyList())
                .figures(Collections.emptyList())
                .fileHash("E6825C716B81A2A5574BCCEBCDC90F96")
                .fileModifiedTime("2026-04-21T20:28:13+08:00")
                .filename("test-temp/sem-vid-1776774492774503000.mp4")
                .formatLongName("QuickTime / MOV")
                .formatName("mov,mp4,m4a,3gp,3g2,mj2")
                .insights(insights)
                .labels(Collections.emptyList())
                .mediaType("video")
                .ocrContents(Collections.emptyList())
                .ossCrc64("2327801188977127298")
                .ossObjectType("Normal")
                .ossStorageClass("Standard")
                .ossTagging(OSSTagging.newBuilder().tagging(Arrays.asList(
                        new Tagging().toBuilder()
                                .key("routing-dataset")
                                .value("test-dataset-sem-vid-1776774492")
                                .build())).build())
                .ossTaggingCount(1L)
                .objectAcl("default")
                .size(3320455L)
                .streamCount(2L)
                .subtitles(Collections.emptyList())
                .uri("oss://oss-metaquery-dataset-test/test-temp/sem-vid-1776774492774503000.mp4")
                .updateTime("2026-04-21T20:28:27.359034257+08:00")
                .videoHeight(1080L)
                .videoStreams(Arrays.asList(videoStream))
                .videoWidth(1920L)
                .build();

        SemanticQueryResponseBody responseBody = new SemanticQueryResponseBody();
        SemanticQueryResult result = SemanticQueryResult.newBuilder()
                .innerBody(responseBody)
                .build();

        assertThat(result.files()).isNull();
    }

    @Test
    public void testToBuilderPreserveState() {
        SemanticQueryResult original = SemanticQueryResult.newBuilder()
                .build();

        SemanticQueryResult copy = original.toBuilder().build();

        assertThat(copy.headers()).isNotNull();
    }

    @Test
    public void testXmlBuilder() throws JsonProcessingException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SemanticQueryResponse>\n" +
                "    <Files>\n" +
                "        <File>\n" +
                "            <Addresses/>\n" +
                "            <AudioCovers/>\n" +
                "            <AudioStreams>\n" +
                "                <AudioStream>\n" +
                "                    <Bitrate>128000</Bitrate>\n" +
                "                    <ChannelLayout>stereo</ChannelLayout>\n" +
                "                    <Channels>2</Channels>\n" +
                "                    <CodecLongName>AAC (Advanced Audio Coding)</CodecLongName>\n" +
                "                    <CodecName>aac</CodecName>\n" +
                "                    <CodecTag>0x6134706d</CodecTag>\n" +
                "                    <CodecTagString>mp4a</CodecTagString>\n" +
                "                    <Duration>16.021769</Duration>\n" +
                "                    <FrameCount>690</FrameCount>\n" +
                "                    <Index>1</Index>\n" +
                "                    <SampleFormat>fltp</SampleFormat>\n" +
                "                    <SampleRate>44100</SampleRate>\n" +
                "                    <TimeBase>1/44100</TimeBase>\n" +
                "                </AudioStream>\n" +
                "            </AudioStreams>\n" +
                "            <Bitrate>1656706</Bitrate>\n" +
                "            <ContentMd5>5oJccWuBoqVXS8zrzckPlg==</ContentMd5>\n" +
                "            <ContentType>video/mp4</ContentType>\n" +
                "            <CreateTime>2026-04-21T20:28:17.018858947+08:00</CreateTime>\n" +
                "            <CroppingSuggestions/>\n" +
                "            <DatasetName>test-dataset-sem-vid-1776774492</DatasetName>\n" +
                "            <Duration>16.034</Duration>\n" +
                "            <ETag>\"E6825C716B81A2A5574BCCEBCDC90F96\"</ETag>\n" +
                "            <Elements/>\n" +
                "            <Figures/>\n" +
                "            <FileHash>E6825C716B81A2A5574BCCEBCDC90F96</FileHash>\n" +
                "            <FileModifiedTime>2026-04-21T20:28:13+08:00</FileModifiedTime>\n" +
                "            <Filename>test-temp/sem-vid-1776774492774503000.mp4</Filename>\n" +
                "            <FormatLongName>QuickTime / MOV</FormatLongName>\n" +
                "            <FormatName>mov,mp4,m4a,3gp,3g2,mj2</FormatName>\n" +
                "            <Insights>\n" +
                "                <Video>\n" +
                "                    <Caption>蓝衣男走向餐桌</Caption>\n" +
                "                    <Description>这是一段室内高角度监控录像，场景为一个客厅。</Description>\n" +
                "                </Video>\n" +
                "            </Insights>\n" +
                "            <Labels/>\n" +
                "            <MediaType>video</MediaType>\n" +
                "            <OCRContents/>\n" +
                "            <OSSCRC64>2327801188977127298</OSSCRC64>\n" +
                "            <OSSObjectType>Normal</OSSObjectType>\n" +
                "            <OSSStorageClass>Standard</OSSStorageClass>\n" +
                "            <OSSTagging>\n" +
                "                <routing-dataset>test-dataset-sem-vid-1776774492</routing-dataset>\n" +
                "            </OSSTagging>\n" +
                "            <OSSTaggingCount>1</OSSTaggingCount>\n" +
                "            <ObjectACL>default</ObjectACL>\n" +
                "            <SequenceNumber>2</SequenceNumber>\n" +
                "            <SemanticSimilarity>0.5583347777557373</SemanticSimilarity>\n" +
                "            <Size>3320455</Size>\n" +
                "            <SmartClusters/>\n" +
                "            <StreamCount>2</StreamCount>\n" +
                "            <Subtitles/>\n" +
                "            <URI>oss://oss-metaquery-dataset-test/test-temp/sem-vid-1776774492774503000.mp4</URI>\n" +
                "            <UpdateTime>2026-04-21T20:28:27.359034257+08:00</UpdateTime>\n" +
                "            <VideoHeight>1080</VideoHeight>\n" +
                "            <VideoStreams>\n" +
                "                <VideoStream>\n" +
                "                    <AverageFrameRate>21645000/721493</AverageFrameRate>\n" +
                "                    <BitDepth>8</BitDepth>\n" +
                "                    <Bitrate>1521221</Bitrate>\n" +
                "                    <CodecLongName>H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10</CodecLongName>\n" +
                "                    <CodecName>h264</CodecName>\n" +
                "                    <CodecTag>0x31637661</CodecTag>\n" +
                "                    <CodecTagString>avc1</CodecTagString>\n" +
                "                    <ColorPrimaries>bt709</ColorPrimaries>\n" +
                "                    <ColorRange>tv</ColorRange>\n" +
                "                    <ColorSpace>bt709</ColorSpace>\n" +
                "                    <ColorTransfer>bt709</ColorTransfer>\n" +
                "                    <DisplayAspectRatio>16:9</DisplayAspectRatio>\n" +
                "                    <Duration>16.033178</Duration>\n" +
                "                    <FrameCount>481</FrameCount>\n" +
                "                    <FrameRate>90000/2999</FrameRate>\n" +
                "                    <Height>1080</Height>\n" +
                "                    <Level>31</Level>\n" +
                "                    <PixelFormat>yuv420p</PixelFormat>\n" +
                "                    <Profile>High</Profile>\n" +
                "                    <SampleAspectRatio>1:1</SampleAspectRatio>\n" +
                "                    <TimeBase>1/90000</TimeBase>\n" +
                "                    <Width>1920</Width>\n" +
                "                </VideoStream>\n" +
                "            </VideoStreams>\n" +
                "            <VideoWidth>1920</VideoWidth>\n" +
                "        </File>\n" +
                "    </Files>\n" +
                "</SemanticQueryResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        SemanticQueryResult result = SerdeDatasetBasic.toSemanticQuery(output);

        assertThat(result.files()).isNotNull();
        assertThat(result.files()).hasSize(1);

        File file = result.files().get(0);
        assertThat(file.datasetName()).isEqualTo("test-dataset-sem-vid-1776774492");
        assertThat(file.filename()).isEqualTo("test-temp/sem-vid-1776774492774503000.mp4");
        assertThat(file.mediaType()).isEqualTo("video");
        assertThat(file.contentType()).isEqualTo("video/mp4");
        assertThat(file.size()).isEqualTo(3320455L);
        assertThat(file.videoWidth()).isEqualTo(1920L);
        assertThat(file.videoHeight()).isEqualTo(1080L);
        assertThat(file.duration()).isEqualTo(16.034);
        assertThat(file.bitrate()).isEqualTo(1656706L);
        assertThat(file.streamCount()).isEqualTo(2L);
        assertThat(file.ossObjectType()).isEqualTo("Normal");
        assertThat(file.ossStorageClass()).isEqualTo("Standard");
        assertThat(file.objectAcl()).isEqualTo("default");

        assertThat(file.audioStreams()).isNotNull();
        assertThat(file.audioStreams()).hasSize(1);
        AudioStream audioStream = file.audioStreams().get(0);
        assertThat(audioStream.bitrate()).isEqualTo(128000L);
        assertThat(audioStream.channels()).isEqualTo(2L);
        assertThat(audioStream.codecName()).isEqualTo("aac");

        assertThat(file.videoStreams()).isNotNull();
        assertThat(file.videoStreams()).hasSize(1);
        VideoStream videoStream = file.videoStreams().get(0);
        assertThat(videoStream.width()).isEqualTo(1920L);
        assertThat(videoStream.height()).isEqualTo(1080L);
        assertThat(videoStream.codecName()).isEqualTo("h264");
        assertThat(videoStream.profile()).isEqualTo("High");

        assertThat(file.insights()).isNotNull();
        assertThat(file.insights().video()).isNotNull();
        assertThat(file.insights().video().caption()).isEqualTo("蓝衣男走向餐桌");
    }

    @Test
    public void testXmlBuilderWithLabelsAndSceneElements() throws JsonProcessingException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<SemanticQueryResponse>\n" +
                "    <Files>\n" +
                "        <File>\n" +
                "            <Addresses/>\n" +
                "            <AudioCovers/>\n" +
                "            <AudioStreams>\n" +
                "                <AudioStream>\n" +
                "                    <Bitrate>14983</Bitrate>\n" +
                "                    <ChannelLayout>mono</ChannelLayout>\n" +
                "                    <Channels>1</Channels>\n" +
                "                    <CodecLongName>AAC (Advanced Audio Coding)</CodecLongName>\n" +
                "                    <CodecName>aac</CodecName>\n" +
                "                    <CodecTag>0x6134706d</CodecTag>\n" +
                "                    <CodecTagString>mp4a</CodecTagString>\n" +
                "                    <Duration>7.936</Duration>\n" +
                "                    <FrameCount>62</FrameCount>\n" +
                "                    <Index>1</Index>\n" +
                "                    <SampleFormat>fltp</SampleFormat>\n" +
                "                    <SampleRate>8000</SampleRate>\n" +
                "                    <TimeBase>1/8000</TimeBase>\n" +
                "                </AudioStream>\n" +
                "            </AudioStreams>\n" +
                "            <Bitrate>196284</Bitrate>\n" +
                "            <ContentMd5>5/ZLrWYXpuQfDfxEf4+lyA==</ContentMd5>\n" +
                "            <ContentType>video/mp4</ContentType>\n" +
                "            <CreateTime>2026-04-21T10:51:38.264045621+08:00</CreateTime>\n" +
                "            <CroppingSuggestions/>\n" +
                "            <DatasetName>dataset-aianalysis-walk</DatasetName>\n" +
                "            <Duration>8</Duration>\n" +
                "            <ETag>\"E7F64BAD6617A6E41F0DFC447F8FA5C8\"</ETag>\n" +
                "            <Elements/>\n" +
                "            <Figures/>\n" +
                "            <FileHash>E7F64BAD6617A6E41F0DFC447F8FA5C8</FileHash>\n" +
                "            <FileModifiedTime>2026-04-21T10:51:25+08:00</FileModifiedTime>\n" +
                "            <Filename>mp4file/AE09411YAG00081_AE09411YAG00081-0_e723c79f850047458a3e0c0115c4b108_20260421104610825sf0-203372.mp4</Filename>\n" +
                "            <FormatLongName>QuickTime / MOV</FormatLongName>\n" +
                "            <FormatName>mov,mp4,m4a,3gp,3g2,mj2</FormatName>\n" +
                "            <Labels>\n" +
                "                <Label>\n" +
                "                    <LabelConfidence>1</LabelConfidence>\n" +
                "                    <LabelName>有人走过</LabelName>\n" +
                "                    <ParentLabelName>自定义标签</ParentLabelName>\n" +
                "                    <Clips>\n" +
                "                        <Clip>\n" +
                "                            <TimeRange>200</TimeRange>\n" +
                "                            <TimeRange>5533</TimeRange>\n" +
                "                        </Clip>\n" +
                "                    </Clips>\n" +
                "                </Label>\n" +
                "            </Labels>\n" +
                "            <MediaType>video</MediaType>\n" +
                "            <OCRContents/>\n" +
                "            <OSSCRC64>16628192875747293357</OSSCRC64>\n" +
                "            <OSSObjectType>Normal</OSSObjectType>\n" +
                "            <OSSStorageClass>Standard</OSSStorageClass>\n" +
                "            <OSSTagging>\n" +
                "                <alarmId>AE09411YAG0008117767395421908241</alarmId>\n" +
                "                <test-routing-dataset>dataset-aianalysis-walk</test-routing-dataset>\n" +
                "            </OSSTagging>\n" +
                "            <OSSTaggingCount>2</OSSTaggingCount>\n" +
                "            <OSSUserMeta>\n" +
                "                <X-Oss-Meta-Author>oss</X-Oss-Meta-Author>\n" +
                "            </OSSUserMeta>\n" +
                "            <ObjectACL>default</ObjectACL>\n" +
                "            <ProduceTime>2026-04-21T10:46:10+08:00</ProduceTime>\n" +
                "            <SceneElements>\n" +
                "                <SceneElement>\n" +
                "                    <FrameTimes>6000</FrameTimes>\n" +
                "                    <TimeRange>4133</TimeRange>\n" +
                "                    <TimeRange>8533</TimeRange>\n" +
                "                    <VideoStreamIndex>0</VideoStreamIndex>\n" +
                "                    <Labels/>\n" +
                "                </SceneElement>\n" +
                "            </SceneElements>\n" +
                "            <SemanticSimilarity>0.2536</SemanticSimilarity>\n" +
                "            <SequenceNumber>5</SequenceNumber>\n" +
                "            <Size>196284</Size>\n" +
                "            <SmartClusters/>\n" +
                "            <StreamCount>2</StreamCount>\n" +
                "            <Subtitles/>\n" +
                "            <URI>oss://paas-smart-cloud-test/mp4file/AE09411YAG00081_AE09411YAG00081-0_e723c79f850047458a3e0c0115c4b108_20260421104610825sf0-203372.mp4</URI>\n" +
                "            <UpdateTime>2026-04-21T10:52:39.412605575+08:00</UpdateTime>\n" +
                "            <VideoHeight>360</VideoHeight>\n" +
                "            <VideoStreams>\n" +
                "                <VideoStream>\n" +
                "                    <AverageFrameRate>15/1</AverageFrameRate>\n" +
                "                    <BitDepth>8</BitDepth>\n" +
                "                    <Bitrate>178202</Bitrate>\n" +
                "                    <CodecLongName>H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10</CodecLongName>\n" +
                "                    <CodecName>h264</CodecName>\n" +
                "                    <CodecTag>0x31637661</CodecTag>\n" +
                "                    <CodecTagString>avc1</CodecTagString>\n" +
                "                    <Duration>8</Duration>\n" +
                "                    <FrameCount>120</FrameCount>\n" +
                "                    <FrameRate>500/33</FrameRate>\n" +
                "                    <Height>360</Height>\n" +
                "                    <Level>22</Level>\n" +
                "                    <PixelFormat>yuv420p</PixelFormat>\n" +
                "                    <Profile>Main</Profile>\n" +
                "                    <TimeBase>1/1000</TimeBase>\n" +
                "                    <Width>640</Width>\n" +
                "                </VideoStream>\n" +
                "            </VideoStreams>\n" +
                "            <VideoWidth>640</VideoWidth>\n" +
                "        </File>\n" +
                "    </Files>\n" +
                "</SemanticQueryResponse>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();
        SemanticQueryResult result = SerdeDatasetBasic.toSemanticQuery(output);

        assertThat(result.files()).isNotNull();
        assertThat(result.files()).hasSize(1);

        File file = result.files().get(0);
        assertThat(file.datasetName()).isEqualTo("dataset-aianalysis-walk");
        assertThat(file.filename()).isEqualTo("mp4file/AE09411YAG00081_AE09411YAG00081-0_e723c79f850047458a3e0c0115c4b108_20260421104610825sf0-203372.mp4");
        assertThat(file.mediaType()).isEqualTo("video");
        assertThat(file.contentType()).isEqualTo("video/mp4");
        assertThat(file.size()).isEqualTo(196284L);
        assertThat(file.videoWidth()).isEqualTo(640L);
        assertThat(file.videoHeight()).isEqualTo(360L);
        assertThat(file.duration()).isEqualTo(8.0);
        assertThat(file.bitrate()).isEqualTo(196284L);
        assertThat(file.streamCount()).isEqualTo(2L);
        assertThat(file.ossObjectType()).isEqualTo("Normal");
        assertThat(file.ossStorageClass()).isEqualTo("Standard");
        assertThat(file.objectAcl()).isEqualTo("default");
        assertThat(file.produceTime()).isEqualTo("2026-04-21T10:46:10+08:00");

        assertThat(file.audioStreams()).isNotNull();
        assertThat(file.audioStreams()).hasSize(1);
        AudioStream audioStream = file.audioStreams().get(0);
        assertThat(audioStream.bitrate()).isEqualTo(14983L);
        assertThat(audioStream.channels()).isEqualTo(1L);
        assertThat(audioStream.codecName()).isEqualTo("aac");
        assertThat(audioStream.channelLayout()).isEqualTo("mono");
        assertThat(audioStream.sampleRate()).isEqualTo(8000L);

        assertThat(file.videoStreams()).isNotNull();
        assertThat(file.videoStreams()).hasSize(1);
        VideoStream videoStream = file.videoStreams().get(0);
        assertThat(videoStream.width()).isEqualTo(640L);
        assertThat(videoStream.height()).isEqualTo(360L);
        assertThat(videoStream.codecName()).isEqualTo("h264");
        assertThat(videoStream.profile()).isEqualTo("Main");
        assertThat(videoStream.bitrate()).isEqualTo(178202L);
        assertThat(videoStream.frameCount()).isEqualTo(120L);

        assertThat(file.labels()).isNotNull();
        assertThat(file.labels()).hasSize(1);
        Label label = file.labels().get(0);
        assertThat(label.labelName()).isEqualTo("有人走过");
        assertThat(label.parentLabelName()).isEqualTo("自定义标签");
        assertThat(label.labelConfidence()).isEqualTo(1.0f);

        assertThat(file.sceneElements()).isNotNull();
        assertThat(file.sceneElements()).hasSize(1);
        SceneElement sceneElement = file.sceneElements().get(0);
        assertThat(sceneElement.frameTimes()).isEqualTo(Arrays.asList(6000L));
        assertThat(sceneElement.videoStreamIndex()).isEqualTo(0L);
    }
}
