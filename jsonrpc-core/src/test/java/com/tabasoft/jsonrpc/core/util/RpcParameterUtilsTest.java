package com.tabasoft.jsonrpc.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabasoft.jsonrpc.core.exception.RpcException;
import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RpcParameterUtilsTest {

    @Test
    void whenParamsIsArray_thenPlaceParametersPositionally() throws IOException {
        var mapper = new ObjectMapper();
        var node = mapper.readTree("[\"a\",3]");
        var rpcMethodDefinition = JsonRpcMethodDefinition
                .builder()
                .javaMethod(TestRpcResource.class.getMethods()[0])
                .parameterNames(new String[]{"a", "b"})
                .build();
        var result = RpcParameterUtils.getRpcParameters(node, rpcMethodDefinition);
        Assertions.assertArrayEquals(result, new Object[]{"a", 3L});
    }

    @Test
    void whenParamsIsArrayAndMoreThanDefined_thenThrowRpcException() throws IOException {
        var mapper = new ObjectMapper();
        var node = mapper.readTree("[\"a\",3, \"c\"]");
        var rpcMethodDefinition = JsonRpcMethodDefinition
                .builder()
                .parameterNames(new String[]{"a", "b"})
                .javaMethod(TestRpcResource.class.getMethods()[0])
                .build();
        Assertions.assertThrows(RpcException.class, () -> RpcParameterUtils.getRpcParameters(node, rpcMethodDefinition));
    }

    @Test
    void whenParamsIsArrayAndTypeMismatches_thenThrowRpcException() throws IOException {
        var mapper = new ObjectMapper();
        var node = mapper.readTree("[\"a\",\"b\"]");
        var rpcMethodDefinition = JsonRpcMethodDefinition
                .builder()
                .parameterNames(new String[]{"a", "b"})
                .javaMethod(TestRpcResource.class.getMethods()[0])
                .build();
        Assertions.assertThrows(RpcException.class, () -> RpcParameterUtils.getRpcParameters(node, rpcMethodDefinition));
    }


    @Test
    void whenParamsIsObject_thenPlaceParametersByName() throws IOException {
        var mapper = new ObjectMapper();
        var node = mapper.readTree("{\"a\":\"test\",\"b\":2}");
        var rpcMethodDefinition = JsonRpcMethodDefinition
                .builder()
                .parameterNames(new String[]{"a", "b"})
                .javaMethod(TestRpcResource.class.getMethods()[0])
                .build();
        var result = RpcParameterUtils.getRpcParameters(node, rpcMethodDefinition);
        Assertions.assertArrayEquals(result, new Object[]{"test", 2L});
    }

    private class TestRpcResource {
        public void testRpcMethod(String a, Long b) {

        }
    }
}
