package com.aliyun.sdk.service.oss2.models;

import static java.util.Objects.requireNonNull;

/**
 * The request for the PutStyle operation.
 */
public final class PutStyleRequest extends RequestModel { 
    private final String bucket;
    private final Style style;

    private PutStyleRequest(Builder builder) {
        super(builder);
        this.bucket = builder.bucket;
        this.style = builder.style;
    }
    
    /**
     * The name of the bucket.
     */
    public String bucket() {
        return bucket;
    }
    
    /**
     * The name of the image style.
     */
    public String styleName() {
        String value = parameters.get("styleName");
        return value;
    }
    
    /**
     * The category of the style.
     */
    public String category() {
        String value = parameters.get("category");
        return value;
    }
    
    /**
     * The container that stores the content information about the image style.
     */
    public Style style() {
        return style;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends RequestModel.Builder<Builder> { 
        private String bucket;
        private Style style;
    
        /**
        * The name of the bucket.
        */
        public Builder bucket(String value) {
            requireNonNull(value);
            this.bucket = value;
            return this;        
        }
    
        /**
        * The name of the image style.
        */
        public Builder styleName(String value) {
            requireNonNull(value);
            this.parameters.put("styleName", value);
            return this;        
        }
    
        /**
        * The category of the style.
        */
        public Builder category(String value) {
            requireNonNull(value);
            this.parameters.put("category", value);
            return this;        
        }
    
        /**
        * The container that stores the content information about the image style.
        */
        public Builder style(Style value) {
            requireNonNull(value);
            this.style = value;
            return this;        
        }
        
        public PutStyleRequest build() {
            return new PutStyleRequest(this);
        }

        private Builder() {
            super();
        }

        private Builder(PutStyleRequest request) {
            super(request);
            this.bucket = request.bucket;
            this.style = request.style;
        }        
    }

}
