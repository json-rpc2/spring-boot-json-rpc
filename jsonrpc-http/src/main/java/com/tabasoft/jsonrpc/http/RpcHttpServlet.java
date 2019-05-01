package com.tabasoft.jsonrpc.http;

import com.tabasoft.jsonrpc.core.service.JsonRpcHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RpcHttpServlet extends HttpServlet {
    private static final long serialVersionUID = -5708801472716410989L;
    private static final Logger logger = LoggerFactory.getLogger(RpcHttpServlet.class);
    private final JsonRpcHandler rpcHandler;

    public RpcHttpServlet(JsonRpcHandler rpcHandler) {
        this.rpcHandler = rpcHandler;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            var result = rpcHandler.handleRequest(request.getReader());
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("test");
    }
}
