package com.kh.youtube.controller;

import com.kh.youtube.domain.Category;
import com.kh.youtube.domain.Video;
import com.kh.youtube.domain.VideoComment;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCommentService comment;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 영상 전체 조회 : GET - http://localhost:8080/api/video
    @GetMapping("/video")
    public ResponseEntity<List<Video>> videoList(@RequestParam(name="page", defaultValue = "1") int page) {

        // 정렬
        Sort sort = Sort.by("videoCode").descending();
        // 한 페이지에 10개만 들어오도록
        Pageable pageable = PageRequest.of(-1, 10, sort);
        Page<Video> result = videoService.showAll(pageable);

        log.info("Total Pages : " + result.getTotalPages()); // 총 몇 페이지
        log.info("Total Count : " + result.getTotalElements()); // 전체 개수
        log.info("Page Number : " + result.getNumber()); // 현재 페이지 번호
        log.info("Page Size : " + result.getSize()); // 페이지당 데이터 개수
        log.info("Next Page : " + result.hasNext()); // 다음 페이지가 있는지 존재여부
        log.info("First Page : " + result.isFirst()); // 시작 페이지 여부

//        return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    // 영상 추가 : POST - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, @RequestParam(name="desc", required = false) String desc, String categoryCode) {

        String originalVideoName = video.getOriginalFilename();
        String realVideoName = originalVideoName.substring(originalVideoName.lastIndexOf("\\") + 1);
        String uuidVideo = UUID.randomUUID().toString();
        String saveVideoPath = uploadPath + File.separator + uuidVideo + "_" + realVideoName;
        Path pathVideo = Paths.get(saveVideoPath);

        String originalImage = image.getOriginalFilename();
        String realImage = originalImage.substring(originalImage.lastIndexOf("\\")+1);
        String uuid = UUID.randomUUID().toString();
        String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
        Path pathImage = Paths.get(saveImage);

        try {
            video.transferTo(pathVideo);
            image.transferTo(pathImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));

        Video vo = new Video();
        vo.setVideoTitle(title);
        vo.setVideoDesc(desc);
        vo.setVideoUrl(saveVideoPath);
        vo.setVideoPhoto(saveImage);
        vo.setCategory(category);

        return ResponseEntity.status(HttpStatus.OK).body(videoService.create(vo));
    }

    // 영상 수정 : PUT - http://localhost:8080/api/video
    @PutMapping("/video")
    public ResponseEntity<Video> updateVideo(@RequestBody Video vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.update(vo));
    }

    // 영상 삭제 : DELETE - http://localhost:8080/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity<Video> deleteVideo(@RequestBody int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.delete(id));
    }

    // 영상 1개 조회 : GET - http://localhost:8080/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity<Video> showVideo(@RequestBody int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.show(id));
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
