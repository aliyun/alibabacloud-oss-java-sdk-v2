package com.aliyun.sdk.service.oss2.dataprocess.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Map;

@JacksonXmlRootElement(localName = "File")
public final class File {

    @JacksonXmlProperty(localName = "OwnerId")
    private String ownerId;

    @JacksonXmlProperty(localName = "DatasetName")
    private String datasetName;

    @JacksonXmlProperty(localName = "ObjectType")
    private String objectType;

    @JacksonXmlProperty(localName = "ObjectId")
    private String objectId;

    @JacksonXmlProperty(localName = "UpdateTime")
    private String updateTime;

    @JacksonXmlProperty(localName = "CreateTime")
    private String createTime;

    @JacksonXmlProperty(localName = "URI")
    private String uri;

    @JacksonXmlProperty(localName = "OSSURI")
    private String ossUri;

    @JacksonXmlProperty(localName = "Filename")
    private String filename;

    @JacksonXmlProperty(localName = "MediaType")
    private String mediaType;

    @JacksonXmlProperty(localName = "ContentType")
    private String contentType;

    @JacksonXmlProperty(localName = "Size")
    private Long size;

    @JacksonXmlProperty(localName = "FileHash")
    private String fileHash;

    @JacksonXmlProperty(localName = "FileModifiedTime")
    private String fileModifiedTime;

    @JacksonXmlProperty(localName = "FileCreateTime")
    private String fileCreateTime;

    @JacksonXmlProperty(localName = "FileAccessTime")
    private String fileAccessTime;

    @JacksonXmlProperty(localName = "ProduceTime")
    private String produceTime;

    @JacksonXmlProperty(localName = "LatLong")
    private String latLong;

    @JacksonXmlProperty(localName = "Timezone")
    private String timezone;

    @JacksonXmlElementWrapper(localName = "Addresses")
    @JacksonXmlProperty(localName = "Address")
    private List<Address> addresses;

    @JacksonXmlProperty(localName = "TravelClusterId")
    private String travelClusterId;

    @JacksonXmlProperty(localName = "Orientation")
    private Long orientation;

    @JacksonXmlElementWrapper(localName = "Figures")
    @JacksonXmlProperty(localName = "Figure")
    private List<Figure> figures;

    @JacksonXmlProperty(localName = "FigureCount")
    private Long figureCount;

    @JacksonXmlElementWrapper(localName = "Labels")
    @JacksonXmlProperty(localName = "Label")
    private List<Label> labels;

    @JacksonXmlProperty(localName = "Title")
    private String title;

    @JacksonXmlProperty(localName = "ImageWidth")
    private Long imageWidth;

    @JacksonXmlProperty(localName = "ImageHeight")
    private Long imageHeight;

    @JacksonXmlProperty(localName = "EXIF")
    private String exif;

    @JacksonXmlProperty(localName = "ImageScore")
    private ImageScore imageScore;

    @JacksonXmlElementWrapper(localName = "CroppingSuggestions")
    @JacksonXmlProperty(localName = "CroppingSuggestion")
    private List<CroppingSuggestion> croppingSuggestions;

    @JacksonXmlElementWrapper(localName = "OCRContents")
    @JacksonXmlProperty(localName = "OCRContents")
    private List<OCRContents> ocrContents;

    @JacksonXmlProperty(localName = "VideoWidth")
    private Long videoWidth;

    @JacksonXmlProperty(localName = "VideoHeight")
    private Long videoHeight;

    @JacksonXmlElementWrapper(localName = "VideoStreams")
    @JacksonXmlProperty(localName = "VideoStream")
    private List<VideoStream> videoStreams;

    @JacksonXmlElementWrapper(localName = "Subtitles")
    @JacksonXmlProperty(localName = "Subtitle")
    private List<SubtitleStream> subtitles;

    @JacksonXmlElementWrapper(localName = "AudioStreams")
    @JacksonXmlProperty(localName = "AudioStream")
    private List<AudioStream> audioStreams;

    @JacksonXmlProperty(localName = "Artist")
    private String artist;

    @JacksonXmlProperty(localName = "AlbumArtist")
    private String albumArtist;

    @JacksonXmlElementWrapper(localName = "AudioCovers")
    @JacksonXmlProperty(localName = "AudioCover")
    private List<Image> audioCovers;

    @JacksonXmlProperty(localName = "Composer")
    private String composer;

    @JacksonXmlProperty(localName = "Performer")
    private String performer;

    @JacksonXmlProperty(localName = "Language")
    private String language;

    @JacksonXmlProperty(localName = "Album")
    private String album;

    @JacksonXmlProperty(localName = "PageCount")
    private Long pageCount;

    @JacksonXmlProperty(localName = "ETag")
    private String eTag;

    @JacksonXmlProperty(localName = "CacheControl")
    private String cacheControl;

    @JacksonXmlProperty(localName = "ContentDisposition")
    private String contentDisposition;

    @JacksonXmlProperty(localName = "ContentEncoding")
    private String contentEncoding;

    @JacksonXmlProperty(localName = "ContentLanguage")
    private String contentLanguage;

    @JacksonXmlProperty(localName = "AccessControlAllowOrigin")
    private String accessControlAllowOrigin;

    @JacksonXmlProperty(localName = "AccessControlRequestMethod")
    private String accessControlRequestMethod;

    @JacksonXmlProperty(localName = "ServerSideEncryptionCustomerAlgorithm")
    private String serverSideEncryptionCustomerAlgorithm;

    @JacksonXmlProperty(localName = "ServerSideEncryption")
    private String serverSideEncryption;

    @JacksonXmlProperty(localName = "ServerSideDataEncryption")
    private String serverSideDataEncryption;

    @JacksonXmlProperty(localName = "ServerSideEncryptionKeyId")
    private String serverSideEncryptionKeyId;

    @JacksonXmlProperty(localName = "OSSStorageClass")
    private String ossStorageClass;

    @JacksonXmlProperty(localName = "OSSCRC64")
    private String ossCrc64;

    @JacksonXmlProperty(localName = "ObjectACL")
    private String objectAcl;

    @JacksonXmlProperty(localName = "ContentMd5")
    private String contentMd5;

    @JacksonXmlProperty(localName = "OSSUserMeta")
    private Map<String, Object> ossUserMeta;

    @JacksonXmlProperty(localName = "OSSTaggingCount")
    private Long ossTaggingCount;

    @JacksonXmlProperty(localName = "OSSTagging")
    private Map<String, Object> ossTagging;

    @JacksonXmlProperty(localName = "OSSExpiration")
    private String ossExpiration;

    @JacksonXmlProperty(localName = "OSSVersionId")
    private String ossVersionId;

    @JacksonXmlProperty(localName = "OSSDeleteMarker")
    private String ossDeleteMarker;

    @JacksonXmlProperty(localName = "OSSObjectType")
    private String ossObjectType;

    @JacksonXmlProperty(localName = "CustomId")
    private String customId;

    @JacksonXmlProperty(localName = "CustomLabels")
    private Map<String, Object> customLabels;

    @JacksonXmlProperty(localName = "StreamCount")
    private Long streamCount;

    @JacksonXmlProperty(localName = "ProgramCount")
    private Long programCount;

    @JacksonXmlProperty(localName = "FormatName")
    private String formatName;

    @JacksonXmlProperty(localName = "FormatLongName")
    private String formatLongName;

    @JacksonXmlProperty(localName = "StartTime")
    private Double startTime;

    @JacksonXmlProperty(localName = "Bitrate")
    private Long bitrate;

    @JacksonXmlProperty(localName = "Duration")
    private Double duration;

    @JacksonXmlElementWrapper(localName = "SemanticTypes")
    @JacksonXmlProperty(localName = "SemanticType")
    private List<String> semanticTypes;

    @JacksonXmlElementWrapper(localName = "Elements")
    @JacksonXmlProperty(localName = "Element")
    private List<Element> elements;

    @JacksonXmlElementWrapper(localName = "SceneElements")
    @JacksonXmlProperty(localName = "SceneElement")
    private List<SceneElement> sceneElements;

    @JacksonXmlProperty(localName = "OCRTexts")
    private String ocrTexts;

    @JacksonXmlProperty(localName = "Reason")
    private String reason;

    @JacksonXmlProperty(localName = "ObjectStatus")
    private String objectStatus;

    @JacksonXmlProperty(localName = "Insights")
    private Insights insights;

    public File() {
    }

    private File(Builder builder) {
        this.ownerId = builder.ownerId;
        this.datasetName = builder.datasetName;
        this.objectType = builder.objectType;
        this.objectId = builder.objectId;
        this.updateTime = builder.updateTime;
        this.createTime = builder.createTime;
        this.uri = builder.uri;
        this.ossUri = builder.ossUri;
        this.filename = builder.filename;
        this.mediaType = builder.mediaType;
        this.contentType = builder.contentType;
        this.size = builder.size;
        this.fileHash = builder.fileHash;
        this.fileModifiedTime = builder.fileModifiedTime;
        this.fileCreateTime = builder.fileCreateTime;
        this.fileAccessTime = builder.fileAccessTime;
        this.produceTime = builder.produceTime;
        this.latLong = builder.latLong;
        this.timezone = builder.timezone;
        this.addresses = builder.addresses;
        this.travelClusterId = builder.travelClusterId;
        this.orientation = builder.orientation;
        this.figures = builder.figures;
        this.figureCount = builder.figureCount;
        this.labels = builder.labels;
        this.title = builder.title;
        this.imageWidth = builder.imageWidth;
        this.imageHeight = builder.imageHeight;
        this.exif = builder.exif;
        this.imageScore = builder.imageScore;
        this.croppingSuggestions = builder.croppingSuggestions;
        this.ocrContents = builder.ocrContents;
        this.videoWidth = builder.videoWidth;
        this.videoHeight = builder.videoHeight;
        this.videoStreams = builder.videoStreams;
        this.subtitles = builder.subtitles;
        this.audioStreams = builder.audioStreams;
        this.artist = builder.artist;
        this.albumArtist = builder.albumArtist;
        this.audioCovers = builder.audioCovers;
        this.composer = builder.composer;
        this.performer = builder.performer;
        this.language = builder.language;
        this.album = builder.album;
        this.pageCount = builder.pageCount;
        this.eTag = builder.eTag;
        this.cacheControl = builder.cacheControl;
        this.contentDisposition = builder.contentDisposition;
        this.contentEncoding = builder.contentEncoding;
        this.contentLanguage = builder.contentLanguage;
        this.accessControlAllowOrigin = builder.accessControlAllowOrigin;
        this.accessControlRequestMethod = builder.accessControlRequestMethod;
        this.serverSideEncryptionCustomerAlgorithm = builder.serverSideEncryptionCustomerAlgorithm;
        this.serverSideEncryption = builder.serverSideEncryption;
        this.serverSideDataEncryption = builder.serverSideDataEncryption;
        this.serverSideEncryptionKeyId = builder.serverSideEncryptionKeyId;
        this.ossStorageClass = builder.ossStorageClass;
        this.ossCrc64 = builder.ossCrc64;
        this.objectAcl = builder.objectAcl;
        this.contentMd5 = builder.contentMd5;
        this.ossUserMeta = builder.ossUserMeta;
        this.ossTaggingCount = builder.ossTaggingCount;
        this.ossTagging = builder.ossTagging;
        this.ossExpiration = builder.ossExpiration;
        this.ossVersionId = builder.ossVersionId;
        this.ossDeleteMarker = builder.ossDeleteMarker;
        this.ossObjectType = builder.ossObjectType;
        this.customId = builder.customId;
        this.customLabels = builder.customLabels;
        this.streamCount = builder.streamCount;
        this.programCount = builder.programCount;
        this.formatName = builder.formatName;
        this.formatLongName = builder.formatLongName;
        this.startTime = builder.startTime;
        this.bitrate = builder.bitrate;
        this.duration = builder.duration;
        this.semanticTypes = builder.semanticTypes;
        this.elements = builder.elements;
        this.sceneElements = builder.sceneElements;
        this.ocrTexts = builder.ocrTexts;
        this.reason = builder.reason;
        this.objectStatus = builder.objectStatus;
        this.insights = builder.insights;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public String ownerId() {
        return this.ownerId;
    }

    public String datasetName() {
        return this.datasetName;
    }

    public String objectType() {
        return this.objectType;
    }

    public String objectId() {
        return this.objectId;
    }

    public String updateTime() {
        return this.updateTime;
    }

    public String createTime() {
        return this.createTime;
    }

    public String uri() {
        return this.uri;
    }

    public String ossUri() {
        return this.ossUri;
    }

    public String filename() {
        return this.filename;
    }

    public String mediaType() {
        return this.mediaType;
    }

    public String contentType() {
        return this.contentType;
    }

    public Long size() {
        return this.size;
    }

    public String fileHash() {
        return this.fileHash;
    }

    public String fileModifiedTime() {
        return this.fileModifiedTime;
    }

    public String fileCreateTime() {
        return this.fileCreateTime;
    }

    public String fileAccessTime() {
        return this.fileAccessTime;
    }

    public String produceTime() {
        return this.produceTime;
    }

    public String latLong() {
        return this.latLong;
    }

    public String timezone() {
        return this.timezone;
    }

    public List<Address> addresses() {
        return this.addresses;
    }

    public String travelClusterId() {
        return this.travelClusterId;
    }

    public Long orientation() {
        return this.orientation;
    }

    public List<Figure> figures() {
        return this.figures;
    }

    public Long figureCount() {
        return this.figureCount;
    }

    public List<Label> labels() {
        return this.labels;
    }

    public String title() {
        return this.title;
    }

    public Long imageWidth() {
        return this.imageWidth;
    }

    public Long imageHeight() {
        return this.imageHeight;
    }

    public String exif() {
        return this.exif;
    }

    public ImageScore imageScore() {
        return this.imageScore;
    }

    public List<CroppingSuggestion> croppingSuggestions() {
        return this.croppingSuggestions;
    }

    public List<OCRContents> ocrContents() {
        return this.ocrContents;
    }

    public Long videoWidth() {
        return this.videoWidth;
    }

    public Long videoHeight() {
        return this.videoHeight;
    }

    public List<VideoStream> videoStreams() {
        return this.videoStreams;
    }

    public List<SubtitleStream> subtitles() {
        return this.subtitles;
    }

    public List<AudioStream> audioStreams() {
        return this.audioStreams;
    }

    public String artist() {
        return this.artist;
    }

    public String albumArtist() {
        return this.albumArtist;
    }

    public List<Image> audioCovers() {
        return this.audioCovers;
    }

    public String composer() {
        return this.composer;
    }

    public String performer() {
        return this.performer;
    }

    public String language() {
        return this.language;
    }

    public String album() {
        return this.album;
    }

    public Long pageCount() {
        return this.pageCount;
    }

    public String eTag() {
        return this.eTag;
    }

    public String cacheControl() {
        return this.cacheControl;
    }

    public String contentDisposition() {
        return this.contentDisposition;
    }

    public String contentEncoding() {
        return this.contentEncoding;
    }

    public String contentLanguage() {
        return this.contentLanguage;
    }

    public String accessControlAllowOrigin() {
        return this.accessControlAllowOrigin;
    }

    public String accessControlRequestMethod() {
        return this.accessControlRequestMethod;
    }

    public String serverSideEncryptionCustomerAlgorithm() {
        return this.serverSideEncryptionCustomerAlgorithm;
    }

    public String serverSideEncryption() {
        return this.serverSideEncryption;
    }

    public String serverSideDataEncryption() {
        return this.serverSideDataEncryption;
    }

    public String serverSideEncryptionKeyId() {
        return this.serverSideEncryptionKeyId;
    }

    public String ossStorageClass() {
        return this.ossStorageClass;
    }

    public String ossCrc64() {
        return this.ossCrc64;
    }

    public String objectAcl() {
        return this.objectAcl;
    }

    public String contentMd5() {
        return this.contentMd5;
    }

    public Map<String, Object> ossUserMeta() {
        return this.ossUserMeta;
    }

    public Long ossTaggingCount() {
        return this.ossTaggingCount;
    }

    public Map<String, Object> ossTagging() {
        return this.ossTagging;
    }

    public String ossExpiration() {
        return this.ossExpiration;
    }

    public String ossVersionId() {
        return this.ossVersionId;
    }

    public String ossDeleteMarker() {
        return this.ossDeleteMarker;
    }

    public String ossObjectType() {
        return this.ossObjectType;
    }

    public String customId() {
        return this.customId;
    }

    public Map<String, Object> customLabels() {
        return this.customLabels;
    }

    public Long streamCount() {
        return this.streamCount;
    }

    public Long programCount() {
        return this.programCount;
    }

    public String formatName() {
        return this.formatName;
    }

    public String formatLongName() {
        return this.formatLongName;
    }

    public Double startTime() {
        return this.startTime;
    }

    public Long bitrate() {
        return this.bitrate;
    }

    public Double duration() {
        return this.duration;
    }

    public List<String> semanticTypes() {
        return this.semanticTypes;
    }

    public List<Element> elements() {
        return this.elements;
    }

    public List<SceneElement> sceneElements() {
        return this.sceneElements;
    }

    public String ocrTexts() {
        return this.ocrTexts;
    }

    public String reason() {
        return this.reason;
    }

    public String objectStatus() {
        return this.objectStatus;
    }

    public Insights insights() {
        return this.insights;
    }

    public static class Builder {
        private String ownerId;
        private String datasetName;
        private String objectType;
        private String objectId;
        private String updateTime;
        private String createTime;
        private String uri;
        private String ossUri;
        private String filename;
        private String mediaType;
        private String contentType;
        private Long size;
        private String fileHash;
        private String fileModifiedTime;
        private String fileCreateTime;
        private String fileAccessTime;
        private String produceTime;
        private String latLong;
        private String timezone;
        private List<Address> addresses;
        private String travelClusterId;
        private Long orientation;
        private List<Figure> figures;
        private Long figureCount;
        private List<Label> labels;
        private String title;
        private Long imageWidth;
        private Long imageHeight;
        private String exif;
        private ImageScore imageScore;
        private List<CroppingSuggestion> croppingSuggestions;
        private List<OCRContents> ocrContents;
        private Long videoWidth;
        private Long videoHeight;
        private List<VideoStream> videoStreams;
        private List<SubtitleStream> subtitles;
        private List<AudioStream> audioStreams;
        private String artist;
        private String albumArtist;
        private List<Image> audioCovers;
        private String composer;
        private String performer;
        private String language;
        private String album;
        private Long pageCount;
        private String eTag;
        private String cacheControl;
        private String contentDisposition;
        private String contentEncoding;
        private String contentLanguage;
        private String accessControlAllowOrigin;
        private String accessControlRequestMethod;
        private String serverSideEncryptionCustomerAlgorithm;
        private String serverSideEncryption;
        private String serverSideDataEncryption;
        private String serverSideEncryptionKeyId;
        private String ossStorageClass;
        private String ossCrc64;
        private String objectAcl;
        private String contentMd5;
        private Map<String, Object> ossUserMeta;
        private Long ossTaggingCount;
        private Map<String, Object> ossTagging;
        private String ossExpiration;
        private String ossVersionId;
        private String ossDeleteMarker;
        private String ossObjectType;
        private String customId;
        private Map<String, Object> customLabels;
        private Long streamCount;
        private Long programCount;
        private String formatName;
        private String formatLongName;
        private Double startTime;
        private Long bitrate;
        private Double duration;
        private List<String> semanticTypes;
        private List<Element> elements;
        private List<SceneElement> sceneElements;
        private String ocrTexts;
        private String reason;
        private String objectStatus;
        private Insights insights;

        private Builder() {
        }

        private Builder(File file) {
            this.ownerId = file.ownerId;
            this.datasetName = file.datasetName;
            this.objectType = file.objectType;
            this.objectId = file.objectId;
            this.updateTime = file.updateTime;
            this.createTime = file.createTime;
            this.uri = file.uri;
            this.ossUri = file.ossUri;
            this.filename = file.filename;
            this.mediaType = file.mediaType;
            this.contentType = file.contentType;
            this.size = file.size;
            this.fileHash = file.fileHash;
            this.fileModifiedTime = file.fileModifiedTime;
            this.fileCreateTime = file.fileCreateTime;
            this.fileAccessTime = file.fileAccessTime;
            this.produceTime = file.produceTime;
            this.latLong = file.latLong;
            this.timezone = file.timezone;
            this.addresses = file.addresses;
            this.travelClusterId = file.travelClusterId;
            this.orientation = file.orientation;
            this.figures = file.figures;
            this.figureCount = file.figureCount;
            this.labels = file.labels;
            this.title = file.title;
            this.imageWidth = file.imageWidth;
            this.imageHeight = file.imageHeight;
            this.exif = file.exif;
            this.imageScore = file.imageScore;
            this.croppingSuggestions = file.croppingSuggestions;
            this.ocrContents = file.ocrContents;
            this.videoWidth = file.videoWidth;
            this.videoHeight = file.videoHeight;
            this.videoStreams = file.videoStreams;
            this.subtitles = file.subtitles;
            this.audioStreams = file.audioStreams;
            this.artist = file.artist;
            this.albumArtist = file.albumArtist;
            this.audioCovers = file.audioCovers;
            this.composer = file.composer;
            this.performer = file.performer;
            this.language = file.language;
            this.album = file.album;
            this.pageCount = file.pageCount;
            this.eTag = file.eTag;
            this.cacheControl = file.cacheControl;
            this.contentDisposition = file.contentDisposition;
            this.contentEncoding = file.contentEncoding;
            this.contentLanguage = file.contentLanguage;
            this.accessControlAllowOrigin = file.accessControlAllowOrigin;
            this.accessControlRequestMethod = file.accessControlRequestMethod;
            this.serverSideEncryptionCustomerAlgorithm = file.serverSideEncryptionCustomerAlgorithm;
            this.serverSideEncryption = file.serverSideEncryption;
            this.serverSideDataEncryption = file.serverSideDataEncryption;
            this.serverSideEncryptionKeyId = file.serverSideEncryptionKeyId;
            this.ossStorageClass = file.ossStorageClass;
            this.ossCrc64 = file.ossCrc64;
            this.objectAcl = file.objectAcl;
            this.contentMd5 = file.contentMd5;
            this.ossUserMeta = file.ossUserMeta;
            this.ossTaggingCount = file.ossTaggingCount;
            this.ossTagging = file.ossTagging;
            this.ossExpiration = file.ossExpiration;
            this.ossVersionId = file.ossVersionId;
            this.ossDeleteMarker = file.ossDeleteMarker;
            this.ossObjectType = file.ossObjectType;
            this.customId = file.customId;
            this.customLabels = file.customLabels;
            this.streamCount = file.streamCount;
            this.programCount = file.programCount;
            this.formatName = file.formatName;
            this.formatLongName = file.formatLongName;
            this.startTime = file.startTime;
            this.bitrate = file.bitrate;
            this.duration = file.duration;
            this.semanticTypes = file.semanticTypes;
            this.elements = file.elements;
            this.sceneElements = file.sceneElements;
            this.ocrTexts = file.ocrTexts;
            this.reason = file.reason;
            this.objectStatus = file.objectStatus;
            this.insights = file.insights;
        }

        public Builder ownerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder datasetName(String datasetName) {
            this.datasetName = datasetName;
            return this;
        }

        public Builder objectType(String objectType) {
            this.objectType = objectType;
            return this;
        }

        public Builder objectId(String objectId) {
            this.objectId = objectId;
            return this;
        }

        public Builder updateTime(String updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder createTime(String createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public Builder ossUri(String ossUri) {
            this.ossUri = ossUri;
            return this;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder mediaType(String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder size(Long size) {
            this.size = size;
            return this;
        }

        public Builder fileHash(String fileHash) {
            this.fileHash = fileHash;
            return this;
        }

        public Builder fileModifiedTime(String fileModifiedTime) {
            this.fileModifiedTime = fileModifiedTime;
            return this;
        }

        public Builder fileCreateTime(String fileCreateTime) {
            this.fileCreateTime = fileCreateTime;
            return this;
        }

        public Builder fileAccessTime(String fileAccessTime) {
            this.fileAccessTime = fileAccessTime;
            return this;
        }

        public Builder produceTime(String produceTime) {
            this.produceTime = produceTime;
            return this;
        }

        public Builder latLong(String latLong) {
            this.latLong = latLong;
            return this;
        }

        public Builder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder addresses(List<Address> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder travelClusterId(String travelClusterId) {
            this.travelClusterId = travelClusterId;
            return this;
        }

        public Builder orientation(Long orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder figures(List<Figure> figures) {
            this.figures = figures;
            return this;
        }

        public Builder figureCount(Long figureCount) {
            this.figureCount = figureCount;
            return this;
        }

        public Builder labels(List<Label> labels) {
            this.labels = labels;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder imageWidth(Long imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder imageHeight(Long imageHeight) {
            this.imageHeight = imageHeight;
            return this;
        }

        public Builder exif(String exif) {
            this.exif = exif;
            return this;
        }

        public Builder imageScore(ImageScore imageScore) {
            this.imageScore = imageScore;
            return this;
        }

        public Builder croppingSuggestions(List<CroppingSuggestion> croppingSuggestions) {
            this.croppingSuggestions = croppingSuggestions;
            return this;
        }

        public Builder ocrContents(List<OCRContents> ocrContents) {
            this.ocrContents = ocrContents;
            return this;
        }

        public Builder videoWidth(Long videoWidth) {
            this.videoWidth = videoWidth;
            return this;
        }

        public Builder videoHeight(Long videoHeight) {
            this.videoHeight = videoHeight;
            return this;
        }

        public Builder videoStreams(List<VideoStream> videoStreams) {
            this.videoStreams = videoStreams;
            return this;
        }

        public Builder subtitles(List<SubtitleStream> subtitles) {
            this.subtitles = subtitles;
            return this;
        }

        public Builder audioStreams(List<AudioStream> audioStreams) {
            this.audioStreams = audioStreams;
            return this;
        }

        public Builder artist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder albumArtist(String albumArtist) {
            this.albumArtist = albumArtist;
            return this;
        }

        public Builder audioCovers(List<Image> audioCovers) {
            this.audioCovers = audioCovers;
            return this;
        }

        public Builder composer(String composer) {
            this.composer = composer;
            return this;
        }

        public Builder performer(String performer) {
            this.performer = performer;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder album(String album) {
            this.album = album;
            return this;
        }

        public Builder pageCount(Long pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder eTag(String eTag) {
            this.eTag = eTag;
            return this;
        }

        public Builder cacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
            return this;
        }

        public Builder contentDisposition(String contentDisposition) {
            this.contentDisposition = contentDisposition;
            return this;
        }

        public Builder contentEncoding(String contentEncoding) {
            this.contentEncoding = contentEncoding;
            return this;
        }

        public Builder contentLanguage(String contentLanguage) {
            this.contentLanguage = contentLanguage;
            return this;
        }

        public Builder accessControlAllowOrigin(String accessControlAllowOrigin) {
            this.accessControlAllowOrigin = accessControlAllowOrigin;
            return this;
        }

        public Builder accessControlRequestMethod(String accessControlRequestMethod) {
            this.accessControlRequestMethod = accessControlRequestMethod;
            return this;
        }

        public Builder serverSideEncryptionCustomerAlgorithm(String serverSideEncryptionCustomerAlgorithm) {
            this.serverSideEncryptionCustomerAlgorithm = serverSideEncryptionCustomerAlgorithm;
            return this;
        }

        public Builder serverSideEncryption(String serverSideEncryption) {
            this.serverSideEncryption = serverSideEncryption;
            return this;
        }

        public Builder serverSideDataEncryption(String serverSideDataEncryption) {
            this.serverSideDataEncryption = serverSideDataEncryption;
            return this;
        }

        public Builder serverSideEncryptionKeyId(String serverSideEncryptionKeyId) {
            this.serverSideEncryptionKeyId = serverSideEncryptionKeyId;
            return this;
        }

        public Builder ossStorageClass(String ossStorageClass) {
            this.ossStorageClass = ossStorageClass;
            return this;
        }

        public Builder ossCrc64(String ossCrc64) {
            this.ossCrc64 = ossCrc64;
            return this;
        }

        public Builder objectAcl(String objectAcl) {
            this.objectAcl = objectAcl;
            return this;
        }

        public Builder contentMd5(String contentMd5) {
            this.contentMd5 = contentMd5;
            return this;
        }

        public Builder ossUserMeta(Map<String, Object> ossUserMeta) {
            this.ossUserMeta = ossUserMeta;
            return this;
        }

        public Builder ossTaggingCount(Long ossTaggingCount) {
            this.ossTaggingCount = ossTaggingCount;
            return this;
        }

        public Builder ossTagging(Map<String, Object> ossTagging) {
            this.ossTagging = ossTagging;
            return this;
        }

        public Builder ossExpiration(String ossExpiration) {
            this.ossExpiration = ossExpiration;
            return this;
        }

        public Builder ossVersionId(String ossVersionId) {
            this.ossVersionId = ossVersionId;
            return this;
        }

        public Builder ossDeleteMarker(String ossDeleteMarker) {
            this.ossDeleteMarker = ossDeleteMarker;
            return this;
        }

        public Builder ossObjectType(String ossObjectType) {
            this.ossObjectType = ossObjectType;
            return this;
        }

        public Builder customId(String customId) {
            this.customId = customId;
            return this;
        }

        public Builder customLabels(Map<String, Object> customLabels) {
            this.customLabels = customLabels;
            return this;
        }

        public Builder streamCount(Long streamCount) {
            this.streamCount = streamCount;
            return this;
        }

        public Builder programCount(Long programCount) {
            this.programCount = programCount;
            return this;
        }

        public Builder formatName(String formatName) {
            this.formatName = formatName;
            return this;
        }

        public Builder formatLongName(String formatLongName) {
            this.formatLongName = formatLongName;
            return this;
        }

        public Builder startTime(Double startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder bitrate(Long bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        public Builder duration(Double duration) {
            this.duration = duration;
            return this;
        }

        public Builder semanticTypes(List<String> semanticTypes) {
            this.semanticTypes = semanticTypes;
            return this;
        }

        public Builder elements(List<Element> elements) {
            this.elements = elements;
            return this;
        }

        public Builder sceneElements(List<SceneElement> sceneElements) {
            this.sceneElements = sceneElements;
            return this;
        }

        public Builder ocrTexts(String ocrTexts) {
            this.ocrTexts = ocrTexts;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder objectStatus(String objectStatus) {
            this.objectStatus = objectStatus;
            return this;
        }

        public Builder insights(Insights insights) {
            this.insights = insights;
            return this;
        }

        public File build() {
            return new File(this);
        }
    }
}
