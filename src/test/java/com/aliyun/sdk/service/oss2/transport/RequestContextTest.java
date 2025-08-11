package com.aliyun.sdk.service.oss2.transport;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestContextTest {

    @Test
    public void requestContext() {
        RequestContext ctx = RequestContext.empty();
        assertFalse(ctx.containsKey(RequestContext.Key.HTTP_COMPLETION_OPTION));

        ctx.put(RequestContext.Key.HTTP_COMPLETION_OPTION, RequestContext.HttpCompletionOption.ResponseContentRead);
        assertTrue(ctx.containsKey(RequestContext.Key.HTTP_COMPLETION_OPTION));
        assertSame(RequestContext.HttpCompletionOption.ResponseContentRead, ctx.get(RequestContext.Key.HTTP_COMPLETION_OPTION));

        ctx.put(RequestContext.Key.HTTP_COMPLETION_OPTION, RequestContext.HttpCompletionOption.ResponseHeadersRead);
        assertTrue(ctx.containsKey(RequestContext.Key.HTTP_COMPLETION_OPTION));
        assertSame(RequestContext.HttpCompletionOption.ResponseHeadersRead, ctx.get(RequestContext.Key.HTTP_COMPLETION_OPTION));
    }
}
