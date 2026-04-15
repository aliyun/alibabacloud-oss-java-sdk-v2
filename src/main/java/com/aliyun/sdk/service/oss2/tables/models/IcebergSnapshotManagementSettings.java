package com.aliyun.sdk.service.oss2.tables.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IcebergSnapshotManagementSettings {

    @JsonProperty("minSnapshotsToKeep")
    private Integer minSnapshotsToKeep;

    @JsonProperty("maxSnapshotAgeHours")
    private Integer maxSnapshotAgeHours;

    public IcebergSnapshotManagementSettings() {
    }

    private IcebergSnapshotManagementSettings(Builder builder) {
        this.minSnapshotsToKeep = builder.minSnapshotsToKeep;
        this.maxSnapshotAgeHours = builder.maxSnapshotAgeHours;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Integer minSnapshotsToKeep() {
        return minSnapshotsToKeep;
    }

    public Integer maxSnapshotAgeHours() {
        return maxSnapshotAgeHours;
    }

    public static class Builder {
        private Integer minSnapshotsToKeep;
        private Integer maxSnapshotAgeHours;

        private Builder() {
        }

        private Builder(IcebergSnapshotManagementSettings model) {
            this.minSnapshotsToKeep = model.minSnapshotsToKeep;
            this.maxSnapshotAgeHours = model.maxSnapshotAgeHours;
        }

        public Builder minSnapshotsToKeep(Integer minSnapshotsToKeep) {
            this.minSnapshotsToKeep = minSnapshotsToKeep;
            return this;
        }

        public Builder maxSnapshotAgeHours(Integer maxSnapshotAgeHours) {
            this.maxSnapshotAgeHours = maxSnapshotAgeHours;
            return this;
        }

        public IcebergSnapshotManagementSettings build() {
            return new IcebergSnapshotManagementSettings(this);
        }
    }
}
