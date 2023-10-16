package com.kh.youtube.service;

import com.kh.youtube.domain.Member;
import com.kh.youtube.repo.MemberDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberDAO dao;

    public List<Member> showAll() {
        return dao.findAll(); // SELECT * FROM MEMBER
    }

    public Member show(String id) {
        return dao.findById(id).orElse(null); // SELECT * FROM MEMBER WHERE id=?
    }

    // INSERT INTO MEMBER(ID, PASSWORD, NAME, AUTHORITY)
    // VALUES(?, ?, ?, 'ROLE_USER')
    public Member create(Member member) {
        log.info("member : " + member);
        return dao.save(member);
    }

    // UPDATE MEMBER SET ID=?, PASSWORD=?, NAME=?, AUTHORITY=?
    // WHERE ID=?
    public Member update(Member member) {
        Member target = dao.findById(member.getId()).orElse(null);
        if(target!=null) {
            return dao.save(member);
        }
        return null;
    }

    // DELETE FROM MEMBER WHERE ID=?
    public Member delete(String id) {
        Member target = dao.findById(id).orElse(null);
        dao.delete(target);
        return target;
    }

    public Member getByCredentials(String id, String password, PasswordEncoder encoder) {
        Member member = dao.findById(id).orElse(null);
        if(member!=null && encoder.matches(password, member.getPassword())) {
            return member;
        }
        return null;
    }

}