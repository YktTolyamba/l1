package org.example.utils;

import org.apache.http.client.utils.URIBuilder;
import org.example.constants.JsonFiles;
import org.example.enums.Routes;

import java.net.*;


public class UrlAssembler {
    private static final String protocol = JsonFiles.CONFIG.getValue("/protocol").toString();
    private static final String domain = JsonFiles.CONFIG.getValue("/domain").toString();
    private static final int port = Integer.parseInt(JsonFiles.CONFIG.getValue("/port").toString());

    public static String assembleWebUrlWithBasicAuthentication(String login, String password) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(protocol);
        builder.setUserInfo(login, password);
        builder.setHost(domain);
        builder.setPort(port);
        builder.setPath(Routes.WEB_PATH.getPath());
        URL url = null;
        try {
            url = builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return url.toString();
    }

    private static String assembleUrl(String path) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(protocol);
        builder.setHost(domain);
        builder.setPort(port);
        builder.setPath(path);
        URL url = null;
        try {
            url = builder.build().toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return url.toString();
    }

    public static String assembleApiGetTokenUrl() {
        return assembleUrl(Routes.GET_TOKEN.getPath());
    }

    public static String assembleApiGetTestsJsonUrl() {
        return assembleUrl(Routes.GET_TESTS_JSON.getPath());
    }

    public static String assembleApiPutTestURL() {
        return assembleUrl(Routes.PUT_TEST.getPath());
    }

    public static String assembleApiPutTestLogURL() {
        return assembleUrl(Routes.PUT_TEST_LOG.getPath());
    }

    public static String assembleApiPutScreenshotURL() {
        return assembleUrl(Routes.PUT_TEST_ATTACHMENT.getPath());
    }

}
