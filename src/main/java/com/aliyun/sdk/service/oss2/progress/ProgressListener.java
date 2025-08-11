package com.aliyun.sdk.service.oss2.progress;

public interface ProgressListener {

    void onProgress(long increment, long transferred, long total);

    void onFinish();

}
