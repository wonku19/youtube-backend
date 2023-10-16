package com.kh.youtube.controller;

import com.kh.youtube.domain.Channel;
import com.kh.youtube.domain.Member;
import com.kh.youtube.domain.MemberDTO;
import com.kh.youtube.security.TokenProvider;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class MemberController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberService service;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity register(@RequestBody MemberDTO dto) {
        // 비밀번호 -> 암호화 처리 + 저장할 유저 만들기
        Member member = Member.builder()
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .build();

        // 서비스를 이용해 리포지터리에 유저 저장
        Member registerMember = service.create(member);
        MemberDTO responseDTO = dto.builder()
                .id(registerMember.getId())
                .name(registerMember.getName())
                .build();
        return ResponseEntity.ok().body(responseDTO);
    }

    // 로그인 -> token
    @PostMapping("/user/signin")
    public ResponseEntity authenticate(@RequestBody MemberDTO dto) {
        Member member = service.getByCredentials(dto.getId(), dto.getPassword(), passwordEncoder);
        if(member!=null) { // -> 토큰 생성
            String token = tokenProvider.create(member);
            MemberDTO responseDTO = MemberDTO.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}