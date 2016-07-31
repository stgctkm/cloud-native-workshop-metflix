package com.metflix.controller;

import org.springframework.web.bind.annotation.*;
import com.metflix.domain.Member;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/members")
class MembershipController {
    final Map<String, Member> memberStore = new ConcurrentHashMap<String, Member>() {
        {
            put("making", new Member("making", 10));
            put("tichimura", new Member("tichimura", 30));
        }
    };

    @RequestMapping(method = RequestMethod.POST)
    public Member register(@RequestBody Member member) {
        memberStore.put(member.user, member);
        return member;
    }

    @RequestMapping("/{user}")
    Member get(@PathVariable String user) {
        return memberStore.get(user);
    }
}
