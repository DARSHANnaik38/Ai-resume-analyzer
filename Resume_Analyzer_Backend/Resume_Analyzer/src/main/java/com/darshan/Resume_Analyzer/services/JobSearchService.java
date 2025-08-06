package com.darshan.Resume_Analyzer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JobSearchService {

    private final WebClient webClient;

    private final String baseUrl = "https://api.adzuna.com/v1/api/jobs";

    public JobSearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Value("${adzuna.app.id}")
    private String appId;

    @Value("${adzuna.app.key}")
    private String appKey;

    public Mono<String> searchJobs(String query) {
        String countryCode = "in";
        // --- ADD THIS LINE TO BE MORE SPECIFIC ---
        String location = "Bangalore";

        return webClient.get()
                .uri(baseUrl + "/" + countryCode + "/search/1", uriBuilder -> uriBuilder
                        .queryParam("app_id", appId)
                        .queryParam("app_key", appKey)
                        .queryParam("results_per_page", 20)
                        .queryParam("what", query)
                        // --- ADD THIS LINE TO INCLUDE THE LOCATION ---
                        .queryParam("where", location)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}