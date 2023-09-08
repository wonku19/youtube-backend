package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ChannelService channelService;

    @GetMapping("/user")
    public ResponseEntity<List<Member>> showAll(){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.showAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Member> show(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.show(id));
    }

    @PostMapping("/user")
    public ResponseEntity<Member> create(@RequestBody Member member){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.create(member));
    }

    @PutMapping("/user")
    public ResponseEntity<Member> update(@RequestBody Member member){
        Member result = memberService.update(member);
        if(result!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Member> delete(@PathVariable String id){
        log.info("삭제~");
        return ResponseEntity.status(HttpStatus.OK).body(memberService.delete(id));
    }

    // SELECT * FROM channel WHERE id=?
    // http://localhost:8080/user/channel?id=user1
    @GetMapping("/user/channel")
    public ResponseEntity<List <Channel>> showMember(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.OK).body(channelService.showMember(id));
    }
}