package com.aliyun.sdk.service.oss2.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import static java.util.Objects.requireNonNull;

/**
 * The operation to perform after the rule is matched.  This parameter must be specified if RoutingRule is specified.
 */
 @JacksonXmlRootElement(localName = "RoutingRuleRedirect")
public final class RoutingRuleRedirect {  
    @JacksonXmlProperty(localName = "MirrorAllowVideoSnapshot")
    private Boolean mirrorAllowVideoSnapshot;
 
    @JacksonXmlProperty(localName = "MirrorAsyncStatus")
    private Long mirrorAsyncStatus;
 
    @JacksonXmlProperty(localName = "MirrorTaggings")
    private MirrorTaggings mirrorTaggings;
 
    @JacksonXmlProperty(localName = "MirrorAuth")
    private MirrorAuth mirrorAuth;
 
    @JacksonXmlProperty(localName = "HostName")
    private String hostName;
 
    @JacksonXmlProperty(localName = "ReplaceKeyPrefixWith")
    private String replaceKeyPrefixWith;
 
    @JacksonXmlProperty(localName = "MirrorDstRegion")
    private String mirrorDstRegion;
 
    @JacksonXmlProperty(localName = "MirrorPassQueryString")
    private Boolean mirrorPassQueryString;
 
    @JacksonXmlProperty(localName = "HttpRedirectCode")
    private Long httpRedirectCode;
 
    @JacksonXmlProperty(localName = "MirrorDstVpcId")
    private String mirrorDstVpcId;
 
    @JacksonXmlProperty(localName = "MirrorHeaders")
    private MirrorHeaders mirrorHeaders;
 
    @JacksonXmlProperty(localName = "MirrorTunnelId")
    private String mirrorTunnelId;
 
    @JacksonXmlProperty(localName = "MirrorRole")
    private String mirrorRole;
 
    @JacksonXmlProperty(localName = "RedirectType")
    private String redirectType;
 
    @JacksonXmlProperty(localName = "PassQueryString")
    private Boolean passQueryString;
 
    @JacksonXmlProperty(localName = "MirrorUsingRole")
    private Boolean mirrorUsingRole;
 
    @JacksonXmlProperty(localName = "MirrorReturnHeaders")
    private MirrorReturnHeaders mirrorReturnHeaders;
 
    @JacksonXmlProperty(localName = "MirrorProxyPass")
    private Boolean mirrorProxyPass;
 
    @JacksonXmlProperty(localName = "MirrorIsExpressTunnel")
    private Boolean mirrorIsExpressTunnel;
 
    @JacksonXmlProperty(localName = "MirrorDstSlaveVpcId")
    private String mirrorDstSlaveVpcId;
 
    @JacksonXmlProperty(localName = "MirrorAllowHeadObject")
    private Boolean mirrorAllowHeadObject;
 
    @JacksonXmlProperty(localName = "TransparentMirrorResponseCodes")
    private String transparentMirrorResponseCodes;
 
    @JacksonXmlProperty(localName = "MirrorURL")
    private String mirrorURL;
 
    @JacksonXmlProperty(localName = "ReplaceKeyWith")
    private String replaceKeyWith;
 
    @JacksonXmlProperty(localName = "MirrorSaveOssMeta")
    private Boolean mirrorSaveOssMeta;
 
    @JacksonXmlProperty(localName = "MirrorAllowGetImageInfo")
    private Boolean mirrorAllowGetImageInfo;
 
    @JacksonXmlProperty(localName = "MirrorFollowRedirect")
    private Boolean mirrorFollowRedirect;
 
    @JacksonXmlProperty(localName = "MirrorURLProbe")
    private String mirrorURLProbe;
 
    @JacksonXmlProperty(localName = "Protocol")
    private String protocol;
 
    @JacksonXmlProperty(localName = "EnableReplacePrefix")
    private Boolean enableReplacePrefix;
 
    @JacksonXmlProperty(localName = "MirrorPassOriginalSlashes")
    private Boolean mirrorPassOriginalSlashes;
 
    @JacksonXmlProperty(localName = "MirrorURLSlave")
    private String mirrorURLSlave;
 
    @JacksonXmlProperty(localName = "MirrorUserLastModified")
    private Boolean mirrorUserLastModified;
 
    @JacksonXmlProperty(localName = "MirrorSwitchAllErrors")
    private Boolean mirrorSwitchAllErrors;
 
    @JacksonXmlProperty(localName = "MirrorSNI")
    private Boolean mirrorSNI;
 
    @JacksonXmlProperty(localName = "MirrorCheckMd5")
    private Boolean mirrorCheckMd5;
 
    @JacksonXmlProperty(localName = "MirrorMultiAlternates")
    private MirrorMultiAlternates mirrorMultiAlternates;

    public RoutingRuleRedirect() {}

    private RoutingRuleRedirect(Builder builder) { 
        this.mirrorAllowVideoSnapshot = builder.mirrorAllowVideoSnapshot; 
        this.mirrorAsyncStatus = builder.mirrorAsyncStatus; 
        this.mirrorTaggings = builder.mirrorTaggings; 
        this.mirrorAuth = builder.mirrorAuth; 
        this.hostName = builder.hostName; 
        this.replaceKeyPrefixWith = builder.replaceKeyPrefixWith; 
        this.mirrorDstRegion = builder.mirrorDstRegion; 
        this.mirrorPassQueryString = builder.mirrorPassQueryString; 
        this.httpRedirectCode = builder.httpRedirectCode; 
        this.mirrorDstVpcId = builder.mirrorDstVpcId; 
        this.mirrorHeaders = builder.mirrorHeaders; 
        this.mirrorTunnelId = builder.mirrorTunnelId; 
        this.mirrorRole = builder.mirrorRole; 
        this.redirectType = builder.redirectType; 
        this.passQueryString = builder.passQueryString; 
        this.mirrorUsingRole = builder.mirrorUsingRole; 
        this.mirrorReturnHeaders = builder.mirrorReturnHeaders; 
        this.mirrorProxyPass = builder.mirrorProxyPass; 
        this.mirrorIsExpressTunnel = builder.mirrorIsExpressTunnel; 
        this.mirrorDstSlaveVpcId = builder.mirrorDstSlaveVpcId; 
        this.mirrorAllowHeadObject = builder.mirrorAllowHeadObject; 
        this.transparentMirrorResponseCodes = builder.transparentMirrorResponseCodes; 
        this.mirrorURL = builder.mirrorURL; 
        this.replaceKeyWith = builder.replaceKeyWith; 
        this.mirrorSaveOssMeta = builder.mirrorSaveOssMeta; 
        this.mirrorAllowGetImageInfo = builder.mirrorAllowGetImageInfo; 
        this.mirrorFollowRedirect = builder.mirrorFollowRedirect; 
        this.mirrorURLProbe = builder.mirrorURLProbe; 
        this.protocol = builder.protocol; 
        this.enableReplacePrefix = builder.enableReplacePrefix; 
        this.mirrorPassOriginalSlashes = builder.mirrorPassOriginalSlashes; 
        this.mirrorURLSlave = builder.mirrorURLSlave; 
        this.mirrorUserLastModified = builder.mirrorUserLastModified; 
        this.mirrorSwitchAllErrors = builder.mirrorSwitchAllErrors; 
        this.mirrorSNI = builder.mirrorSNI; 
        this.mirrorCheckMd5 = builder.mirrorCheckMd5; 
        this.mirrorMultiAlternates = builder.mirrorMultiAlternates; 
    }

    /**
    * Whether to allow take video snapshot in mirror-based back-to-origin.
    */
    public Boolean mirrorAllowVideoSnapshot() {
        return this.mirrorAllowVideoSnapshot;
    }

    /**
    * The HTTP status codes that trigger the asynchronous pull mode in mirror-based back-to-origin.
    */
    public Long mirrorAsyncStatus() {
        return this.mirrorAsyncStatus;
    }

    /**
    * The rules for setting tags when saving files during mirror-based back-to-origin.
    */
    public MirrorTaggings mirrorTaggings() {
        return this.mirrorTaggings;
    }

    /**
    * The authentication information for the origin server in mirror-based back-to-origin.
    */
    public MirrorAuth mirrorAuth() {
        return this.mirrorAuth;
    }

    /**
    * The domain name used for redirection. The domain name must comply with the domain naming rules. For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`.
    */
    public String hostName() {
        return this.hostName;
    }

    /**
    * The string that is used to replace the prefix of the object name during redirection. If the prefix of an object name is empty, the string precedes the object name.  You can specify only one of the ReplaceKeyWith and ReplaceKeyPrefixWith parameters in a rule. For example, if you access an object named abc/test.txt, KeyPrefixEquals is set to abc/, ReplaceKeyPrefixWith is set to def/, the value of the Location header is `http://example.com/def/test.txt`.
    */
    public String replaceKeyPrefixWith() {
        return this.replaceKeyPrefixWith;
    }

    /**
    * The VPC region for mirror-based back-to-origin express tunnel.
    */
    public String mirrorDstRegion() {
        return this.mirrorDstRegion;
    }

    /**
    * This parameter plays the same role as PassQueryString and has a higher priority than PassQueryString. This parameter takes effect only when the value of RedirectType is Mirror. Default value: false.Valid values:*   true            *   false            
    */
    public Boolean mirrorPassQueryString() {
        return this.mirrorPassQueryString;
    }

    /**
    * The HTTP redirect code in the response. This parameter takes effect only when RedirectType is set to External or AliCDN. Valid values: 301, 302, and 307.
    */
    public Long httpRedirectCode() {
        return this.httpRedirectCode;
    }

    /**
    * The VPC ID for mirror-based back-to-origin express tunnel.
    */
    public String mirrorDstVpcId() {
        return this.mirrorDstVpcId;
    }

    /**
    * The headers contained in the response that is returned when you use mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
    */
    public MirrorHeaders mirrorHeaders() {
        return this.mirrorHeaders;
    }

    /**
    * The tunnel ID for mirror-based back-to-origin.
    */
    public String mirrorTunnelId() {
        return this.mirrorTunnelId;
    }

    /**
    * The role name used for mirror-based back-to-origin.
    */
    public String mirrorRole() {
        return this.mirrorRole;
    }

    /**
    * The redirection type. Valid values:*   **Mirror**: mirroring-based back-to-origin.*   **External**: external redirection. OSS returns an HTTP 3xx status code and returns an address for you to redirect to.*   **AliCDN**: redirection based on Alibaba Cloud CDN. Compared with external redirection, OSS adds an additional header to the request. After Alibaba Cloud CDN identifies the header, Alibaba Cloud CDN redirects the access to the specified address and returns the obtained data instead of the HTTP 3xx status code that redirects the access to another address.  This parameter must be specified if Redirect is specified.
    */
    public String redirectType() {
        return this.redirectType;
    }

    /**
    * Specifies whether to include parameters of the original request in the redirection request when the system runs the redirection rule or mirroring-based back-to-origin rule. For example, if the {@code PassQueryString} parameter is set to true, the {@code ?a=b&amp;c=d} parameter string is included in a request sent to OSS, and the redirection mode is 302, this parameter is added to the Location header. For example, if the request is {@code Location: example.com?a=b&amp;c=d} and the redirection type is mirroring-based back-to-origin, the {@code ?a=b&amp;c=d} parameter string is also included in the back-to-origin request. Valid values: true and false (default).
    */
    public Boolean passQueryString() {
        return this.passQueryString;
    }

    /**
    * Whether to use role for mirror-based back-to-origin.
    */
    public Boolean mirrorUsingRole() {
        return this.mirrorUsingRole;
    }

    /**
    * Container to store the rules for setting response headers in mirror-based back-to-origin.
    */
    public MirrorReturnHeaders mirrorReturnHeaders() {
        return this.mirrorReturnHeaders;
    }

    /**
    * Not save data in web-based back-to-origin.
    */
    public Boolean mirrorProxyPass() {
        return this.mirrorProxyPass;
    }

    /**
    * Mirror-based back-to-origin with express tunnel.
    */
    public Boolean mirrorIsExpressTunnel() {
        return this.mirrorIsExpressTunnel;
    }

    /**
    * The slave VPC ID for mirror-based back-to-origin express tunnel.
    */
    public String mirrorDstSlaveVpcId() {
        return this.mirrorDstSlaveVpcId;
    }

    /**
    * Whether to allow take HeadObject in mirror-based back-to-origin.
    */
    public Boolean mirrorAllowHeadObject() {
        return this.mirrorAllowHeadObject;
    }

    /**
    * Specify which status codes returned by the origin server should be passed through to the client along with the body. The value should be HTTP status codes such as 4xx, 5xx, etc., separated by commas (,), for example, 400,404. This setting takes effect only when RedirectType is set to Mirror. When OSS requests content from the origin server, if the origin server returns one of the status codes specified in this parameter, OSS will pass through the status code and body returned by the origin server to the client. If the 404 status code is specified in this parameter, the configured ErrorDocument will be ineffective.
    */
    public String transparentMirrorResponseCodes() {
        return this.transparentMirrorResponseCodes;
    }

    /**
    * The origin URL for mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror. The origin URL must start with \*\*http://** or **https://\*\* and end with a forward slash (/). OSS adds an object name to the end of the URL to generate a back-to-origin URL. For example, the name of the object to access is myobject. If MirrorURL is set to `http://example.com/`, the back-to-origin URL is `http://example.com/myobject`. If MirrorURL is set to `http://example.com/dir1/`, the back-to-origin URL is `http://example.com/dir1/myobject`.  This parameter must be specified if RedirectType is set to Mirror.Valid values:*   true            *   false            
    */
    public String mirrorURL() {
        return this.mirrorURL;
    }

    /**
    * The string that is used to replace the requested object name when the request is redirected. This parameter can be set to the ${key} variable, which indicates the object name in the request. For example, if ReplaceKeyWith is set to `prefix/${key}.suffix` and the object to access is test, the value of the Location header is `http://example.com/prefix/test.suffix`.
    */
    public String replaceKeyWith() {
        return this.replaceKeyWith;
    }

    /**
    * Whether to store the user defined metadata in mirror-based back-to-origin.
    */
    public Boolean mirrorSaveOssMeta() {
        return this.mirrorSaveOssMeta;
    }

    /**
    * Whether to allow get image information in mirror-based back-to-origin.
    */
    public Boolean mirrorAllowGetImageInfo() {
        return this.mirrorAllowGetImageInfo;
    }

    /**
    * Specifies whether to redirect the access to the address specified by Location if the origin returns an HTTP 3xx status code. This parameter takes effect only when the value of RedirectType is Mirror. For example, when a mirroring-based back-to-origin request is initiated, the origin returns 302 and Location is specified.*   If you set MirrorFollowRedirect to true, OSS continues requesting the resource at the address specified by Location. The access can be redirected up to 10 times. If the access is redirected more than 10 times, the mirroring-based back-to-origin request fails.*   If you set MirrorFollowRedirect to false, OSS returns 302 and passes through Location.Default value: true.
    */
    public Boolean mirrorFollowRedirect() {
        return this.mirrorFollowRedirect;
    }

    /**
    * The probe URL for mirror-based back-to-origin.
    */
    public String mirrorURLProbe() {
        return this.mirrorURLProbe;
    }

    /**
    * The protocol used for redirection. This parameter takes effect only when RedirectType is set to External or AliCDN. For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`. Valid values: **http** and **https**.
    */
    public String protocol() {
        return this.protocol;
    }

    /**
    * If this parameter is set to true, the prefix of the object names is replaced with the value specified by ReplaceKeyPrefixWith. If this parameter is not specified or empty, the prefix of object names is truncated.  When the ReplaceKeyWith parameter is not empty, the EnableReplacePrefix parameter cannot be set to true.Default value: false.
    */
    public Boolean enableReplacePrefix() {
        return this.enableReplacePrefix;
    }

    /**
    * Whether to pass slashes to origin.
    */
    public Boolean mirrorPassOriginalSlashes() {
        return this.mirrorPassOriginalSlashes;
    }

    /**
    * The slave URL for mirror-based back-to-origin.
    */
    public String mirrorURLSlave() {
        return this.mirrorURLSlave;
    }

    /**
    * Use LastModifiedTime of the file from origin.
    */
    public Boolean mirrorUserLastModified() {
        return this.mirrorUserLastModified;
    }

    /**
    * Used for determining the state of primary-secondary switching. The logic for primary-secondary switching is based on the error code returned by the origin server. If MirrorSwitchAllErrors is set to true, all status codes except the following are considered failures: 200, 206, 301, 302, 303, 307, 404. If it is set to false, only status codes in the 5xx range or timeouts are considered failures.
    */
    public Boolean mirrorSwitchAllErrors() {
        return this.mirrorSwitchAllErrors;
    }

    /**
    * 是否透传SNI
    */
    public Boolean mirrorSNI() {
        return this.mirrorSNI;
    }

    /**
    * Specifies whether to check the MD5 hash of the body of the response returned by the origin. This parameter takes effect only when the value of RedirectType is Mirror. When **MirrorCheckMd5** is set to true and the response returned by the origin includes the Content-Md5 header, OSS checks whether the MD5 hash of the obtained data matches the header value. If the MD5 hash of the obtained data does not match the header value, the obtained data is not stored in OSS. Default value: false.
    */
    public Boolean mirrorCheckMd5() {
        return this.mirrorCheckMd5;
    }

    /**
    * The container to store the configuration for multiple origins in mirror-based back-to-origin.
    */
    public MirrorMultiAlternates mirrorMultiAlternates() {
        return this.mirrorMultiAlternates;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder { 
        private Boolean mirrorAllowVideoSnapshot;
        private Long mirrorAsyncStatus;
        private MirrorTaggings mirrorTaggings;
        private MirrorAuth mirrorAuth;
        private String hostName;
        private String replaceKeyPrefixWith;
        private String mirrorDstRegion;
        private Boolean mirrorPassQueryString;
        private Long httpRedirectCode;
        private String mirrorDstVpcId;
        private MirrorHeaders mirrorHeaders;
        private String mirrorTunnelId;
        private String mirrorRole;
        private String redirectType;
        private Boolean passQueryString;
        private Boolean mirrorUsingRole;
        private MirrorReturnHeaders mirrorReturnHeaders;
        private Boolean mirrorProxyPass;
        private Boolean mirrorIsExpressTunnel;
        private String mirrorDstSlaveVpcId;
        private Boolean mirrorAllowHeadObject;
        private String transparentMirrorResponseCodes;
        private String mirrorURL;
        private String replaceKeyWith;
        private Boolean mirrorSaveOssMeta;
        private Boolean mirrorAllowGetImageInfo;
        private Boolean mirrorFollowRedirect;
        private String mirrorURLProbe;
        private String protocol;
        private Boolean enableReplacePrefix;
        private Boolean mirrorPassOriginalSlashes;
        private String mirrorURLSlave;
        private Boolean mirrorUserLastModified;
        private Boolean mirrorSwitchAllErrors;
        private Boolean mirrorSNI;
        private Boolean mirrorCheckMd5;
        private MirrorMultiAlternates mirrorMultiAlternates;
        
        /**
        * Whether to allow take video snapshot in mirror-based back-to-origin.
        */
        public Builder mirrorAllowVideoSnapshot(Boolean value) {
            requireNonNull(value);
            this.mirrorAllowVideoSnapshot = value;
            return this;
        }
        
        /**
        * The HTTP status codes that trigger the asynchronous pull mode in mirror-based back-to-origin.
        */
        public Builder mirrorAsyncStatus(Long value) {
            requireNonNull(value);
            this.mirrorAsyncStatus = value;
            return this;
        }
        
        /**
        * The rules for setting tags when saving files during mirror-based back-to-origin.
        */
        public Builder mirrorTaggings(MirrorTaggings value) {
            requireNonNull(value);
            this.mirrorTaggings = value;
            return this;
        }
        
        /**
        * The authentication information for the origin server in mirror-based back-to-origin.
        */
        public Builder mirrorAuth(MirrorAuth value) {
            requireNonNull(value);
            this.mirrorAuth = value;
            return this;
        }
        
        /**
        * The domain name used for redirection. The domain name must comply with the domain naming rules. For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`.
        */
        public Builder hostName(String value) {
            requireNonNull(value);
            this.hostName = value;
            return this;
        }
        
        /**
        * The string that is used to replace the prefix of the object name during redirection. If the prefix of an object name is empty, the string precedes the object name.  You can specify only one of the ReplaceKeyWith and ReplaceKeyPrefixWith parameters in a rule. For example, if you access an object named abc/test.txt, KeyPrefixEquals is set to abc/, ReplaceKeyPrefixWith is set to def/, the value of the Location header is `http://example.com/def/test.txt`.
        */
        public Builder replaceKeyPrefixWith(String value) {
            requireNonNull(value);
            this.replaceKeyPrefixWith = value;
            return this;
        }
        
        /**
        * The VPC region for mirror-based back-to-origin express tunnel.
        */
        public Builder mirrorDstRegion(String value) {
            requireNonNull(value);
            this.mirrorDstRegion = value;
            return this;
        }
        
        /**
        * This parameter plays the same role as PassQueryString and has a higher priority than PassQueryString. This parameter takes effect only when the value of RedirectType is Mirror. Default value: false.Valid values:*   true            *   false            
        */
        public Builder mirrorPassQueryString(Boolean value) {
            requireNonNull(value);
            this.mirrorPassQueryString = value;
            return this;
        }
        
        /**
        * The HTTP redirect code in the response. This parameter takes effect only when RedirectType is set to External or AliCDN. Valid values: 301, 302, and 307.
        */
        public Builder httpRedirectCode(Long value) {
            requireNonNull(value);
            this.httpRedirectCode = value;
            return this;
        }
        
        /**
        * The VPC ID for mirror-based back-to-origin express tunnel.
        */
        public Builder mirrorDstVpcId(String value) {
            requireNonNull(value);
            this.mirrorDstVpcId = value;
            return this;
        }
        
        /**
        * The headers contained in the response that is returned when you use mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror.
        */
        public Builder mirrorHeaders(MirrorHeaders value) {
            requireNonNull(value);
            this.mirrorHeaders = value;
            return this;
        }
        
        /**
        * The tunnel ID for mirror-based back-to-origin.
        */
        public Builder mirrorTunnelId(String value) {
            requireNonNull(value);
            this.mirrorTunnelId = value;
            return this;
        }
        
        /**
        * The role name used for mirror-based back-to-origin.
        */
        public Builder mirrorRole(String value) {
            requireNonNull(value);
            this.mirrorRole = value;
            return this;
        }
        
        /**
        * The redirection type. Valid values:*   **Mirror**: mirroring-based back-to-origin.*   **External**: external redirection. OSS returns an HTTP 3xx status code and returns an address for you to redirect to.*   **AliCDN**: redirection based on Alibaba Cloud CDN. Compared with external redirection, OSS adds an additional header to the request. After Alibaba Cloud CDN identifies the header, Alibaba Cloud CDN redirects the access to the specified address and returns the obtained data instead of the HTTP 3xx status code that redirects the access to another address.  This parameter must be specified if Redirect is specified.
        */
        public Builder redirectType(String value) {
            requireNonNull(value);
            this.redirectType = value;
            return this;
        }
        
        /**
        * Specifies whether to include parameters of the original request in the redirection request when the system runs the redirection rule or mirroring-based back-to-origin rule. For example, if the {@code PassQueryString} parameter is set to true, the {@code ?a=b&amp;c=d} parameter string is included in a request sent to OSS, and the redirection mode is 302, this parameter is added to the Location header. For example, if the request is {@code Location: example.com?a=b&amp;c=d} and the redirection type is mirroring-based back-to-origin, the {@code ?a=b&amp;c=d} parameter string is also included in the back-to-origin request. Valid values: true and false (default).
        */
        public Builder passQueryString(Boolean value) {
            requireNonNull(value);
            this.passQueryString = value;
            return this;
        }
        
        /**
        * Whether to use role for mirror-based back-to-origin.
        */
        public Builder mirrorUsingRole(Boolean value) {
            requireNonNull(value);
            this.mirrorUsingRole = value;
            return this;
        }
        
        /**
        * Container to store the rules for setting response headers in mirror-based back-to-origin.
        */
        public Builder mirrorReturnHeaders(MirrorReturnHeaders value) {
            requireNonNull(value);
            this.mirrorReturnHeaders = value;
            return this;
        }
        
        /**
        * Not save data in web-based back-to-origin.
        */
        public Builder mirrorProxyPass(Boolean value) {
            requireNonNull(value);
            this.mirrorProxyPass = value;
            return this;
        }
        
        /**
        * Mirror-based back-to-origin with express tunnel.
        */
        public Builder mirrorIsExpressTunnel(Boolean value) {
            requireNonNull(value);
            this.mirrorIsExpressTunnel = value;
            return this;
        }
        
        /**
        * The slave VPC ID for mirror-based back-to-origin express tunnel.
        */
        public Builder mirrorDstSlaveVpcId(String value) {
            requireNonNull(value);
            this.mirrorDstSlaveVpcId = value;
            return this;
        }
        
        /**
        * Whether to allow take HeadObject in mirror-based back-to-origin.
        */
        public Builder mirrorAllowHeadObject(Boolean value) {
            requireNonNull(value);
            this.mirrorAllowHeadObject = value;
            return this;
        }
        
        /**
        * Specify which status codes returned by the origin server should be passed through to the client along with the body. The value should be HTTP status codes such as 4xx, 5xx, etc., separated by commas (,), for example, 400,404. This setting takes effect only when RedirectType is set to Mirror. When OSS requests content from the origin server, if the origin server returns one of the status codes specified in this parameter, OSS will pass through the status code and body returned by the origin server to the client. If the 404 status code is specified in this parameter, the configured ErrorDocument will be ineffective.
        */
        public Builder transparentMirrorResponseCodes(String value) {
            requireNonNull(value);
            this.transparentMirrorResponseCodes = value;
            return this;
        }
        
        /**
        * The origin URL for mirroring-based back-to-origin. This parameter takes effect only when the value of RedirectType is Mirror. The origin URL must start with \*\*http://** or **https://\*\* and end with a forward slash (/). OSS adds an object name to the end of the URL to generate a back-to-origin URL. For example, the name of the object to access is myobject. If MirrorURL is set to `http://example.com/`, the back-to-origin URL is `http://example.com/myobject`. If MirrorURL is set to `http://example.com/dir1/`, the back-to-origin URL is `http://example.com/dir1/myobject`.  This parameter must be specified if RedirectType is set to Mirror.Valid values:*   true            *   false            
        */
        public Builder mirrorURL(String value) {
            requireNonNull(value);
            this.mirrorURL = value;
            return this;
        }
        
        /**
        * The string that is used to replace the requested object name when the request is redirected. This parameter can be set to the ${key} variable, which indicates the object name in the request. For example, if ReplaceKeyWith is set to `prefix/${key}.suffix` and the object to access is test, the value of the Location header is `http://example.com/prefix/test.suffix`.
        */
        public Builder replaceKeyWith(String value) {
            requireNonNull(value);
            this.replaceKeyWith = value;
            return this;
        }
        
        /**
        * Whether to store the user defined metadata in mirror-based back-to-origin.
        */
        public Builder mirrorSaveOssMeta(Boolean value) {
            requireNonNull(value);
            this.mirrorSaveOssMeta = value;
            return this;
        }
        
        /**
        * Whether to allow get image information in mirror-based back-to-origin.
        */
        public Builder mirrorAllowGetImageInfo(Boolean value) {
            requireNonNull(value);
            this.mirrorAllowGetImageInfo = value;
            return this;
        }
        
        /**
        * Specifies whether to redirect the access to the address specified by Location if the origin returns an HTTP 3xx status code. This parameter takes effect only when the value of RedirectType is Mirror. For example, when a mirroring-based back-to-origin request is initiated, the origin returns 302 and Location is specified.*   If you set MirrorFollowRedirect to true, OSS continues requesting the resource at the address specified by Location. The access can be redirected up to 10 times. If the access is redirected more than 10 times, the mirroring-based back-to-origin request fails.*   If you set MirrorFollowRedirect to false, OSS returns 302 and passes through Location.Default value: true.
        */
        public Builder mirrorFollowRedirect(Boolean value) {
            requireNonNull(value);
            this.mirrorFollowRedirect = value;
            return this;
        }
        
        /**
        * The probe URL for mirror-based back-to-origin.
        */
        public Builder mirrorURLProbe(String value) {
            requireNonNull(value);
            this.mirrorURLProbe = value;
            return this;
        }
        
        /**
        * The protocol used for redirection. This parameter takes effect only when RedirectType is set to External or AliCDN. For example, if you access an object named test, Protocol is set to https, and Hostname is set to `example.com`, the value of the Location header is `https://example.com/test`. Valid values: **http** and **https**.
        */
        public Builder protocol(String value) {
            requireNonNull(value);
            this.protocol = value;
            return this;
        }
        
        /**
        * If this parameter is set to true, the prefix of the object names is replaced with the value specified by ReplaceKeyPrefixWith. If this parameter is not specified or empty, the prefix of object names is truncated.  When the ReplaceKeyWith parameter is not empty, the EnableReplacePrefix parameter cannot be set to true.Default value: false.
        */
        public Builder enableReplacePrefix(Boolean value) {
            requireNonNull(value);
            this.enableReplacePrefix = value;
            return this;
        }
        
        /**
        * Whether to pass slashes to origin.
        */
        public Builder mirrorPassOriginalSlashes(Boolean value) {
            requireNonNull(value);
            this.mirrorPassOriginalSlashes = value;
            return this;
        }
        
        /**
        * The slave URL for mirror-based back-to-origin.
        */
        public Builder mirrorURLSlave(String value) {
            requireNonNull(value);
            this.mirrorURLSlave = value;
            return this;
        }
        
        /**
        * Use LastModifiedTime of the file from origin.
        */
        public Builder mirrorUserLastModified(Boolean value) {
            requireNonNull(value);
            this.mirrorUserLastModified = value;
            return this;
        }
        
        /**
        * Used for determining the state of primary-secondary switching. The logic for primary-secondary switching is based on the error code returned by the origin server. If MirrorSwitchAllErrors is set to true, all status codes except the following are considered failures: 200, 206, 301, 302, 303, 307, 404. If it is set to false, only status codes in the 5xx range or timeouts are considered failures.
        */
        public Builder mirrorSwitchAllErrors(Boolean value) {
            requireNonNull(value);
            this.mirrorSwitchAllErrors = value;
            return this;
        }
        
        /**
        * 是否透传SNI
        */
        public Builder mirrorSNI(Boolean value) {
            requireNonNull(value);
            this.mirrorSNI = value;
            return this;
        }
        
        /**
        * Specifies whether to check the MD5 hash of the body of the response returned by the origin. This parameter takes effect only when the value of RedirectType is Mirror. When **MirrorCheckMd5** is set to true and the response returned by the origin includes the Content-Md5 header, OSS checks whether the MD5 hash of the obtained data matches the header value. If the MD5 hash of the obtained data does not match the header value, the obtained data is not stored in OSS. Default value: false.
        */
        public Builder mirrorCheckMd5(Boolean value) {
            requireNonNull(value);
            this.mirrorCheckMd5 = value;
            return this;
        }
        
        /**
        * The container to store the configuration for multiple origins in mirror-based back-to-origin.
        */
        public Builder mirrorMultiAlternates(MirrorMultiAlternates value) {
            requireNonNull(value);
            this.mirrorMultiAlternates = value;
            return this;
        }
        

        private Builder() {
            super();
        }

        private Builder(RoutingRuleRedirect from) { 
            this.mirrorAllowVideoSnapshot = from.mirrorAllowVideoSnapshot; 
            this.mirrorAsyncStatus = from.mirrorAsyncStatus; 
            this.mirrorTaggings = from.mirrorTaggings; 
            this.mirrorAuth = from.mirrorAuth; 
            this.hostName = from.hostName; 
            this.replaceKeyPrefixWith = from.replaceKeyPrefixWith; 
            this.mirrorDstRegion = from.mirrorDstRegion; 
            this.mirrorPassQueryString = from.mirrorPassQueryString; 
            this.httpRedirectCode = from.httpRedirectCode; 
            this.mirrorDstVpcId = from.mirrorDstVpcId; 
            this.mirrorHeaders = from.mirrorHeaders; 
            this.mirrorTunnelId = from.mirrorTunnelId; 
            this.mirrorRole = from.mirrorRole; 
            this.redirectType = from.redirectType; 
            this.passQueryString = from.passQueryString; 
            this.mirrorUsingRole = from.mirrorUsingRole; 
            this.mirrorReturnHeaders = from.mirrorReturnHeaders; 
            this.mirrorProxyPass = from.mirrorProxyPass; 
            this.mirrorIsExpressTunnel = from.mirrorIsExpressTunnel; 
            this.mirrorDstSlaveVpcId = from.mirrorDstSlaveVpcId; 
            this.mirrorAllowHeadObject = from.mirrorAllowHeadObject; 
            this.transparentMirrorResponseCodes = from.transparentMirrorResponseCodes; 
            this.mirrorURL = from.mirrorURL; 
            this.replaceKeyWith = from.replaceKeyWith; 
            this.mirrorSaveOssMeta = from.mirrorSaveOssMeta; 
            this.mirrorAllowGetImageInfo = from.mirrorAllowGetImageInfo; 
            this.mirrorFollowRedirect = from.mirrorFollowRedirect; 
            this.mirrorURLProbe = from.mirrorURLProbe; 
            this.protocol = from.protocol; 
            this.enableReplacePrefix = from.enableReplacePrefix; 
            this.mirrorPassOriginalSlashes = from.mirrorPassOriginalSlashes; 
            this.mirrorURLSlave = from.mirrorURLSlave; 
            this.mirrorUserLastModified = from.mirrorUserLastModified; 
            this.mirrorSwitchAllErrors = from.mirrorSwitchAllErrors; 
            this.mirrorSNI = from.mirrorSNI; 
            this.mirrorCheckMd5 = from.mirrorCheckMd5; 
            this.mirrorMultiAlternates = from.mirrorMultiAlternates; 
        }

        public RoutingRuleRedirect build() {
            return new RoutingRuleRedirect(this);
        }
    }
}
