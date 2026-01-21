package com.aliyun.sdk.service.oss2.models;

import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.transform.SerdeBucketMetaquery;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.utils.MapUtils;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DoMetaQueryResultTest {

    @Test
    public void testEmptyBuilder() {
        DoMetaQueryResult result = DoMetaQueryResult.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.headers()).isNotNull();
        assertThat(result.headers().isEmpty()).isTrue();
        assertThat(result.metaQuery()).isNull();
    }

    @Test
    public void testFullBuilder() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-1234567890abcdefg",
                "ETag", "\"B5eJF1ptWaXm4bijSPyxw==\""
        );

        DoMetaQueryResult result = DoMetaQueryResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-1234567890abcdefg");
        assertThat(result.headers().get("ETag")).isEqualTo("\"B5eJF1ptWaXm4bijSPyxw==\"");
        assertThat(result.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(result.statusCode()).isEqualTo(200);
    }

    @Test
    public void testToBuilderPreserveState() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-765432109876543210",
                "ETag", "\"original-etag\""
        );

        DoMetaQueryResult original = DoMetaQueryResult.newBuilder()
                .headers(headers)
                .status("HTTP/1.1 200 OK")
                .statusCode(200)
                .build();

        DoMetaQueryResult copy = original.toBuilder().build();

        assertThat(copy.headers().get("x-oss-request-id")).isEqualTo("req-765432109876543210");
        assertThat(copy.headers().get("ETag")).isEqualTo("\"original-etag\"");
        assertThat(copy.status()).isEqualTo("HTTP/1.1 200 OK");
        assertThat(copy.statusCode()).isEqualTo(200);
    }

    @Test
    public void testHeaderProperties() {
        Map<String, String> headers = MapUtils.of(
                "x-oss-request-id", "req-test-headers",
                "Date", "Mon, 01 Jan 2024 12:00:00 GMT"
        );

        DoMetaQueryResult result = DoMetaQueryResult.newBuilder()
                .headers(headers)
                .build();

        assertThat(result.headers().get("x-oss-request-id")).isEqualTo("req-test-headers");
        assertThat(result.headers().get("Date")).isEqualTo("Mon, 01 Jan 2024 12:00:00 GMT");
    }

    @Test
    public void xmlBuilder() {
        String xml = "" +
                "        <MetaQuery>\n" +
                "          <NextToken>MTIzNDU2Nzg6aW1tdGVzdDpleGFtcGxlYnVja2V0OmRhdGFzZXQwMDE6b3NzOi8vZXhhbXBsZWJ1Y2tldC9zYW1wbGVvYmplY3QxLmpw****</NextToken>\n" +
                "          <Aggregations>\n" +
                "            <Aggregation>\n" +
                "              <Field>Size</Field>\n" +
                "              <Operation>sum</Operation>\n" +
                "              <Value>1000.0</Value>\n" +
                "              <Groups>\n" +
                "                <Group>\n" +
                "                  <Count>5</Count>\n" +
                "                  <Value>large</Value>\n" +
                "                </Group>\n" +
                "              </Groups>\n" +
                "            </Aggregation>\n" +
                "          </Aggregations>\n" +
                "          <Files>\n" +
                "            <File>\n" +
                "              <Filename>exampleobject.txt</Filename>\n" +
                "              <Size>120</Size>\n" +
                "              <FileModifiedTime>2021-06-29T15:04:05.000000000Z07:00</FileModifiedTime>\n" +
                "              <OSSObjectType>Normal</OSSObjectType>\n" +
                "              <OSSStorageClass>Standard</OSSStorageClass>\n" +
                "              <ObjectACL>default</ObjectACL>\n" +
                "              <ETag>\"fba9dede5f27731c9771645a3986****\"</ETag>\n" +
                "              <OSSCRC64>4858A48BD1466884</OSSCRC64>\n" +
                "              <OSSTaggingCount>2</OSSTaggingCount>\n" +
                "              <OSSTagging>\n" +
                "                <Tagging>\n" +
                "                  <Key>owner</Key>\n" +
                "                  <Value>John</Value>\n" +
                "                </Tagging>\n" +
                "                <Tagging>\n" +
                "                  <Key>type</Key>\n" +
                "                  <Value>document</Value>\n" +
                "                </Tagging>\n" +
                "              </OSSTagging>\n" +
                "              <OSSUserMeta>\n" +
                "                <UserMeta>\n" +
                "                  <Key>x-oss-meta-location</Key>\n" +
                "                  <Value>hangzhou</Value>\n" +
                "                </UserMeta>\n" +
                "              </OSSUserMeta>\n" +
                "            </File>\n" +
                "          </Files>\n" +
                "        </MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();

        DoMetaQueryResult result = SerdeBucketMetaquery.toDoMetaQuery(output);

        assertThat(result).isNotNull();
        assertThat(result.metaQuery()).isNotNull();

        // Validate NextToken
        assertThat(result.metaQuery().nextToken()).isEqualTo("MTIzNDU2Nzg6aW1tdGVzdDpleGFtcGxlYnVja2V0OmRhdGFzZXQwMDE6b3NzOi8vZXhhbXBsZWJ1Y2tldC9zYW1wbGVvYmplY3QxLmpw****");

        // Validate Aggregations structure
        assertThat(result.metaQuery().aggregations()).isNotNull();
        assertThat(result.metaQuery().aggregations().aggregation()).isNotNull();
        assertThat(result.metaQuery().aggregations().aggregation()).hasSize(1);

        MetaQueryAggregation aggregation = result.metaQuery().aggregations().aggregation().get(0);
        assertThat(aggregation.field()).isEqualTo("Size");
        assertThat(aggregation.operation()).isEqualTo("sum");
        assertThat(aggregation.value()).isEqualTo(1000.0);

        // Validate Groups structure
        assertThat(aggregation.groups()).isNotNull();
        assertThat(aggregation.groups().group()).isNotNull();
        assertThat(aggregation.groups().group()).hasSize(1);

        MetaQueryGroup group = aggregation.groups().group().get(0);
        assertThat(group.count()).isEqualTo(5L);
        assertThat(group.value()).isEqualTo("large");

        // Validate Files structure
        assertThat(result.metaQuery().files()).isNotNull();
        assertThat(result.metaQuery().files().file()).isNotNull();
        assertThat(result.metaQuery().files().file()).isNotEmpty();
        assertThat(result.metaQuery().files().file().size()).isEqualTo(1);

        // Validate all fields of the first file
        MetaQueryFile file = result.metaQuery().files().file().get(0);
        assertThat(file.filename()).isEqualTo("exampleobject.txt");
        assertThat(file.size()).isEqualTo(120);
        assertThat(file.fileModifiedTime()).isEqualTo("2021-06-29T15:04:05.000000000Z07:00");
        assertThat(file.ossObjectType()).isEqualTo("Normal");
        assertThat(file.ossStorageClass()).isEqualTo("Standard");
        assertThat(file.objectACL()).isEqualTo("default");
        assertThat(file.eTag()).isEqualTo("\"fba9dede5f27731c9771645a3986****\"");
        assertThat(file.ossCRC64()).isEqualTo("4858A48BD1466884");
        assertThat(file.ossTaggingCount()).isEqualTo(2);

        // Validate tagging information
        assertThat(file.ossTagging()).isNotNull();
        assertThat(file.ossTagging().tagging()).isNotNull();
        assertThat(file.ossTagging().tagging()).hasSize(2);

        assertThat(file.ossTagging().tagging().get(0).key()).isEqualTo("owner");
        assertThat(file.ossTagging().tagging().get(0).value()).isEqualTo("John");
        assertThat(file.ossTagging().tagging().get(1).key()).isEqualTo("type");
        assertThat(file.ossTagging().tagging().get(1).value()).isEqualTo("document");

        // Validate user-defined metadata
        assertThat(file.ossUserMeta()).isNotNull();
        assertThat(file.ossUserMeta().userMeta()).isNotNull();
        assertThat(file.ossUserMeta().userMeta()).hasSize(1);

        assertThat(file.ossUserMeta().userMeta().get(0).key()).isEqualTo("x-oss-meta-location");
        assertThat(file.ossUserMeta().userMeta().get(0).value()).isEqualTo("hangzhou");
    }

    @Test
    public void xmlBuilderSemantic() {
        String xml = "" +
                "<MetaQuery>\n" +
                "          <Files>\n" +
                "            <File>\n" +
                "              <URI>oss://examplebucket/test-object.jpg</URI>\n" +
                "              <Filename>sampleobject.jpg</Filename>\n" +
                "              <Size>1000</Size>\n" +
                "              <ObjectACL>default</ObjectACL>\n" +
                "              <FileModifiedTime>2021-06-29T14:50:14.011643661+08:00</FileModifiedTime>\n" +
                "              <ServerSideEncryption>AES256</ServerSideEncryption>\n" +
                "              <ServerSideEncryptionCustomerAlgorithm>SM4</ServerSideEncryptionCustomerAlgorithm>\n" +
                "              <ETag>\"1D9C280A7C4F67F7EF873E28449****\"</ETag>\n" +
                "              <OSSCRC64>559890638950338001</OSSCRC64>\n" +
                "              <ProduceTime>2021-06-29T14:50:15.011643661+08:00</ProduceTime>\n" +
                "              <ContentType>image/jpeg</ContentType>\n" +
                "              <MediaType>image</MediaType>\n" +
                "              <LatLong>30.134390,120.074997</LatLong>\n" +
                "              <Title>test</Title>\n" +
                "              <OSSExpiration>2024-12-01T12:00:00.000Z</OSSExpiration>\n" +
                "              <AccessControlAllowOrigin>https://aliyundoc.com</AccessControlAllowOrigin>\n" +
                "              <AccessControlRequestMethod>PUT</AccessControlRequestMethod>\n" +
                "              <ServerSideDataEncryption>SM4</ServerSideDataEncryption>\n" +
                "              <ServerSideEncryptionKeyId>9468da86-3509-4f8d-a61e-6eab1eac****</ServerSideEncryptionKeyId>\n" +
                "              <CacheControl>no-cache</CacheControl>\n" +
                "              <ContentDisposition>attachment; filename =test.jpg</ContentDisposition>\n" +
                "              <ContentEncoding>UTF-8</ContentEncoding>\n" +
                "              <ContentLanguage>zh-CN</ContentLanguage>\n" +
                "              <ImageHeight>500</ImageHeight>\n" +
                "              <ImageWidth>270</ImageWidth>\n" +
                "              <VideoWidth>1080</VideoWidth>\n" +
                "              <VideoHeight>1920</VideoHeight>\n" +
                "              <VideoStreams>\n" +
                "                <VideoStream>\n" +
                "                  <CodecName>h264</CodecName>\n" +
                "                  <Language>en</Language>\n" +
                "                  <Bitrate>5407765</Bitrate>\n" +
                "                  <FrameRate>25/1</FrameRate>\n" +
                "                  <StartTime>0</StartTime>\n" +
                "                  <Duration>22.88</Duration>\n" +
                "                  <FrameCount>572</FrameCount>\n" +
                "                  <BitDepth>8</BitDepth>\n" +
                "                  <PixelFormat>yuv420p</PixelFormat>\n" +
                "                  <ColorSpace>bt709</ColorSpace>\n" +
                "                  <Height>720</Height>\n" +
                "                  <Width>1280</Width>\n" +
                "                </VideoStream>\n" +
                "                <VideoStream>\n" +
                "                  <CodecName>h264</CodecName>\n" +
                "                  <Language>en</Language>\n" +
                "                  <Bitrate>5407765</Bitrate>\n" +
                "                  <FrameRate>25/1</FrameRate>\n" +
                "                  <StartTime>0</StartTime>\n" +
                "                  <Duration>22.88</Duration>\n" +
                "                  <FrameCount>572</FrameCount>\n" +
                "                  <BitDepth>8</BitDepth>\n" +
                "                  <PixelFormat>yuv420p</PixelFormat>\n" +
                "                  <ColorSpace>bt709</ColorSpace>\n" +
                "                  <Height>720</Height>\n" +
                "                  <Width>1280</Width>\n" +
                "                </VideoStream>\n" +
                "              </VideoStreams>\n" +
                "              <AudioStreams>\n" +
                "                <AudioStream>\n" +
                "                  <CodecName>aac</CodecName>\n" +
                "                  <Bitrate>1048576</Bitrate>\n" +
                "                  <SampleRate>48000</SampleRate>\n" +
                "                  <StartTime>0.0235</StartTime>\n" +
                "                  <Duration>3.690667</Duration>\n" +
                "                  <Channels>2</Channels>\n" +
                "                  <Language>en</Language>\n" +
                "                </AudioStream>\n" +
                "              </AudioStreams>\n" +
                "              <Subtitles>\n" +
                "                <Subtitle>\n" +
                "                  <CodecName>mov_text</CodecName>\n" +
                "                  <Language>en</Language>\n" +
                "                  <StartTime>0</StartTime>\n" +
                "                  <Duration>71.378</Duration>\n" +
                "                </Subtitle>\n" +
                "                <Subtitle>\n" +
                "                  <CodecName>mov_text</CodecName>\n" +
                "                  <Language>en</Language>\n" +
                "                  <StartTime>72</StartTime>\n" +
                "                  <Duration>71.378</Duration>\n" +
                "                </Subtitle>\n" +
                "              </Subtitles>\n" +
                "              <Bitrate>5407765</Bitrate>\n" +
                "              <Artist>Jane</Artist>\n" +
                "              <AlbumArtist>Jenny</AlbumArtist>\n" +
                "              <Composer>Jane</Composer>\n" +
                "              <Performer>Jane</Performer>\n" +
                "              <Album>FirstAlbum</Album>\n" +
                "              <Duration>71.378</Duration>\n" +
                "              <Addresses>\n" +
                "                <Address>\n" +
                "                  <AddressLine>中国浙江省杭州市余杭区文一西路969号</AddressLine>\n" +
                "                  <City>杭州市</City>\n" +
                "                  <Country>中国</Country>\n" +
                "                  <District>余杭区</District>\n" +
                "                  <Language>zh-Hans</Language>\n" +
                "                  <Province>浙江省</Province>\n" +
                "                  <Township>文一西路</Township>\n" +
                "                </Address>\n" +
                "                <Address>\n" +
                "                  <AddressLine>中国浙江省杭州市余杭区文一西路970号</AddressLine>\n" +
                "                  <City>杭州市</City>\n" +
                "                  <Country>中国</Country>\n" +
                "                  <District>余杭区</District>\n" +
                "                  <Language>zh-Hans</Language>\n" +
                "                  <Province>浙江省</Province>\n" +
                "                  <Township>文一西路</Township>\n" +
                "                </Address>\n" +
                "              </Addresses>\n" +
                "              <OSSObjectType>Normal</OSSObjectType>\n" +
                "              <OSSStorageClass>Standard</OSSStorageClass>\n" +
                "              <OSSTaggingCount>2</OSSTaggingCount>\n" +
                "              <OSSTagging>\n" +
                "                <Tagging>\n" +
                "                  <Key>key</Key>\n" +
                "                  <Value>val</Value>\n" +
                "                </Tagging>\n" +
                "                <Tagging>\n" +
                "                  <Key>key2</Key>\n" +
                "                  <Value>val2</Value>\n" +
                "                </Tagging>\n" +
                "              </OSSTagging>\n" +
                "              <OSSUserMeta>\n" +
                "                <UserMeta>\n" +
                "                  <Key>key</Key>\n" +
                "                  <Value>val</Value>\n" +
                "                </UserMeta>\n" +
                "              </OSSUserMeta>\n" +
                "              <Insights>\n" +
                "                <Video>\n" +
                "                  <Caption>hand holding shampoo</Caption>\n" +
                "                  <Description>The video shows two different scenes: one with a stationary white plate, black bottle, and transparent glass cup, and another of a hand holding a shampoo bottle labeled \"YEZOLU\" moving slowly upward in the bathroom</Description>\n" +
                "                </Video>\n" +
                "                <Image>\n" +
                "                  <Caption>person standing</Caption>\n" +
                "                  <Description>Picture shows a person wearing a dark-colored suit jacket with a white shirt underneath. The background is a gradient from light blue to gray</Description>\n" +
                "                </Image>\n" +
                "              </Insights>\n" +
                "            </File>\n" +
                "            <File>\n" +
                "                  <AlbumArtist>Jenny</AlbumArtist>\n" +
                "                  <Composer>Jane</Composer>\n" +
                "                  <Performer>Jane</Performer>\n" +
                "                  <Album>FirstAlbum</Album>\n" +
                "            </File>\n" +
                "          </Files>\n" +
                "        </MetaQuery>";

        OperationOutput output = OperationOutput.newBuilder()
                .body(BinaryData.fromString(xml))
                .build();

        DoMetaQueryResult result = SerdeBucketMetaquery.toDoMetaQuery(output);

        assertThat(result).isNotNull();
        assertThat(result.metaQuery()).isNotNull();
        assertThat(result.metaQuery().files()).isNotNull();
        assertThat(result.metaQuery().files().file()).isNotNull();
        assertThat(result.metaQuery().files().file()).isNotEmpty();
        assertThat(result.metaQuery().files().file().size()).isEqualTo(2);

        // Validate all fields of the first file
        MetaQueryFile firstFile = result.metaQuery().files().file().get(0);
        assertThat(firstFile.uri()).isEqualTo("oss://examplebucket/test-object.jpg");
        assertThat(firstFile.filename()).isEqualTo("sampleobject.jpg");
        assertThat(firstFile.size()).isEqualTo(1000);
        assertThat(firstFile.objectACL()).isEqualTo("default");
        assertThat(firstFile.fileModifiedTime()).isEqualTo("2021-06-29T14:50:14.011643661+08:00");
        assertThat(firstFile.serverSideEncryption()).isEqualTo("AES256");
        assertThat(firstFile.serverSideEncryptionCustomerAlgorithm()).isEqualTo("SM4");
        assertThat(firstFile.eTag()).isEqualTo("\"1D9C280A7C4F67F7EF873E28449****\"");
        assertThat(firstFile.ossCRC64()).isEqualTo("559890638950338001");
        assertThat(firstFile.produceTime()).isEqualTo("2021-06-29T14:50:15.011643661+08:00");
        assertThat(firstFile.contentType()).isEqualTo("image/jpeg");
        assertThat(firstFile.mediaType()).isEqualTo("image");
        assertThat(firstFile.latLong()).isEqualTo("30.134390,120.074997");
        assertThat(firstFile.title()).isEqualTo("test");
        assertThat(firstFile.ossExpiration()).isEqualTo("2024-12-01T12:00:00.000Z");
        assertThat(firstFile.accessControlAllowOrigin()).isEqualTo("https://aliyundoc.com");
        assertThat(firstFile.accessControlRequestMethod()).isEqualTo("PUT");
        assertThat(firstFile.serverSideDataEncryption()).isEqualTo("SM4");
        assertThat(firstFile.serverSideEncryptionKeyId()).isEqualTo("9468da86-3509-4f8d-a61e-6eab1eac****");
        assertThat(firstFile.cacheControl()).isEqualTo("no-cache");
        assertThat(firstFile.contentDisposition()).isEqualTo("attachment; filename =test.jpg");
        assertThat(firstFile.contentEncoding()).isEqualTo("UTF-8");
        assertThat(firstFile.contentLanguage()).isEqualTo("zh-CN");
        assertThat(firstFile.imageHeight()).isEqualTo(500);
        assertThat(firstFile.imageWidth()).isEqualTo(270);
        assertThat(firstFile.videoWidth()).isEqualTo(1080);
        assertThat(firstFile.videoHeight()).isEqualTo(1920);
        assertThat(firstFile.bitrate()).isEqualTo(5407765);
        assertThat(firstFile.artist()).isEqualTo("Jane");
        assertThat(firstFile.albumArtist()).isEqualTo("Jenny");
        assertThat(firstFile.composer()).isEqualTo("Jane");
        assertThat(firstFile.performer()).isEqualTo("Jane");
        assertThat(firstFile.album()).isEqualTo("FirstAlbum");
        assertThat(firstFile.duration()).isEqualTo(71.378);
        assertThat(firstFile.ossObjectType()).isEqualTo("Normal");
        assertThat(firstFile.ossStorageClass()).isEqualTo("Standard");
        assertThat(firstFile.ossTaggingCount()).isEqualTo(2);

        // Validate video streams information
        assertThat(firstFile.videoStreams()).isNotNull();
        assertThat(firstFile.videoStreams().videoStream()).isNotNull();
        assertThat(firstFile.videoStreams().videoStream()).hasSize(2);

        MetaQueryVideoStream videoStream1 = firstFile.videoStreams().videoStream().get(0);
        assertThat(videoStream1.codecName()).isEqualTo("h264");
        assertThat(videoStream1.language()).isEqualTo("en");
        assertThat(videoStream1.bitrate()).isEqualTo(5407765);
        assertThat(videoStream1.frameRate()).isEqualTo("25/1");
        assertThat(videoStream1.startTime()).isEqualTo(0);
        assertThat(videoStream1.duration()).isEqualTo(22.88);
        assertThat(videoStream1.frameCount()).isEqualTo(572);
        assertThat(videoStream1.bitDepth()).isEqualTo(8);
        assertThat(videoStream1.pixelFormat()).isEqualTo("yuv420p");
        assertThat(videoStream1.colorSpace()).isEqualTo("bt709");
        assertThat(videoStream1.height()).isEqualTo(720);
        assertThat(videoStream1.width()).isEqualTo(1280);

        MetaQueryVideoStream videoStream2 = firstFile.videoStreams().videoStream().get(1);
        assertThat(videoStream2.codecName()).isEqualTo("h264");
        assertThat(videoStream2.language()).isEqualTo("en");
        assertThat(videoStream2.bitrate()).isEqualTo(5407765);
        assertThat(videoStream2.frameRate()).isEqualTo("25/1");
        assertThat(videoStream2.startTime()).isEqualTo(0);
        assertThat(videoStream2.duration()).isEqualTo(22.88);
        assertThat(videoStream2.frameCount()).isEqualTo(572);
        assertThat(videoStream2.bitDepth()).isEqualTo(8);
        assertThat(videoStream2.pixelFormat()).isEqualTo("yuv420p");
        assertThat(videoStream2.colorSpace()).isEqualTo("bt709");
        assertThat(videoStream2.height()).isEqualTo(720);
        assertThat(videoStream2.width()).isEqualTo(1280);

        // Validate audio streams information
        assertThat(firstFile.audioStreams()).isNotNull();
        assertThat(firstFile.audioStreams().audioStream()).isNotNull();
        assertThat(firstFile.audioStreams().audioStream()).hasSize(1);

        MetaQueryAudioStream audioStream = firstFile.audioStreams().audioStream().get(0);
        assertThat(audioStream.codecName()).isEqualTo("aac");
        assertThat(audioStream.bitrate()).isEqualTo(1048576);
        assertThat(audioStream.sampleRate()).isEqualTo(48000);
        assertThat(audioStream.startTime()).isEqualTo(0.0235);
        assertThat(audioStream.duration()).isEqualTo(3.690667);
        assertThat(audioStream.channels()).isEqualTo(2);
        assertThat(audioStream.language()).isEqualTo("en");

        // Validate subtitles information
        assertThat(firstFile.subtitles()).isNotNull();
        assertThat(firstFile.subtitles().subtitle()).isNotNull();
        assertThat(firstFile.subtitles().subtitle()).hasSize(2);

        MetaQuerySubtitle subtitle1 = firstFile.subtitles().subtitle().get(0);
        assertThat(subtitle1.codecName()).isEqualTo("mov_text");
        assertThat(subtitle1.language()).isEqualTo("en");
        assertThat(subtitle1.startTime()).isEqualTo(0);
        assertThat(subtitle1.duration()).isEqualTo(71.378);

        MetaQuerySubtitle subtitle2 = firstFile.subtitles().subtitle().get(1);
        assertThat(subtitle2.codecName()).isEqualTo("mov_text");
        assertThat(subtitle2.language()).isEqualTo("en");
        assertThat(subtitle2.startTime()).isEqualTo(72);
        assertThat(subtitle2.duration()).isEqualTo(71.378);

        // Validate addresses information
        assertThat(firstFile.addresses()).isNotNull();
        assertThat(firstFile.addresses().address()).isNotNull();
        assertThat(firstFile.addresses().address()).hasSize(2);

        MetaQueryAddress address1 = firstFile.addresses().address().get(0);
        assertThat(address1.addressLine()).isEqualTo("中国浙江省杭州市余杭区文一西路969号");
        assertThat(address1.city()).isEqualTo("杭州市");
        assertThat(address1.country()).isEqualTo("中国");
        assertThat(address1.district()).isEqualTo("余杭区");
        assertThat(address1.language()).isEqualTo("zh-Hans");
        assertThat(address1.province()).isEqualTo("浙江省");
        assertThat(address1.township()).isEqualTo("文一西路");

        MetaQueryAddress address2 = firstFile.addresses().address().get(1);
        assertThat(address2.addressLine()).isEqualTo("中国浙江省杭州市余杭区文一西路970号");
        assertThat(address2.city()).isEqualTo("杭州市");
        assertThat(address2.country()).isEqualTo("中国");
        assertThat(address2.district()).isEqualTo("余杭区");
        assertThat(address2.language()).isEqualTo("zh-Hans");
        assertThat(address2.province()).isEqualTo("浙江省");
        assertThat(address2.township()).isEqualTo("文一西路");

        // Validate tagging information
        assertThat(firstFile.ossTagging()).isNotNull();
        assertThat(firstFile.ossTagging().tagging()).isNotNull();
        assertThat(firstFile.ossTagging().tagging()).hasSize(2);

        assertThat(firstFile.ossTagging().tagging().get(0).key()).isEqualTo("key");
        assertThat(firstFile.ossTagging().tagging().get(0).value()).isEqualTo("val");
        assertThat(firstFile.ossTagging().tagging().get(1).key()).isEqualTo("key2");
        assertThat(firstFile.ossTagging().tagging().get(1).value()).isEqualTo("val2");

        // Validate user-defined metadata
        assertThat(firstFile.ossUserMeta()).isNotNull();
        assertThat(firstFile.ossUserMeta().userMeta()).isNotNull();
        assertThat(firstFile.ossUserMeta().userMeta()).hasSize(1);

        assertThat(firstFile.ossUserMeta().userMeta().get(0).key()).isEqualTo("key");
        assertThat(firstFile.ossUserMeta().userMeta().get(0).value()).isEqualTo("val");

        // Validate insights information
        assertThat(firstFile.insights()).isNotNull();
        assertThat(firstFile.insights().video()).isNotNull();
        assertThat(firstFile.insights().image()).isNotNull();

        // Validate video insights
        assertThat(firstFile.insights().video().caption()).isEqualTo("hand holding shampoo");
        assertThat(firstFile.insights().video().description()).isEqualTo("The video shows two different scenes: one with a stationary white plate, black bottle, and transparent glass cup, and another of a hand holding a shampoo bottle labeled \"YEZOLU\" moving slowly upward in the bathroom");

        // Validate image insights
        assertThat(firstFile.insights().image().caption()).isEqualTo("person standing");
        assertThat(firstFile.insights().image().description()).isEqualTo("Picture shows a person wearing a dark-colored suit jacket with a white shirt underneath. The background is a gradient from light blue to gray");

        // Validate fields of the second file
        MetaQueryFile secondFile = result.metaQuery().files().file().get(1);
        assertThat(secondFile.albumArtist()).isEqualTo("Jenny");
        assertThat(secondFile.composer()).isEqualTo("Jane");
        assertThat(secondFile.performer()).isEqualTo("Jane");
        assertThat(secondFile.album()).isEqualTo("FirstAlbum");

        // Validate that the second file doesn't have insights (null check)
        assertThat(secondFile.insights()).isNull();
    }
}