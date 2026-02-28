package com.aliyun.sdk.service.oss2.imm;

import com.aliyun.sdk.service.oss2.OperationInput;
import com.aliyun.sdk.service.oss2.OperationOptions;
import com.aliyun.sdk.service.oss2.OperationOutput;
import com.aliyun.sdk.service.oss2.imm.models.*;


/**
 * A client for accessing IMM synchronously.
 * This can be created using the static {@link #newBuilder()} method.
 */
public interface IMMClient extends AutoCloseable {

    static IMMClientBuilder newBuilder() {
        return new DefaultIMMClientBuilder();
    }

    // common api
    //-----------------------------------------------------------------------
    default OperationOutput invokeOperation(OperationInput input, OperationOptions opts) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------

    // Dataset api
    //-----------------------------------------------------------------------
    /**
     * Creates a dataset.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @return A {@link CreateDatasetResult} for CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateDatasetResult createDataset(CreateDatasetRequest request) {
        return createDataset(request, OperationOptions.defaults());
    }

    /**
     * Creates a vector bucket.
     *
     * @param request A {@link CreateDatasetRequest} for CreateDataset operation.
     * @param options The operation options.
     * @return A {@link CreateDatasetResult} for CreateDataset operation.
     * @throws RuntimeException If an error occurs
     */
    default CreateDatasetResult createDataset(CreateDatasetRequest request, OperationOptions options) {
        throw new UnsupportedOperationException();
    }
    //-----------------------------------------------------------------------
}
