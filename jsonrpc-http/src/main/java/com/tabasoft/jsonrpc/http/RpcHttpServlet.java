package com.tabasoft.jsonrpc.http;

import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RpcHttpServlet extends HttpServlet {
    private static final long serialVersionUID = -5708801472716410989L;

    private final JsonRpcHandler rpcHandler;

    public RpcHttpServlet(JsonRpcHandler rpcHandler) {
        this.rpcHandler = rpcHandler;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
