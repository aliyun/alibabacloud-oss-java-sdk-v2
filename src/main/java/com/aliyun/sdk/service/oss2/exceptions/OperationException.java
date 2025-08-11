package com.aliyun.sdk.service.oss2.exceptions;

public class OperationException extends RuntimeException {
    static final String fmt = "Operation %s raised an exception:\n%s";
    private final String opName;

    public OperationException(String opName) {
        super(String.format(fmt, opName, ""));
        this.opName = opName;
    }

    public OperationException(String opName, Throwable cause) {
        super(String.format(fmt, opName, cause), cause);
        this.opName = opName;
    }

    public String opName() {
        return this.opName;
    }

    public <T> Throwable contains(Class<T> clz) {
        Throwable next = this.getCause();
        do {
            if (next == null) {
                break;
            }
            if (next.getClass().equals(clz)) {
                break;
            }
            next = next.getCause();
        } while (true);
        return next;
    }
}
