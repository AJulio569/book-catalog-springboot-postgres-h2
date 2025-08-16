package com.book.client;

import com.book.dto.response.PaginatedBookResponse;
import com.book.utils.ConvertJacksonData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class GutendexClient {
    @Value("${gutendex.api.url}")
    private String gutendexUrl;

    private static final String SEARCH = "?search=";

    private final ConvertJacksonData jacksonData;

    public PaginatedBookResponse searchBooks(String title) {
        return getContent(title, PaginatedBookResponse.class);
    }

    private <T> T getContent(String title, Class<T> clazz) {
        try {
            URI uri = buildUri(title);
            HttpRequest request = buildHttpRequest(uri);
            HttpResponse<String> response = sendRequest(request);

            return parseResponse(response.body(), title, clazz);

        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error consuming the API", e);
        }
    }

    private URI buildUri(String title) {
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        return URI.create(gutendexUrl+ SEARCH + encodedTitle);
    }

    private HttpRequest buildHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private <T> T parseResponse(String responseBody, String title, Class<T> clazz) {
        T result = jacksonData.fromJson(responseBody, clazz);

        if (result instanceof PaginatedBookResponse bookResponse) {
            if (bookResponse.getResults() == null || bookResponse.getResults().isEmpty()) {
                //throw new CustomNotFoundException("Book not found: " + title);
            }
        }
        return result;
    }
}
