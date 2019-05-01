package com.tabasoft.jsonrpc.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.BufferedReader;

public interface JsonRpcHandler {
    String handleRequest(BufferedReader requestReader);

    String handleRequest(JsonNode node) throws JsonProcessingException;
}
