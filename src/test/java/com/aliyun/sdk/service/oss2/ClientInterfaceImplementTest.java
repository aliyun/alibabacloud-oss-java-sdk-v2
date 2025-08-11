package com.aliyun.sdk.service.oss2;

import com.aliyun.sdk.service.oss2.credentials.AnonymousCredentialsProvider;
import org.junit.Test;

import static com.aliyun.sdk.service.oss2.internal.ClientImplMockTest.findCause;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

public class ClientInterfaceImplementTest {

    @Test
    public void checkOSSClientImplement() {
        OSSClient client = OSSClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        Class<?> clazz = client.getClass();
        Method[] methods = clazz.getMethods();
        int checkMethodCnt = 0;
        for (Method method : methods) {
            if (method.getParameterCount() != 2) {
                continue;
            }
            Class<?> paramClazz = method.getParameterTypes()[0];
            if (!paramClazz.getName().startsWith("com.aliyun.sdk.service.oss2")) {
                continue;
            }

            try {
                method.invoke(client, null, null);
            } catch (Exception e) {
                UnsupportedOperationException cause = findCause(e, UnsupportedOperationException.class);
                if (cause != null) {
                    fail(method.getName() + "is not implemented");
                }
                checkMethodCnt++;
            }
        }
        assertThat(checkMethodCnt).isGreaterThan(0);
    }

    @Test
    public void checkOSSAsyncClientImplement() {
        OSSAsyncClient client = OSSAsyncClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        Class<?> clazz = client.getClass();
        Method[] methods = clazz.getMethods();
        int checkMethodCnt = 0;
        for (Method method : methods) {
            if (method.getParameterCount() != 2) {
                continue;
            }
            Class<?> paramClazz = method.getParameterTypes()[0];
            if (!paramClazz.getName().startsWith("com.aliyun.sdk.service.oss2")) {
                continue;
            }

            try {
                method.invoke(client, null, null);
            } catch (Exception e) {
                UnsupportedOperationException cause = findCause(e, UnsupportedOperationException.class);
                if (cause != null) {
                    fail(method.getName() + "is not implemented");
                }
                checkMethodCnt++;
            }
        }
        assertThat(checkMethodCnt).isGreaterThan(0);
    }

    @Test
    public void checkOSSDualClientImplement() {
        OSSDualClient client = OSSDualClient.newBuilder()
                .region("cn-hangzhou")
                .credentialsProvider(new AnonymousCredentialsProvider())
                .build();

        Class<?> clazz = client.getClass();
        Method[] methods = clazz.getMethods();
        int checkMethodCnt = 0;
        for (Method method : methods) {
            if (method.getParameterCount() != 2) {
                continue;
            }
            Class<?> paramClazz = method.getParameterTypes()[0];
            if (!paramClazz.getName().startsWith("com.aliyun.sdk.service.oss2")) {
                continue;
            }

            try {
                method.invoke(client, null, null);
            } catch (Exception e) {
                UnsupportedOperationException cause = findCause(e, UnsupportedOperationException.class);
                if (cause != null) {
                    fail(method.getName() + " is not implemented");
                }
                checkMethodCnt++;
            }
        }
        assertThat(checkMethodCnt).isGreaterThan(0);
    }
}
