package com.tabasoft.jsonrpc.core.exception;

/**
 * Default exception codes defined in https://www.jsonrpc.org/specification
 */
public class DefaultExceptionCodes {
    public static int PARSE_ERROR = -32700;
    public static int INVALID_REQUEST = -32600;
    public static int METHOD_NOT_FOUND = -32601;
    public static int INVALID_PARAMS = -32602;
    public static int INTERNAL_ERROR = -32603;
}
