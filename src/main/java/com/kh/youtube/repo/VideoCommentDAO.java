package com.kh.youtube.repo;

import com.kh.youtube.domain.VideoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoCommentDAO extends JpaRepository<VideoComment, Integer> {

    // 영상 1개에 따른 댓글 전체 조회
    @Query(value="SELECT * FROM video_comment WHERE video_code = :code", nativeQuery = true)
    List<VideoComment> findByVideoCode(int code);

}
