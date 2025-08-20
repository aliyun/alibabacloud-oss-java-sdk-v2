package com.aliyun.sdk.service.oss2.transport;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface ObservableByteChannel extends WritableByteChannel {
    void finished() throws IOException;
}
