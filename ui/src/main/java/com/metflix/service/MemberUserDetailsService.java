package com.metflix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class MemberUserDetailsService implements UserDetailsService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${member.api:http://localhost:4444}")
    URI memberApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String member = restTemplate.exchange(
                RequestEntity.get(UriComponentsBuilder.fromUri(memberApi)
                        .pathSegment("api", "members", username)
                        .build().toUri()).build(),
                String.class).getBody();
        if (member == null) {
            throw new UsernameNotFoundException(username + " is not found");
        }
        return new User(username, "metflix" /* shared secret!! */, AuthorityUtils.createAuthorityList("MEMBER"));
    }
}
