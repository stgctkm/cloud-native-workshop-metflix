package com.metflix.controller;


import com.metflix.domain.Movie;
import com.metflix.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@Controller
@RefreshScope
class HomeController {

    @Autowired
    RecommendationService recommendationService;

    @Value("${recommendation.api:http://localhost:3333}")
    URI recommendationApi;

    @Value("${message:Welcome to Metflix!}")
    String message;

    @RequestMapping("/")
    String home(Principal principal, Model model) {
        List<Movie> recommendations =  recommendationService.getRecommendations(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("message", message);
        return "index";
    }
}
