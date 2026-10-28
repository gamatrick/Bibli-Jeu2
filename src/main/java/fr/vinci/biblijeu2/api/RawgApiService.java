package fr.vinci.biblijeu2.api;

import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service //
public class RawgApiService {
    private static final String API_KEY = "188aaa2d43d14d8dac6ac85a8da1a2e8";
    private static final String BASE_URL = "https://api.rawg.io/api/games";
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String searchGamesByName(String query) throws Exception {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = BASE_URL + "?key=" + API_KEY + "&search=" + encodedQuery;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}