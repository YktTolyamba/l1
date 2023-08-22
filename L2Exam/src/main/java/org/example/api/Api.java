package org.example.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.example.models.TestModel;

import java.util.HashMap;
import java.util.Map;

public class Api {

    private static HttpResponse<String> sendPostRequestStringResponse(String url, Map<String, Object> parameters) {
        try {
            return Unirest.post(url)
                    .header("Content-type", "application/json")
                    .queryString(parameters)
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse<JsonNode> sendPostRequestJsonResponse(String url, Map<String, Object> parameters) {
        try {
            return Unirest.post(url)
                    .header("Content-type", "application/json")
                    .queryString(parameters)
                    .asJson();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> getToken(String url, String variant) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("variant", variant);
        return Api.sendPostRequestStringResponse(url, parameters);
    }

    public static void putTestLog(String url, int testId, String log) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("testId", testId);
        parameters.put("content", log);
        Api.sendPostRequestStringResponse(url, parameters);
    }

    public static HttpResponse<JsonNode> getTestsByProjectId(String url, int projectId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("projectId", String.valueOf(projectId));
        return Api.sendPostRequestJsonResponse(url, parameters);
    }

    public static HttpResponse<String> putTest(String url, TestModel test) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("SID", test.getSid());
        parameters.put("projectName", test.getProjectName());
        parameters.put("testName", test.getName());
        parameters.put("methodName", test.getMethod());
        parameters.put("env", test.getEnv());
        return Api.sendPostRequestStringResponse(url, parameters);
    }

    public static void putTestScreenshot(String url, int testId, String screenshot) {
        try {
            Unirest.post(url)
                    .queryString("testId", testId)
                    .queryString("contentType", "image/png")
                    .field("content", screenshot)
                    .asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }


}
