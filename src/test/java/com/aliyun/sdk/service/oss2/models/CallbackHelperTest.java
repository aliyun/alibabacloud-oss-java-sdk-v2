package com.aliyun.sdk.service.oss2.models;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CallbackHelperTest {
    @Test
    public void testEmptyBuilder() {
        CallbackHelper result = CallbackHelper.newBuilder().build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isNull();
        assertThat(result.callbackHost()).isNull();
        assertThat(result.callbackBody()).isNull();
        assertThat(result.callbackBodyType()).isNull();
        assertThat(result.callbackSNI()).isNull();
        assertThat(result.callbackVar()).isNull();

        assertThat(result.toCallbackParameter()).isEqualTo("e30=");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");
    }

    @Test
    public void testFullBuilder() {
        /*
        {
            "callbackUrl":"http://oss-demo.aliyuncs.com:23450",
            "callbackHost":"oss-cn-hangzhou.aliyuncs.com",
            "callbackBody":"bucket=${bucket}&object=${object}&my_var=${x:my_var}",
            "callbackBodyType":"application/x-www-form-urlencoded",
            "callbackSNI":false
        }

        {
          "x:uid": "12345",
          "x:order_id": "67890"
        }
         */

        Map<String, String> varMap = new HashMap<>();
        varMap.put("x:uid", "12345");
        varMap.put("x:order_id", "67890");

        CallbackHelper result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .callbackHost("oss-cn-hangzhou.aliyuncs.com")
                .callbackBody("bucket=${bucket}&object=${object}&my_var=${x:my_var}")
                .callbackBodyType(CallbackHelper.CallbackBodyType.URL_ENCODED)
                .callbackSNI(false)
                .callbackVar(varMap)
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(result.callbackBody()).isEqualTo("bucket=${bucket}&object=${object}&my_var=${x:my_var}");
        assertThat(result.callbackBodyType()).isEqualTo("application/x-www-form-urlencoded");
        assertThat(result.callbackSNI()).isEqualTo(false);
        assertThat(result.callbackVar()).isEqualTo(varMap);

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAiLCJjYWxsYmFja0hvc3QiOiJvc3MtY24taGFuZ3pob3UuYWxpeXVuY3MuY29tIiwiY2FsbGJhY2tCb2R5IjoiYnVja2V0PSR7YnVja2V0fSZvYmplY3Q9JHtvYmplY3R9Jm15X3Zhcj0ke3g6bXlfdmFyfSIsImNhbGxiYWNrQm9keVR5cGUiOiJhcHBsaWNhdGlvbi94LXd3dy1mb3JtLXVybGVuY29kZWQiLCJjYWxsYmFja1NOSSI6ZmFsc2V9");
        assertThat(result.toCallbackVarParameter()).isEqualTo("eyJ4OnVpZCI6IjEyMzQ1IiwieDpvcmRlcl9pZCI6IjY3ODkwIn0=");
    }

    @Test
    public void testToCallbackParameter() {
        /*
        {
            "callbackUrl":"http://oss-demo.aliyuncs.com:23450",
            "callbackHost":"oss-cn-hangzhou.aliyuncs.com",
            "callbackBody":"{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}",
            "callbackBodyType":"application/x-www-form-urlencoded",
            "callbackSNI":false
        }
        */

        CallbackHelper result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .callbackHost("oss-cn-hangzhou.aliyuncs.com")
                .callbackBody("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}")
                .callbackBodyType(CallbackHelper.CallbackBodyType.JSON)
                .callbackSNI(false)
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(result.callbackBody()).isEqualTo("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}");
        assertThat(result.callbackBodyType()).isEqualTo("application/json");
        assertThat(result.callbackSNI()).isEqualTo(false);

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAiLCJjYWxsYmFja0hvc3QiOiJvc3MtY24taGFuZ3pob3UuYWxpeXVuY3MuY29tIiwiY2FsbGJhY2tCb2R5Ijoie1wiYnVja2V0XCI6JHtidWNrZXR9LFwib2JqZWN0XCI6JHtvYmplY3R9LFwibWltZVR5cGVcIjoke21pbWVUeXBlfSxcInNpemVcIjoke3NpemV9LFwibXlfdmFyMVwiOiR7eDpteV92YXIxfSxcIm15X3ZhcjJcIjoke3g6bXlfdmFyMn19IiwiY2FsbGJhY2tCb2R5VHlwZSI6ImFwcGxpY2F0aW9uL2pzb24iLCJjYWxsYmFja1NOSSI6ZmFsc2V9");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");

        result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isNull();
        assertThat(result.callbackBody()).isNull();
        assertThat(result.callbackBodyType()).isNull();
        assertThat(result.callbackSNI()).isNull();

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAifQ==");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");

        result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .callbackHost("oss-cn-hangzhou.aliyuncs.com")
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(result.callbackBody()).isNull();
        assertThat(result.callbackBodyType()).isNull();
        assertThat(result.callbackSNI()).isNull();

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAiLCJjYWxsYmFja0hvc3QiOiJvc3MtY24taGFuZ3pob3UuYWxpeXVuY3MuY29tIn0=");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");

        result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .callbackHost("oss-cn-hangzhou.aliyuncs.com")
                .callbackBody("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}")
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(result.callbackBody()).isEqualTo("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}");
        assertThat(result.callbackBodyType()).isNull();
        assertThat(result.callbackSNI()).isNull();

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAiLCJjYWxsYmFja0hvc3QiOiJvc3MtY24taGFuZ3pob3UuYWxpeXVuY3MuY29tIiwiY2FsbGJhY2tCb2R5Ijoie1wiYnVja2V0XCI6JHtidWNrZXR9LFwib2JqZWN0XCI6JHtvYmplY3R9LFwibWltZVR5cGVcIjoke21pbWVUeXBlfSxcInNpemVcIjoke3NpemV9LFwibXlfdmFyMVwiOiR7eDpteV92YXIxfSxcIm15X3ZhcjJcIjoke3g6bXlfdmFyMn19In0=");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");

        result = CallbackHelper.newBuilder()
                .callbackUrl("http://oss-demo.aliyuncs.com:23450")
                .callbackHost("oss-cn-hangzhou.aliyuncs.com")
                .callbackBody("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}")
                .callbackBodyType(CallbackHelper.CallbackBodyType.JSON)
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isEqualTo("http://oss-demo.aliyuncs.com:23450");
        assertThat(result.callbackHost()).isEqualTo("oss-cn-hangzhou.aliyuncs.com");
        assertThat(result.callbackBody()).isEqualTo("{\"bucket\":${bucket},\"object\":${object},\"mimeType\":${mimeType},\"size\":${size},\"my_var1\":${x:my_var1},\"my_var2\":${x:my_var2}}");
        assertThat(result.callbackBodyType()).isEqualTo("application/json");
        assertThat(result.callbackSNI()).isNull();

        assertThat(result.toCallbackParameter()).isEqualTo("eyJjYWxsYmFja1VybCI6Imh0dHA6Ly9vc3MtZGVtby5hbGl5dW5jcy5jb206MjM0NTAiLCJjYWxsYmFja0hvc3QiOiJvc3MtY24taGFuZ3pob3UuYWxpeXVuY3MuY29tIiwiY2FsbGJhY2tCb2R5Ijoie1wiYnVja2V0XCI6JHtidWNrZXR9LFwib2JqZWN0XCI6JHtvYmplY3R9LFwibWltZVR5cGVcIjoke21pbWVUeXBlfSxcInNpemVcIjoke3NpemV9LFwibXlfdmFyMVwiOiR7eDpteV92YXIxfSxcIm15X3ZhcjJcIjoke3g6bXlfdmFyMn19IiwiY2FsbGJhY2tCb2R5VHlwZSI6ImFwcGxpY2F0aW9uL2pzb24ifQ==");
        assertThat(result.toCallbackVarParameter()).isEqualTo("e30=");
    }

    @Test
    public void testToCallbackVarParameter() {
        /*

        {
          "x:uid": "12345",
          "x:order_id": "67890"
        }
         */

        Map<String, String> varMap = new HashMap<>();
        varMap.put("x:uid", "12345");
        varMap.put("x:order_id", "67890");

        CallbackHelper result = CallbackHelper.newBuilder()
                .callbackVar(varMap)
                .build();
        assertThat(result).isNotNull();
        assertThat(result.callbackUrl()).isNull();
        assertThat(result.callbackHost()).isNull();
        assertThat(result.callbackBody()).isNull();
        assertThat(result.callbackBodyType()).isNull();
        assertThat(result.callbackSNI()).isNull();
        assertThat(result.callbackVar()).isEqualTo(varMap);

        assertThat(result.toCallbackParameter()).isEqualTo("e30=");
        assertThat(result.toCallbackVarParameter()).isEqualTo("eyJ4OnVpZCI6IjEyMzQ1IiwieDpvcmRlcl9pZCI6IjY3ODkwIn0=");
    }

    @Test
    public void testToJsonBase64() {
        Map<String, Object> callbackMap = new LinkedHashMap<>();
        callbackMap.put("callbackHost", "your.callback.com");
        callbackMap.put("callbackUrl", "http://oss-demo.aliyuncs.com:23450");
        callbackMap.put("callbackBody", "bucket=${bucket}&object=${object}&uid=${x:uid}&order=${x:order_id}");
        callbackMap.put("callbackBodyType", "application/x-www-form-urlencoded");
        callbackMap.put("callbackSNI", false);

        String val = CallbackHelper.toJsonBase64(callbackMap);
        assertThat(val).isEqualTo("eyJjYWxsYmFja0hvc3QiOiJ5b3VyLmNhbGxiYWNrLmNvbSIsImNhbGxiYWNrVXJsIjoiaHR0cDovL29zcy1kZW1vLmFsaXl1bmNzLmNvbToyMzQ1MCIsImNhbGxiYWNrQm9keSI6ImJ1Y2tldD0ke2J1Y2tldH0mb2JqZWN0PSR7b2JqZWN0fSZ1aWQ9JHt4OnVpZH0mb3JkZXI9JHt4Om9yZGVyX2lkfSIsImNhbGxiYWNrQm9keVR5cGUiOiJhcHBsaWNhdGlvbi94LXd3dy1mb3JtLXVybGVuY29kZWQiLCJjYWxsYmFja1NOSSI6ZmFsc2V9");
    }
}
