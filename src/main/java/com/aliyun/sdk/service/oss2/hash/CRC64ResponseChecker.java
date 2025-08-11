package com.aliyun.sdk.service.oss2.hash;

import com.aliyun.sdk.service.oss2.exceptions.InconsistentException;
import com.aliyun.sdk.service.oss2.transport.ResponseMessage;

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.zip.Checksum;

public class CRC64ResponseChecker implements Consumer<ResponseMessage> {

    private final Checksum hash;

    public CRC64ResponseChecker(Checksum hash) {
        Objects.requireNonNull(hash);
        this.hash = hash;
    }

    @Override
    public void accept(ResponseMessage responseMessage) {
        Map<String, String> headers = responseMessage.headers();
        if (headers == null) {
            return;
        }

        String serverCRC = headers.get("x-oss-hash-crc64ecma");
        if (serverCRC == null) {
            return;
        }

        String clientCRC = Long.toUnsignedString(hash.getValue());

        if (!serverCRC.equals(clientCRC)) {
            throw new InconsistentException(clientCRC, serverCRC, headers);
        }
    }
}
