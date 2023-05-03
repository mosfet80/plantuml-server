package net.sourceforge.plantuml.servlet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sourceforge.plantuml.servlet.utils.WebappTestCase;


public class TestProxy extends WebappTestCase {

    /**
     * Verifies the proxified reception of the default Bob and Alice diagram
     */
    public void testDefaultProxy() throws IOException {
        final URL url = new URL(getServerUrl() + "/proxy?src=" + getTestDiagramUrl());
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Analyze response
        // Verifies HTTP status code and the Content-Type
        assertEquals("Bad HTTP status received", 200, conn.getResponseCode());
        assertEquals(
            "Response content type is not PNG",
            "image/png",
            conn.getContentType().toLowerCase()
        );
        // Get the image and verify its size (~2000 bytes)
        byte[] inMemoryImage = getContentAsBytes(conn);
        int diagramLen = inMemoryImage.length;
        assertTrue(diagramLen > 2000);
        assertTrue(diagramLen < 3000);
    }

    /**
     * Verifies the proxified reception of the default Bob and Alice diagram with defined format.
     */
    public void testProxyWithFormat() throws IOException {
        final URL url = new URL(getServerUrl() + "/proxy?fmt=svg&src=" + getTestDiagramUrl());
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Analyze response
        // Verifies HTTP status code and the Content-Type
        assertEquals("Bad HTTP status received", 200, conn.getResponseCode());
        assertEquals(
            "Response content type is not SVG",
            "image/svg+xml",
            conn.getContentType().toLowerCase()
        );
        // Get the content and verify its size
        String diagram = getContentText(conn);
        int diagramLen = diagram.length();
        assertTrue(diagramLen > 1500);
        assertTrue(diagramLen < 3000);
    }

    /**
     * Verifies the proxified reception of the default Bob and Alice diagram with defined format and format (idx=0).
     */
    public void testProxyWithFormatIdx0() throws IOException {
        final URL url = new URL(getServerUrl() + "/proxy?fmt=svg&idx=0&src=" + getTestDiagramUrl());
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Analyze response
        // Verifies HTTP status code and the Content-Type
        assertEquals("Bad HTTP status received", 200, conn.getResponseCode());
        assertEquals(
            "Response content type is not SVG",
            "image/svg+xml",
            conn.getContentType().toLowerCase()
        );
        // Get the content and verify its size
        String diagram = getContentText(conn);
        int diagramLen = diagram.length();
        assertTrue(diagramLen > 1500);
        assertTrue(diagramLen < 3000);
    }

    /**
     * Verifies the proxified reception of the default Bob and Alice diagram with defined format and format (idx=1).
     */
    public void testProxyWithFormatIdx1() throws IOException {
        final URL url = new URL(getServerUrl() + "/proxy?fmt=svg&idx=1&src=" + getTestDiagramUrl());
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Analyze response
        // Verifies HTTP status code and the Content-Type
        assertEquals("Bad HTTP status received", 200, conn.getResponseCode());
        assertEquals(
            "Response content type is not SVG",
            "image/svg+xml",
            conn.getContentType().toLowerCase()
        );
        // Get the content and verify its size
        String diagram = getContentText(conn);
        int diagramLen = diagram.length();
        assertTrue(diagramLen > 5000);
        assertTrue(diagramLen < 6000);
    }

    /**
     * Verifies that the HTTP header of a diagram incites the browser to cache it.
     */
    public void testInvalidUrl() throws IOException {
        final URL url = new URL(getServerUrl() + "/proxy?src=invalidURL");
        final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // Analyze response, it must be HTTP error 400
        assertEquals(
            "Bad HTTP status received",
            400,
            conn.getResponseCode()
        );
        // Check error message
        assertTrue(
            "Response is not malformed URL",
            getContentText(conn.getErrorStream()).contains("URL malformed.")
        );
    }

}
