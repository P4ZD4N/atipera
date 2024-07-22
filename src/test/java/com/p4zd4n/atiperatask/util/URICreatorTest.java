package com.p4zd4n.atiperatask.util;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class URICreatorTest {

    @Test
    void createURI_validApiUrlAndPath_cleanedAndCombinedURI() {

        URI uri = URICreator.createURI("https://api.github.com/", "/users/username/repos?type=all");

        assertNotNull(uri);
        assertEquals("https://api.github.com/users/username/repos?type=all", uri.toString());
    }

    @Test
    void createURI_apiUrlWithMultipleTrailingSlashesAndPathWithMultipleTrailingSlashes_cleanedAndCombinedURI() {

        URI uri = URICreator.createURI("https://api.github.com/////", "//users/username/////repos?type=all");

        assertNotNull(uri);
        assertEquals("https://api.github.com/users/username/repos?type=all", uri.toString());
    }

    @Test
    void createURI_emptyPath_returnsApiUrlWithoutPath() {

        URI uri = URICreator.createURI("https://api.github.com/", "");

        assertNotNull(uri);
        assertEquals("https://api.github.com", uri.toString());
    }
}
