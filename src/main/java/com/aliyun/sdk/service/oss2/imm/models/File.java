package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class File {

    @JsonProperty("OwnerId")
    private String ownerId;

    @JsonProperty("DatasetName")
    private String datasetName;

    @JsonProperty("ObjectType")
    private String objectType;

    @JsonProperty("ObjectId")
    private String objectId;

    @JsonProperty("UpdateTime")
    private String updateTime;

    @JsonProperty("CreateTime")
    private String createTime;

    @JsonProperty("URI")
    private String uri;

    @JsonProperty("OSSURI")
    private String ossUri;

    @JsonProperty("Filename")
    private String filename;

    @JsonProperty("MediaType")
    private String mediaType;

    @JsonProperty("ContentType")
    private String contentType;

    @JsonProperty("Size")
    private Long size;

    @JsonProperty("FileHash")
    private String fileHash;

    @JsonProperty("FileModifiedTime")
    private String fileModifiedTime;

    @JsonProperty("FileCreateTime")
    private String fileCreateTime;

    @JsonProperty("FileAccessTime")
    private String fileAccessTime;

    @JsonProperty("ProduceTime")
    private String produceTime;

    @JsonProperty("LatLong")
    private String latLong;

    @JsonProperty("Timezone")
    private String timezone;

    @JsonProperty("Addresses")
    private List<Address> addresses;

    @JsonProperty("TravelClusterId")
    private String travelClusterId;

    @JsonProperty("Orientation")
    private Long orientation;

    @JsonProperty("Figures")
    private List<Figure> figures;

    @JsonProperty("FigureCount")
    private Long figureCount;

    @JsonProperty("Labels")
    private List<Label> labels;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("ImageWidth")
    private Long imageWidth;

    @JsonProperty("ImageHeight")
    private Long imageHeight;

    @JsonProperty("EXIF")
    private String exif;

    @JsonProperty("ImageScore")
    private ImageScore imageScore;

    @JsonProperty("CroppingSuggestions")
    private List<CroppingSuggestion> croppingSuggestions;

    @JsonProperty("OCRContents")
    private List<OCRContents> ocrContents;

    @JsonProperty("VideoWidth")
    private Long videoWidth;

    @JsonProperty("VideoHeight")
    private Long videoHeight;

    @JsonProperty("VideoStreams")
    private List<VideoStream> videoStreams;

    @JsonProperty("Subtitles")
    private List<SubtitleStream> subtitles;

    @JsonProperty("AudioStreams")
    private List<AudioStream> audioStreams;

    @JsonProperty("Artist")
    private String artist;

    @JsonProperty("AlbumArtist")
    private String albumArtist;

    @JsonProperty("AudioCovers")
    private List<Image> audioCovers;

    @JsonProperty("Composer")
    private String composer;

    @JsonProperty("Performer")
    private String performer;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Album")
    private String album;

    @JsonProperty("PageCount")
    private Long pageCount;

    @JsonProperty("ETag")
    private String eTag;

    @JsonProperty("CacheControl")
    private String cacheControl;

    @JsonProperty("ContentDisposition")
    private String contentDisposition;

    @JsonProperty("ContentEncoding")
    private String contentEncoding;

    @JsonProperty("ContentLanguage")
    private String contentLanguage;

    @JsonProperty("AccessControlAllowOrigin")
    private String accessControlAllowOrigin;

    @JsonProperty("AccessControlRequestMethod")
    private String accessControlRequestMethod;

    @JsonProperty("ServerSideEncryptionCustomerAlgorithm")
    private String serverSideEncryptionCustomerAlgorithm;

    @JsonProperty("ServerSideEncryption")
    private String serverSideEncryption;

    @JsonProperty("ServerSideDataEncryption")
    private String serverSideDataEncryption;

    @JsonProperty("ServerSideEncryptionKeyId")
    private String serverSideEncryptionKeyId;

    @JsonProperty("OSSStorageClass")
    private String ossStorageClass;

    @JsonProperty("OSSCRC64")
    private String ossCrc64;

    @JsonProperty("ObjectACL")
    private String objectAcl;

    @JsonProperty("ContentMd5")
    private String contentMd5;

    @JsonProperty("OSSUserMeta")
    private Map<String, Object> ossUserMeta;

    @JsonProperty("OSSTaggingCount")
    private Long ossTaggingCount;

    @JsonProperty("OSSTagging")
    private Map<String, Object> ossTagging;

    @JsonProperty("OSSExpiration")
    private String ossExpiration;

    @JsonProperty("OSSVersionId")
    private String ossVersionId;

    @JsonProperty("OSSDeleteMarker")
    private String ossDeleteMarker;

    @JsonProperty("OSSObjectType")
    private String ossObjectType;

    @JsonProperty("CustomId")
    private String customId;

    @JsonProperty("CustomLabels")
    private Map<String, Object> customLabels;

    @JsonProperty("StreamCount")
    private Long streamCount;

    @JsonProperty("ProgramCount")
    private Long programCount;

    @JsonProperty("FormatName")
    private String formatName;

    @JsonProperty("FormatLongName")
    private String formatLongName;

    @JsonProperty("StartTime")
    private Double startTime;

    @JsonProperty("Bitrate")
    private Long bitrate;

    @JsonProperty("Duration")
    private Double duration;

    @JsonProperty("SemanticTypes")
    private List<String> semanticTypes;

    @JsonProperty("Elements")
    private List<Element> elements;

    @JsonProperty("SceneElements")
    private List<SceneElement> sceneElements;

    @JsonProperty("OCRTexts")
    private String ocrTexts;

    @JsonProperty("Reason")
    private String reason;

    @JsonProperty("ObjectStatus")
    private String objectStatus;

    @JsonProperty("Insights")
    private Insights insights;

    public File() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public File setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public File setDatasetName(String datasetName) {
        this.datasetName = datasetName;
        return this;
    }

    public String getObjectType() {
        return objectType;
    }

    public File setObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public File setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public File setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public File setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public File setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getOssUri() {
        return ossUri;
    }

    public File setOssUri(String ossUri) {
        this.ossUri = ossUri;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public File setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getMediaType() {
        return mediaType;
    }

    public File setMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public File setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public File setSize(Long size) {
        this.size = size;
        return this;
    }

    public String getFileHash() {
        return fileHash;
    }

    public File setFileHash(String fileHash) {
        this.fileHash = fileHash;
        return this;
    }

    public String getFileModifiedTime() {
        return fileModifiedTime;
    }

    public File setFileModifiedTime(String fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
        return this;
    }

    public String getFileCreateTime() {
        return fileCreateTime;
    }

    public File setFileCreateTime(String fileCreateTime) {
        this.fileCreateTime = fileCreateTime;
        return this;
    }

    public String getFileAccessTime() {
        return fileAccessTime;
    }

    public File setFileAccessTime(String fileAccessTime) {
        this.fileAccessTime = fileAccessTime;
        return this;
    }

    public String getProduceTime() {
        return produceTime;
    }

    public File setProduceTime(String produceTime) {
        this.produceTime = produceTime;
        return this;
    }

    public String getLatLong() {
        return latLong;
    }

    public File setLatLong(String latLong) {
        this.latLong = latLong;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public File setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public File setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public String getTravelClusterId() {
        return travelClusterId;
    }

    public File setTravelClusterId(String travelClusterId) {
        this.travelClusterId = travelClusterId;
        return this;
    }

    public Long getOrientation() {
        return orientation;
    }

    public File setOrientation(Long orientation) {
        this.orientation = orientation;
        return this;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public File setFigures(List<Figure> figures) {
        this.figures = figures;
        return this;
    }

    public Long getFigureCount() {
        return figureCount;
    }

    public File setFigureCount(Long figureCount) {
        this.figureCount = figureCount;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public File setLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public File setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getImageWidth() {
        return imageWidth;
    }

    public File setImageWidth(Long imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public Long getImageHeight() {
        return imageHeight;
    }

    public File setImageHeight(Long imageHeight) {
        this.imageHeight = imageHeight;
        return this;
    }

    public String getExif() {
        return exif;
    }

    public File setExif(String exif) {
        this.exif = exif;
        return this;
    }

    public ImageScore getImageScore() {
        return imageScore;
    }

    public File setImageScore(ImageScore imageScore) {
        this.imageScore = imageScore;
        return this;
    }

    public List<CroppingSuggestion> getCroppingSuggestions() {
        return croppingSuggestions;
    }

    public File setCroppingSuggestions(List<CroppingSuggestion> croppingSuggestions) {
        this.croppingSuggestions = croppingSuggestions;
        return this;
    }

    public List<OCRContents> getOcrContents() {
        return ocrContents;
    }

    public File setOcrContents(List<OCRContents> ocrContents) {
        this.ocrContents = ocrContents;
        return this;
    }

    public Long getVideoWidth() {
        return videoWidth;
    }

    public File setVideoWidth(Long videoWidth) {
        this.videoWidth = videoWidth;
        return this;
    }

    public Long getVideoHeight() {
        return videoHeight;
    }

    public File setVideoHeight(Long videoHeight) {
        this.videoHeight = videoHeight;
        return this;
    }

    public List<VideoStream> getVideoStreams() {
        return videoStreams;
    }

    public File setVideoStreams(List<VideoStream> videoStreams) {
        this.videoStreams = videoStreams;
        return this;
    }

    public List<SubtitleStream> getSubtitles() {
        return subtitles;
    }

    public File setSubtitles(List<SubtitleStream> subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public List<AudioStream> getAudioStreams() {
        return audioStreams;
    }

    public File setAudioStreams(List<AudioStream> audioStreams) {
        this.audioStreams = audioStreams;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public File setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public File setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
        return this;
    }

    public List<Image> getAudioCovers() {
        return audioCovers;
    }

    public File setAudioCovers(List<Image> audioCovers) {
        this.audioCovers = audioCovers;
        return this;
    }

    public String getComposer() {
        return composer;
    }

    public File setComposer(String composer) {
        this.composer = composer;
        return this;
    }

    public String getPerformer() {
        return performer;
    }

    public File setPerformer(String performer) {
        this.performer = performer;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public File setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getAlbum() {
        return album;
    }

    public File setAlbum(String album) {
        this.album = album;
        return this;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public File setPageCount(Long pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public String getETag() {
        return eTag;
    }

    public File setETag(String eTag) {
        this.eTag = eTag;
        return this;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public File setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
        return this;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public File setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
        return this;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public File setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
        return this;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public File setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
        return this;
    }

    public String getAccessControlAllowOrigin() {
        return accessControlAllowOrigin;
    }

    public File setAccessControlAllowOrigin(String accessControlAllowOrigin) {
        this.accessControlAllowOrigin = accessControlAllowOrigin;
        return this;
    }

    public String getAccessControlRequestMethod() {
        return accessControlRequestMethod;
    }

    public File setAccessControlRequestMethod(String accessControlRequestMethod) {
        this.accessControlRequestMethod = accessControlRequestMethod;
        return this;
    }

    public String getServerSideEncryptionCustomerAlgorithm() {
        return serverSideEncryptionCustomerAlgorithm;
    }

    public File setServerSideEncryptionCustomerAlgorithm(String serverSideEncryptionCustomerAlgorithm) {
        this.serverSideEncryptionCustomerAlgorithm = serverSideEncryptionCustomerAlgorithm;
        return this;
    }

    public String getServerSideEncryption() {
        return serverSideEncryption;
    }

    public File setServerSideEncryption(String serverSideEncryption) {
        this.serverSideEncryption = serverSideEncryption;
        return this;
    }

    public String getServerSideDataEncryption() {
        return serverSideDataEncryption;
    }

    public File setServerSideDataEncryption(String serverSideDataEncryption) {
        this.serverSideDataEncryption = serverSideDataEncryption;
        return this;
    }

    public String getServerSideEncryptionKeyId() {
        return serverSideEncryptionKeyId;
    }

    public File setServerSideEncryptionKeyId(String serverSideEncryptionKeyId) {
        this.serverSideEncryptionKeyId = serverSideEncryptionKeyId;
        return this;
    }

    public String getOssStorageClass() {
        return ossStorageClass;
    }

    public File setOssStorageClass(String ossStorageClass) {
        this.ossStorageClass = ossStorageClass;
        return this;
    }

    public String getOssCrc64() {
        return ossCrc64;
    }

    public File setOssCrc64(String ossCrc64) {
        this.ossCrc64 = ossCrc64;
        return this;
    }

    public String getObjectAcl() {
        return objectAcl;
    }

    public File setObjectAcl(String objectAcl) {
        this.objectAcl = objectAcl;
        return this;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public File setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
        return this;
    }

    public Map<String, Object> getOssUserMeta() {
        return ossUserMeta;
    }

    public File setOssUserMeta(Map<String, Object> ossUserMeta) {
        this.ossUserMeta = ossUserMeta;
        return this;
    }

    public Long getOssTaggingCount() {
        return ossTaggingCount;
    }

    public File setOssTaggingCount(Long ossTaggingCount) {
        this.ossTaggingCount = ossTaggingCount;
        return this;
    }

    public Map<String, Object> getOssTagging() {
        return ossTagging;
    }

    public File setOssTagging(Map<String, Object> ossTagging) {
        this.ossTagging = ossTagging;
        return this;
    }

    public String getOssExpiration() {
        return ossExpiration;
    }

    public File setOssExpiration(String ossExpiration) {
        this.ossExpiration = ossExpiration;
        return this;
    }

    public String getOssVersionId() {
        return ossVersionId;
    }

    public File setOssVersionId(String ossVersionId) {
        this.ossVersionId = ossVersionId;
        return this;
    }

    public String getOssDeleteMarker() {
        return ossDeleteMarker;
    }

    public File setOssDeleteMarker(String ossDeleteMarker) {
        this.ossDeleteMarker = ossDeleteMarker;
        return this;
    }

    public String getOssObjectType() {
        return ossObjectType;
    }

    public File setOssObjectType(String ossObjectType) {
        this.ossObjectType = ossObjectType;
        return this;
    }

    public String getCustomId() {
        return customId;
    }

    public File setCustomId(String customId) {
        this.customId = customId;
        return this;
    }

    public Map<String, Object> getCustomLabels() {
        return customLabels;
    }

    public File setCustomLabels(Map<String, Object> customLabels) {
        this.customLabels = customLabels;
        return this;
    }

    public Long getStreamCount() {
        return streamCount;
    }

    public File setStreamCount(Long streamCount) {
        this.streamCount = streamCount;
        return this;
    }

    public Long getProgramCount() {
        return programCount;
    }

    public File setProgramCount(Long programCount) {
        this.programCount = programCount;
        return this;
    }

    public String getFormatName() {
        return formatName;
    }

    public File setFormatName(String formatName) {
        this.formatName = formatName;
        return this;
    }

    public String getFormatLongName() {
        return formatLongName;
    }

    public File setFormatLongName(String formatLongName) {
        this.formatLongName = formatLongName;
        return this;
    }

    public Double getStartTime() {
        return startTime;
    }

    public File setStartTime(Double startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public File setBitrate(Long bitrate) {
        this.bitrate = bitrate;
        return this;
    }

    public Double getDuration() {
        return duration;
    }

    public File setDuration(Double duration) {
        this.duration = duration;
        return this;
    }

    public List<String> getSemanticTypes() {
        return semanticTypes;
    }

    public File setSemanticTypes(List<String> semanticTypes) {
        this.semanticTypes = semanticTypes;
        return this;
    }

    public List<Element> getElements() {
        return elements;
    }

    public File setElements(List<Element> elements) {
        this.elements = elements;
        return this;
    }

    public List<SceneElement> getSceneElements() {
        return sceneElements;
    }

    public File setSceneElements(List<SceneElement> sceneElements) {
        this.sceneElements = sceneElements;
        return this;
    }

    public String getOcrTexts() {
        return ocrTexts;
    }

    public File setOcrTexts(String ocrTexts) {
        this.ocrTexts = ocrTexts;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public File setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getObjectStatus() {
        return objectStatus;
    }

    public File setObjectStatus(String objectStatus) {
        this.objectStatus = objectStatus;
        return this;
    }

    public Insights getInsights() {
        return insights;
    }

    public File setInsights(Insights insights) {
        this.insights = insights;
        return this;
    }
}
