package com.kh.youtube.controller;

import com.kh.youtube.domain.Video;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/*")
public class VideoController {

    @Autowired
    private VideoService video;

    @Autowired
    private VideoCommentService comment;

    // 영상 전체 조회 : GET - http://localhost:8080/api/video
    @GetMapping("/video")
    public ResponseEntity<List<Video>> videoList() {
        return ResponseEntity.status(HttpStatus.OK).body(video.showAll());
    }

    // 영상 추가 : POST - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(@RequestBody Video vo) {
        return ResponseEntity.status(HttpStatus.OK).body(video.create(vo));
    }

    // 영상 수정 : PUT - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> updateVideo(@RequestBody Video vo) {
        return ResponseEntity.status(HttpStatus.OK).body(video.update(vo));
    }

    // 영상 삭제 : DELETE - http://localhost:8080/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity<Video> deleteVideo(@RequestBody int id) {
        return ResponseEntity.status(HttpStatus.OK).body(video.delete(id));
    }

    // 영상 1개 조회 : GET - http://localhost:8080/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity<Video> showVideo(@RequestBody int id) {
        return ResponseEntity.status(HttpStatus.OK).body(video.show(id));
    }

    // 영상 1개에 따른 댓글 전체 조회 : GET - http://localhost:8080/api/video/comment
    @GetMapping("/video/comment")
    public ResponseEntity<List<VideoComment>> videoCommentList(@RequestBody int id) {
        return ResponseEntity.status(HttpStatus.OK).body(comment.findByVideoCode(id));
    }

    // 좋아요 추가 : POST - http://localhost:8080/api/video/like
    // 좋아요 취소 : DELETE - http://localhost:8080/api/video/like/1
    // 댓글 추가 : POST - http://localhost:8080/api/video/comment
    // 댓글 수정 : PUT - http://localhost:8080/api/video/comment
    // 댓글 삭제 : DELETE - http://localhost:8080/api/video/comment/1
    // 댓글 좋아요 추가 : POST - http://localhost:8080/api/video/comment/like
    // 댓글 좋아요 취소 : DELETE - http://localhost:8080/api/video/comment/like/1

}
