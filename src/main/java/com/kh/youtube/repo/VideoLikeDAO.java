package com.kh.youtube.repo;

import com.kh.youtube.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoLikeDAO extends JpaRepository<VideoLike, Integer> {
}
