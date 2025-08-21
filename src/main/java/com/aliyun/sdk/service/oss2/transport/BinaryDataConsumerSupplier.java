package com.aliyun.sdk.service.oss2.transport;

import java.util.function.Supplier;

public interface BinaryDataConsumerSupplier extends Supplier<Object> {

    boolean isReplayable();

    boolean autoRelease();
}
