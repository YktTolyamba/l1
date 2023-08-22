package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class JsonUtils {

    public static <T> T getJavaObjectFromJsonResponse(HttpResponse<JsonNode> jsonResponse, Class<T> responseClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse.getBody().toString(), responseClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
