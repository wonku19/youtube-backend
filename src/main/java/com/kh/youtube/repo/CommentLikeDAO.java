package com.kh.youtube.repo;

import com.kh.youtube.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeDAO extends JpaRepository<CommentLike, Integer> {

}
