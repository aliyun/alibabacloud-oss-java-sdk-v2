package com.aliyun.sdk.service.oss2.exceptions;

public class BaseErrorTest {
//
//    /**
//     * Invokes the generateMessage method using reflection
//     *
//     * @param fmt Format string
//     * @param kwargs Parameter map
//     * @return Generated message
//     * @throws Exception If the invocation fails
//     */
//    private String invokeGenerateMessage(String fmt, Map<String, Object> kwargs) throws Exception {
//        Method method = BaseError.class.getDeclaredMethod("generateMessage", String.class, Map.class);
//        method.setAccessible(true);
//        return (String) method.invoke(null, fmt, kwargs);
//    }
//
//    @Test
//    public void testNormalReplacement() throws Exception {
//        String fmt = "Hello {name}, you have {count} messages.";
//        Map<String, Object> kwargs = new HashMap<>();
//        kwargs.put("name", "Alice");
//        kwargs.put("count", 5);
//
//        String result = invokeGenerateMessage(fmt, kwargs);
//        assertEquals("Hello Alice, you have 5 messages.", result);
//    }
//
//    @Test
//    public void testMissingKeyInMap() throws Exception {
//        String fmt = "User {username} not found.";
//        Map<String, Object> kwargs = new HashMap<>();
//
//        String result = invokeGenerateMessage(fmt, kwargs);
//        assertEquals("User {username} not found.", result);
//    }
//
//    @Test
//    public void testMultipleSameKeys() throws Exception {
//        String fmt = "The key is {key}, again the key is {key}.";
//        Map<String, Object> kwargs = new HashMap<>();
//        kwargs.put("key", "value");
//
//        String result = invokeGenerateMessage(fmt, kwargs);
//        assertEquals("The key is value, again the key is value.", result);
//    }
//
//    @Test
//    public void testNullFmtReturnNull() throws Exception {
//        Map<String, Object> kwargs = new HashMap<>();
//        String result = invokeGenerateMessage(null, kwargs);
//        assertNull(result);
//    }
//
//    @Test
//    public void testPartialReplacement() throws Exception {
//        String fmt = "A={a}, B={b}, C={c}";
//        Map<String, Object> kwargs = new HashMap<>();
//        kwargs.put("a", "X");
//        kwargs.put("b", "Y");
//
//        String result = invokeGenerateMessage(fmt, kwargs);
//        assertEquals("A=X, B=Y, C={c}", result);
//    }
//
//    @Test
//    public void testEmptyMap() throws Exception {
//        String fmt = "This is a test message.";
//        Map<String, Object> kwargs = new HashMap<>();
//
//        String result = invokeGenerateMessage(fmt, kwargs);
//        assertEquals("This is a test message.", result);
//    }
}