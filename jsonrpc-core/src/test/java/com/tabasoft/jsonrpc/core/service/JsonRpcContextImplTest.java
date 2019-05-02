package com.tabasoft.jsonrpc.core.service;

import com.tabasoft.jsonrpc.core.exception.RpcInitializationException;
import com.tabasoft.jsonrpc.core.model.JsonRpcMethodDefinition;
import com.tabasoft.jsonrpc.core.service.impl.JsonRpcContextImpl;
import com.tabasoft.jsonrpc.schema.annotation.JsonRpcMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class JsonRpcContextImplTest {
    public static final String METHOD_1_NAME = "method1";
    public static final String TEST_METHOD_NAME = "testMethod";
    private JsonRpcContextImpl service;

    @BeforeEach
    void createService() {
        service = new JsonRpcContextImpl();
    }

    @Test
    void whenBeanWithTwoAnnotatedMethodsPassed_thenShouldRegisterTheseMethods() throws NoSuchFieldException, IllegalAccessException {
        var bean = new BeanWithTwoCorrectlyAnnotatedMethods();
        service.registerService(bean);
        var methods = getBeanMethods();
        Assertions.assertEquals(methods.size(), 2);
        Assertions.assertTrue(methods.containsKey(METHOD_1_NAME));
        Assertions.assertTrue(methods.containsKey(TEST_METHOD_NAME));
        var method1 = methods.get(METHOD_1_NAME);
        var method2 = methods.get(TEST_METHOD_NAME);
        Assertions.assertEquals(method1.getName(), METHOD_1_NAME);
        Assertions.assertEquals(method1.getService(), bean);
        Assertions.assertEquals(method2.getName(), TEST_METHOD_NAME);
        Assertions.assertArrayEquals(method2.getParameterNames(), new String[]{"a", "b"});
    }

    @Test
    void whenBeanContainsRepeatableMethodNames_thenShouldThrow() {
        var bean = new BeanWithRepeatingMethodNames();
        Assertions.assertThrows(RpcInitializationException.class, () -> service.registerService(bean));
    }

    @SuppressWarnings("unchecked")
    private Map<String, JsonRpcMethodDefinition> getBeanMethods() throws NoSuchFieldException, IllegalAccessException {
        var methodsField = service.getClass().getDeclaredField("methods");
        methodsField.setAccessible(true);
        return (Map<String, JsonRpcMethodDefinition>) methodsField.get(service);
    }

    private class BeanWithTwoCorrectlyAnnotatedMethods {
        @JsonRpcMethod
        public String method1() {
            return "1";
        }

        @JsonRpcMethod(name = "testMethod")
        public String method2(String a, Long b) {
            return "1";
        }
    }

    private class BeanWithRepeatingMethodNames {
        @JsonRpcMethod
        public String method1() {
            return "1";
        }

        @JsonRpcMethod(name = "method1")
        public String method2(String a, Long b) {
            return "1";
        }
    }
}
