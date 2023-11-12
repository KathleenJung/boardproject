package com.example.board.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpConnector {
    private static final String HTTPS_SCHEME = "https";

    public static String sendHttpRequest(String method, String authority, String path, String requestData) throws IOException, URISyntaxException {
        int responseCode = -1;
        StringBuilder sb = new StringBuilder();

        try {
            URI uri = new URI(HTTPS_SCHEME, authority, path, null);
            String urlString = uri.toASCIIString();

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);

            if (("POST".equals(method) || "PUT".equals(method)) && requestData != null) {
                conn.setDoOutput(true);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            responseCode = conn.getResponseCode();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }

            conn.disconnect();

            return sb.toString();
        } catch (IOException e) {
            log.error("Failed to send HTTP request. Response code: " + responseCode, e);
            throw e;
        } catch (URISyntaxException e) {
            log.error("Invalid URI syntax: " + e.getMessage(), e);
            throw e;
        }
    }
}
