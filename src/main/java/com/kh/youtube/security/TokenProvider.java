package com.kh.youtube.security;

import com.kh.youtube.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    private static final String SECRET_KEY="FlRpX30MqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M10icegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(Member member) {
        // 토큰 생성 -> 기한 지정 가능 (1일까지로 지정)
        Date expriyDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header 에 들어갈 내용
                .setSubject(member.getId()) // <-- 여기부터는 payload에 들어갈 내용
                .setIssuer("youtube app")
                .setIssuedAt(new Date())
                .setExpiration(expriyDate)
                .compact();
    }

    public String validateAndGetUserId(String token) { // 토큰을 디코딩, 파싱 및 위조여부 확인
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // id를 반환
    }

}
