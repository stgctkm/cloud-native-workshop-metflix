package com.metflix.service;

import com.metflix.domain.Movie;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Component
public class RecommendationService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${recommendation.api:http://localhost:3333}")
    URI recommendationApi;

    @HystrixCommand(fallbackMethod = "getRecommendationsFallback",
            commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"))
    public List<Movie> getRecommendations(String username) {
        return restTemplate.exchange(RequestEntity.get(UriComponentsBuilder.fromUri(recommendationApi)
                .pathSegment("api", "recommendations", username)
                .build().toUri()).build(), new ParameterizedTypeReference<List<Movie>>() {
        }).getBody();
    }

    private List<Movie> getRecommendationsFallback(String username) {
        return Collections.emptyList();
    }
}
