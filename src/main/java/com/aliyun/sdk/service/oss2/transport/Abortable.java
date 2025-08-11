package com.aliyun.sdk.service.oss2.transport;

/**
 * An Abortable task.
 */
public interface Abortable {
    /**
     * Aborts the execution of the task. Multiple calls to abort or calling abort an already aborted task
     * should return without error.
     */
    void abort();
}
