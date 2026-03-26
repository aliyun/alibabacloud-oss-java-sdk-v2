package com.aliyun.sdk.service.oss2.models;

/**
 * The result for the GetObjectLegalHold operation.
 */
public final class GetObjectLegalHoldResult extends ResultModel {
    private final LegalHold legalHold;

    private GetObjectLegalHoldResult(Builder builder) {
        super(builder);
        this.legalHold = builder.legalHold;
    }

    /**
     * The container for object legal hold configuration.
     */
    public LegalHold legalHold() {
        return legalHold;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder extends ResultModel.Builder<Builder> {
        private LegalHold legalHold;

        /**
         * The container for object legal hold configuration.
         */
        public Builder legalHold(LegalHold value) {
            this.legalHold = value;
            return this;
        }

        public GetObjectLegalHoldResult build() {
            return new GetObjectLegalHoldResult(this);
        }

        private Builder() {
            super();
        }

        private Builder(GetObjectLegalHoldResult result) {
            super(result);
            this.legalHold = result.legalHold;
        }
    }
}
