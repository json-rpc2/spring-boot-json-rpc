package com.tabasoft.jsonrpc.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RpcExceptionTest {
    @Test
    void whenCodeAndMessageArePresented_thenShouldCreateObject() {
        final var mockCode = 100;
        final var mockMessage = "abc";
        Assertions.assertDoesNotThrow(() -> new RpcException(mockMessage, mockCode));
    }

    @Test
    void whenCodeMessageAndThrowableArePresented_thenShouldCreateObject() {
        final var mockCode = 100;
        final var mockMessage = "abc";
        var mockThrowable = new Exception();
        Assertions.assertDoesNotThrow(() -> new RpcException(mockMessage, mockCode, mockThrowable));
    }

    @Test
    void whenCodeIsPresented_thenShouldReturnTheCode() {
        final var mockCode = 100;
        final var mockMessage = "abc";
        var obj = new RpcException(mockMessage, mockCode);
        Assertions.assertEquals(obj.getCode(), mockCode);
    }
}
