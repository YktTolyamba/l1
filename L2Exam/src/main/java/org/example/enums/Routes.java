package org.example.enums;

public enum Routes {

    WEB_PATH("/web"),
    GET_TOKEN("/api/token/get"),
    GET_TESTS_JSON("/api/test/get/json"),
    PUT_TEST("/api/test/put"),
    PUT_TEST_LOG("/api/test/put/log"),
    PUT_TEST_ATTACHMENT("/api/test/put/attachment");

    private final String path;

    Routes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
