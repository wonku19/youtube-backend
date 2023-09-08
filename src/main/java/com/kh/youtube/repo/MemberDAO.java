package com.kh.youtube.repo;

import com.kh.youtube.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDAO extends JpaRepository<Member, String> {
}
