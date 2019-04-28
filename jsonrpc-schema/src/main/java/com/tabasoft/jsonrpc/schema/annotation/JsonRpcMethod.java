package com.tabasoft.jsonrpc.schema.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonRpcMethod {
    String name() default "";
    String description() default "";
}
