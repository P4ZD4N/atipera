package com.p4zd4n.atiperatask.util;

import java.net.URI;

public class URICreator {

    public static URI createURI(String apiUrl, String path) {

        String cleanedApiUrl = getCleanedApiUrl(apiUrl);
        String cleanedUriPath = getCleanedUriPath(path);

        return URI.create(cleanedApiUrl + cleanedUriPath);
    }

    private static String getCleanedApiUrl(String apiUrl) {
        return apiUrl.replaceAll("/+$", "");
    }

    private static String getCleanedUriPath(String path) {
        return path.replaceAll("/+", "/");
    }
}
