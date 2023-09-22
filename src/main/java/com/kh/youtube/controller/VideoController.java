package com.kh.youtube.controller;

import com.kh.youtube.domain.*;
import com.kh.youtube.service.CommentLikeService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoLikeService;
import com.kh.youtube.service.VideoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
@Log4j2
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class VideoController {
    @Value("${youtube.upload.path}")// application.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoCommentService videoComment;

    @Autowired
    private VideoLikeService videoLike;

    @Autowired
    private CommentLikeService commentLike;

    // 영상 전체 조회 : GET - http://localhost:8080/api/video
    @GetMapping("/video")
    public ResponseEntity<List<Video>> videoList(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="category", required = false) Integer category) {
        // 정렬
        Sort sort =  Sort.by("videoCode").descending();
        // 한 페이지에 10개
        Pageable pageable = PageRequest.of(page-1,20,sort);

        // 동적 쿼리를 위한 QueryDSL을 사용한 코드들 추가

        // 1. Q도메인 클래스를 가져와야 한다.
        QVideo qVideo = QVideo.video;

        // 2. BooleanBuilder where문에 들어가는 조건들을 넣어주는 컨테이너라고
        BooleanBuilder builder = new BooleanBuilder();

        if(category!=null){
            // 3. 원하는 조건은 필드값과 같이 결합해서 생성한다.
            BooleanExpression expression = qVideo.category.categoryCode.eq(category);
            // 4. 만들어진 조건은 where 문에 and나 or같은 키워드와 결합한다.
            builder.and(expression);

        }
        Page<Video> result = videoService.showAll(pageable, builder);

        log.info("Total Pages : " + result.getTotalPages()); // 총 몇페이지
        log.info("Total Count : " + result.getTotalElements()); // 전체 개수
        log.info("Page Number : " + result.getNumber()); // 현재 페이지 번호
        log.info("Page Size : " + result.getSize()); // 페이지 당 데이터 개수
        log.info("Next Page : " + result.hasNext()); // 다음페 이지가 있는지 존재 여부
        log.info("First Page : "+ result.isFirst()); // 시작 페이지 여부

//      return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    // 영상 추가 : POST - http://localhost:8080/api/video
    @PostMapping("/video")
    // 필수값이 아닐때@RequestParam(name="desc", required = false) String desc 요렇게 지정
    public ResponseEntity<Video> createVideo(MultipartFile video, MultipartFile image, String title, @RequestParam(name="desc", required = false) String desc, String categoryCode) {
        log.info("video : " + video);
        log.info("image : " + image);
        log.info("title : " + title);
        log.info("desc : " + desc);
        log.info("categoryCode : " + categoryCode);

        // video_title, video_desc, video_url, video_photo, category_code
        // 업로드 처리
        // 비디오의 실제 파일 이름
        String originalVideo = video.getOriginalFilename();
        String realVideo = originalVideo.substring(originalVideo.lastIndexOf("\\")+1);
        log.info("real : "+realVideo);

        // UUID
        String uuid = UUID.randomUUID().toString();

        // 실제로 저장할 파일명(위치 포함)
        String saveVideo = uploadPath + File.separator + uuid + "_" + realVideo;
        Path pathVideo = Paths.get(saveVideo);

        // 이미지
        String originalImage = image.getOriginalFilename();
        String realImage = originalImage.substring(originalImage.lastIndexOf("\\")+1);

        String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
        Path pathImage = Paths.get(saveImage);
        try {
            video.transferTo(pathVideo);
            image.transferTo(pathImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Video vo = new Video();
        Category category = new Category();
        category.setCategoryCode(Integer.parseInt(categoryCode));
        vo.setVideoTitle(title);
        vo.setVideoPhoto(uuid + "_" + realImage);
        vo.setVideoUrl(uuid + "_" + realVideo);
        vo.setVideoDesc(desc);
        vo.setCategory(category);

        Channel channel = new Channel();
        channel.setChannelCode(1);
        vo.setChannel(channel);

        Member member = new Member();
        member.setId("user1");
        vo.setMember(member);
//      return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status (HttpStatus.OK).body(videoService.create(vo));
    }
    // 영상 수정 : PUT - http://localhost:8080/api/video
    @PutMapping("/video")
    public ResponseEntity<Video> updateVideo(@RequestBody Video vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.update(vo));
    }

    // 영상 삭제 : DELETE - http://localhost:8080/api/video/1
    @DeleteMapping("/video/{id}")
    public ResponseEntity<Video> deleteVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.delete(id));
    }

    // 영상 1개 조회 : GET - http://localhost:8080/api/video/1
    @GetMapping("/video/{id}")
    public ResponseEntity<Video> showVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.show(id));
    }

    // 영상 1개에 따른 댓글 전체 조회 : GET - http://localhost:8080/api/video/1/comment
    @GetMapping("/video/{id}/comment")
    public ResponseEntity<List<VideoComment>> videoCommentsList(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoComment.findByVideoCode(id));
    }


    // 좋아요 추가 : POST - http://localhost:8080/api/video/like
    @PostMapping("/video/like")
    public ResponseEntity<VideoLike> createVideoLike(@RequestBody VideoLike vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoLike.create(vo));
    }

    // 좋아요 취소 : DELETE - http://localhost:8080/api/video/like/1
    @DeleteMapping("/video/like/{id}")
    public ResponseEntity<VideoLike> deleteVideoLike(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoLike.delete(id));
    }

    // 댓글 추가 : POST - http://localhost:8080/api/video/comment
    @PostMapping("/video/comment")
    public ResponseEntity<VideoComment> createVideoComment(@RequestBody VideoComment vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoComment.create(vo));
    }

    // 댓글 수정 : PUT - http://localhost:8080/api/video/comment
    @PutMapping("/video/comment")
    public ResponseEntity<VideoComment> updateVideoComment(@RequestBody VideoComment vo) {
        return ResponseEntity.status(HttpStatus.OK).body(videoComment.update(vo));
    }


    // 댓글 삭제 : DELETE - http://localhost:8080/api/video/comment/1
    @DeleteMapping("/video/comment/{id}")
    public ResponseEntity<VideoComment> deleteVideoComment(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoComment.delete(id));
    }

    // 댓글 좋아요 추가 : PUT - http://localhost:8080/api/video/comment/like
    @PutMapping("/video/comment/like")
    public ResponseEntity<CommentLike> createCommentLike(@RequestBody CommentLike vo) {
        return ResponseEntity.status(HttpStatus.OK).body(commentLike.create(vo));
    }

    // 댓글 좋아요 취소 : DELETE - http://localhost:8080/api/video/comment/like/1
    @DeleteMapping("/video/comment/like/{id}")
    public ResponseEntity<CommentLike> deleteCommentLike(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentLike.delete(id));
    }

}