package com.aliyun.sdk.service.oss2.tables.models;


public final class CreateTableBucketRequest extends TableRequestModel {

    private CreateTableBucketRequest(Builder builder) {
        super(builder);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String name() {
        return (String) this.bodyFields.get("name");
    }

    public EncryptionConfiguration encryptionConfiguration() {
        return (EncryptionConfiguration) this.bodyFields.get("encryptionConfiguration");
    }


    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends TableRequestModel.Builder<Builder> {

        private Builder() {
            super();
        }

        private Builder(CreateTableBucketRequest from) {
            super(from);
        }

        public Builder name(String value) {
            this.bodyFields.put("name", value);
            return this;
        }

        public Builder encryptionConfiguration(EncryptionConfiguration value) {
            this.bodyFields.put("encryptionConfiguration", value);
            return this;
        }



        public CreateTableBucketRequest build() {
            return new CreateTableBucketRequest(this);
        }
    }
}
