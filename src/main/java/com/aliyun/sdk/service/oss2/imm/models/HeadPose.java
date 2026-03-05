package com.aliyun.sdk.service.oss2.imm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeadPose {

    @JsonProperty("Pitch")
    private Float pitch;

    @JsonProperty("Roll")
    private Float roll;

    @JsonProperty("Yaw")
    private Float yaw;

    public HeadPose() {
    }

    public Float getPitch() {
        return pitch;
    }

    public HeadPose setPitch(Float pitch) {
        this.pitch = pitch;
        return this;
    }

    public Float getRoll() {
        return roll;
    }

    public HeadPose setRoll(Float roll) {
        this.roll = roll;
        return this;
    }

    public Float getYaw() {
        return yaw;
    }

    public HeadPose setYaw(Float yaw) {
        this.yaw = yaw;
        return this;
    }
}
