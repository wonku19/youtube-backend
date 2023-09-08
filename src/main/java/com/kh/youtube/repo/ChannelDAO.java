package com.kh.youtube.repo;

import com.kh.youtube.domain.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelDAO extends JpaRepository<Channel, Integer> {

    // 특정 멤버의 모든 채널 조회
    // SELECT * FROM channel WHERE id=?
    @Query(value="SELECT * FROM channel WHERE id= :id", nativeQuery = true)
    List<Channel> findByMemberId(String id);

}
