package com.metflix.controller;


import com.metflix.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RefreshScope
class HomeController {
    @Autowired
    RestTemplate restTemplate;
    @Value("${recommendation.api:http://localhost:3333}")
    URI recommendationApi;

    @Value("${message:Welcome to Metflix!}")
    String message;

    @RequestMapping("/")
    String home(Principal principal, Model model) {
        List<Movie> recommendations = restTemplate.exchange(RequestEntity.get(UriComponentsBuilder.fromUri(recommendationApi)
                .pathSegment("api", "recommendations", principal.getName())
                .build().toUri()).build(), new ParameterizedTypeReference<List<Movie>>() {
        }).getBody();
        model.addAttribute("username", principal.getName());
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("message", message);
        return "index";
    }
}
