package com.aliyun.sdk.service.oss2.transport.apache5client;

import com.aliyun.sdk.service.oss2.transport.BinaryDataConsumerSupplier;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.CallbackContribution;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.function.Supplier;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.nio.AsyncEntityConsumer;
import org.apache.hc.core5.http.nio.AsyncResponseConsumer;
import org.apache.hc.core5.http.nio.CapacityChannel;
import org.apache.hc.core5.http.nio.entity.AbstractBinAsyncEntityConsumer;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.ByteArrayBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CustomAsyncResponseConsumer implements AsyncResponseConsumer<SimpleHttpResponse> {
    private final BinaryDataConsumerSupplier dataConsumerSupplier;
    private final AtomicReference<AsyncEntityConsumer<byte[]>> dataConsumerRef;

    public CustomAsyncResponseConsumer(final BinaryDataConsumerSupplier dataConsumerSupplier) {
        this.dataConsumerSupplier = dataConsumerSupplier;
        this.dataConsumerRef = new AtomicReference<>();
    }

    private SimpleHttpResponse buildResult(HttpResponse response, byte[] entity, ContentType contentType) {
        SimpleHttpResponse simpleResponse = SimpleHttpResponse.copy(response);
        if (entity != null) {
            simpleResponse.setBody(entity, contentType);
        }

        return simpleResponse;
    }

    private AsyncEntityConsumer<byte[]> getEntityConsumer(int statusCode) {
        if (dataConsumerSupplier == null ||
                statusCode == 203 || statusCode / 100 != 2) {
            return new SimpleAsyncEntityConsumer();
        }

        Object inst = dataConsumerSupplier.get();
        if (inst instanceof WritableByteChannel) {
            return new ByteChannelAsyncEntityConsumer((WritableByteChannel)inst, dataConsumerSupplier.autoRelease());
        } else {
            throw new UnsupportedOperationException("Only supports WritableByteChannel, but got " + inst.getClass().getName());
        }
    }

    @Override
    public final void consumeResponse(
            final HttpResponse response,
            final EntityDetails entityDetails,
            final HttpContext httpContext, final FutureCallback<SimpleHttpResponse> resultCallback) throws HttpException, IOException {
        if (entityDetails != null) {
            final AsyncEntityConsumer<byte[]> dataConsumer = getEntityConsumer(response.getCode());
            if (dataConsumer == null) {
                throw new HttpException("Supplied data consumer is null");
            }
            dataConsumerRef.set(dataConsumer);
            dataConsumer.streamStart(entityDetails, new CallbackContribution<byte[]>(resultCallback) {

                @Override
                public void completed(final byte[] entity) {
                    final ContentType contentType;
                    try {
                        contentType = ContentType.parse(entityDetails.getContentType());
                        final SimpleHttpResponse result = buildResult(response, entity, contentType);
                        if (resultCallback != null) {
                            resultCallback.completed(result);
                        }
                    } catch (final UnsupportedCharsetException ex) {
                        if (resultCallback != null) {
                            resultCallback.failed(ex);
                        }
                    }
                }

            });
        } else {
            final SimpleHttpResponse result = buildResult(response, null, null);
            if (resultCallback != null) {
                resultCallback.completed(result);
            }
        }

    }

    @Override
    public void informationResponse(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {

    }

    @Override
    public final void updateCapacity(final CapacityChannel capacityChannel) throws IOException {
        final AsyncEntityConsumer<byte[]> dataConsumer = dataConsumerRef.get();
        if (dataConsumer != null) {
            dataConsumer.updateCapacity(capacityChannel);
        } else {
            capacityChannel.update(Integer.MAX_VALUE);
        }
    }

    @Override
    public final void consume(final ByteBuffer src) throws IOException {
        final AsyncEntityConsumer<byte[]> dataConsumer = dataConsumerRef.get();
        if (dataConsumer != null) {
            dataConsumer.consume(src);
        }
    }

    @Override
    public final void streamEnd(final List<? extends Header> trailers) throws HttpException, IOException {
        final AsyncEntityConsumer<byte[]> dataConsumer = dataConsumerRef.get();
        if (dataConsumer != null) {
            dataConsumer.streamEnd(trailers);
        }
    }

    @Override
    public final void failed(final Exception cause) {
        releaseResources();
    }

    @Override
    public final void releaseResources() {
        final AsyncEntityConsumer<byte[]> dataConsumer = dataConsumerRef.getAndSet(null);
        if (dataConsumer != null) {
            dataConsumer.releaseResources();
        }
    }

    static class SimpleAsyncEntityConsumer extends AbstractBinAsyncEntityConsumer<byte[]> {

        private final ByteArrayBuffer buffer;

        SimpleAsyncEntityConsumer() {
            super();
            this.buffer = new ByteArrayBuffer(1024);
        }

        @Override
        protected void streamStart(final ContentType contentType) throws HttpException, IOException {
        }

        @Override
        protected int capacityIncrement() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected void data(final ByteBuffer src, final boolean endOfStream) throws IOException {
            if (src == null) {
                return;
            }
            if (src.hasArray()) {
                buffer.append(src.array(), src.arrayOffset() + src.position(), src.remaining());
            } else {
                while (src.hasRemaining()) {
                    buffer.append(src.get());
                }
            }
        }

        @Override
        protected byte[] generateContent() throws IOException {
            return buffer.toByteArray();
        }

        @Override
        public void releaseResources() {
            buffer.clear();
        }

    }

}
