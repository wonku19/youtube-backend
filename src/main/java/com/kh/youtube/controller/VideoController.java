package com.kh.youtube.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kh.youtube.domain.*;
import com.kh.youtube.service.ChannelService;
import com.kh.youtube.service.VideoCommentService;
import com.kh.youtube.service.VideoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/*")
@CrossOrigin(origins={"*"}, maxAge = 6000)
public class VideoController {

    @Value("${youtube.upload.path}") // application.properties에 있는 변수
    private String uploadPath;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private VideoCommentService comment;

    // 영상 전체 조회 : GET - http://localhost:8080/api/video
    @GetMapping("/public/video")
    public ResponseEntity<List<Video>> videoList(@RequestParam(name="page", defaultValue = "1") int page, @RequestParam(name="category", required = false) Integer category) {

        // 정렬
        Sort sort = Sort.by("videoCode").descending();

        // 한 페이지의 10개
        Pageable pageable = PageRequest.of(page-1, 20, sort);

        // 동적 쿼리를 위한 QuerlDSL을 사용한 코드들 추가

        // 1. Q도메인 클래스를 가져와야 한다.
        QVideo qVideo = QVideo.video;

        // 2. BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너
        BooleanBuilder builder = new BooleanBuilder();

        if(category!=null) {
            // 3. 원하는 조건은 필드값과 같이 결합해서 생성한다.
            BooleanExpression expression = qVideo.category.categoryCode.eq(category);

            // 4. 만들어진 조건은 where문에 and나 or 같은 키워드와 결합한다.
            builder.and(expression);
        }

        Page<Video> result = videoService.showAll(pageable, builder);

        //log.info("Total Pages : " + result.getTotalPages()); // 총 몇 페이지
        //log.info("Total Count : " + result.getTotalElements()); // 전체 개수
        //log.info("Page Number : " + result.getNumber()); // 현재 페이지 번호
        //log.info("Page Size : " + result.getSize()); // 페이지당 데이터 개수
        //log.info("Next Page : " + result.hasNext()); // 다음 페이지가 있는지 존재 여부
        //log.info("First Page : " + result.isFirst()); // 시작 페이지 여부

        //return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(result.getContent());
    }

    // 영상 추가 : POST - http://localhost:8080/api/video
    @PostMapping("/video")
    public ResponseEntity<Video> createVideo(@AuthenticationPrincipal String id, @RequestParam(name="video", required = false) MultipartFile video, @RequestParam(name="image", required = false) MultipartFile image, String title, @RequestParam(name="desc", required = false) String desc, String categoryCode) {
        log.info("video : " + video);
        log.info("image : " + image);
        log.info("title : " + title);
        log.info("desc : " + desc);
        log.info("categoryCode : " + categoryCode);
        // video_title, video_desc, video_url, video_photo, category_code
        Video vo = new Video();
        // 업로드 처리
        // 비디오의 실제 파일 이름
        try {
            String originalVideo = video.getOriginalFilename();
            String realVideo = originalVideo.substring(originalVideo.lastIndexOf("\\")+1);
            log.info("realVideo : " + realVideo);

            // 이미지의 실제 파일 이름
            String originalImage = image.getOriginalFilename();
            String realImage = originalImage.substring(originalImage.lastIndexOf("\\")+1);

            // UUID
            String uuid = UUID.randomUUID().toString();

            // 실제로 저장할 파일 명 (위치 포함)
            String saveVideo = uploadPath + File.separator + uuid + "_" + realVideo;
            Path pathVideo = Paths.get(saveVideo);

            String saveImage = uploadPath + File.separator + uuid + "_" + realImage;
            Path pathImage = Paths.get(saveImage);

            video.transferTo(pathVideo);
            image.transferTo(pathImage);

            vo.setVideoTitle(title);
            vo.setVideoDesc(desc);
            vo.setVideoUrl(uuid + "_" + realVideo);
            vo.setVideoPhoto(uuid + "_" + realImage);

            Category category = new Category();
            category.setCategoryCode(Integer.parseInt(categoryCode));
            vo.setCategory(category);

            // 내 채널 조회
            List<Channel> channelList = channelService.showMember(id);
            Channel channel = new Channel();
            channel.setChannelCode(channelList.get(0).getChannelCode());
            vo.setChannel(channel);
            log.info("channel : " + channelList.get(0));

            Member member = new Member();
            member.setId(id); // @AuthenticationPrincipal String id 값 가져와서 넣기
            vo.setMember(member);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//    log.info("empty test ======================>");



        //return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.OK).body(videoService.create(vo));
    }

    // 영상 1개 조회 : GET - http://localhost:8080/api/video/1
    @GetMapping("/public/video/{id}")
    public ResponseEntity<Video> showVideo(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(videoService.show(id));
    }


    // 영상 1개에 따른 댓글 전체 조회 : GET - http://localhost:8080/api/video/1/comment
    @GetMapping("/public/video/{id}/comment")
    public ResponseEntity<List<VideoCommentDTO>> videoCommentList(@PathVariable int id) {
        List<VideoComment> topList = comment.getAllTopLevelComments(id);
        log.info("top : " + topList);

        List<VideoCommentDTO> response = new ArrayList<>();

        for(VideoComment item : topList) {
            VideoCommentDTO dto = new VideoCommentDTO();
            dto.setVideoCode(item.getVideoCode());
            dto.setCommentCode(item.getCommentCode());
            dto.setCommentDesc(item.getCommentDesc());
            dto.setMember(item.getMember());
            List<VideoComment> result = comment.getRepliesByCommentId(item.getCommentCode(), id);
            dto.setReplies(result);
            response.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }



    // 댓글 추가 : POST - http://localhost:8080/api/video/comment
    @PostMapping("/video/comment")
    public ResponseEntity<VideoComment> createComment(@RequestBody VideoComment vo, @AuthenticationPrincipal String id) {
        Member member = new Member();
        member.setId(id);
        vo.setMember(member);
        return ResponseEntity.status(HttpStatus.OK).body(comment.create(vo));
    }

    // 댓글 수정 : PUT - http://localhost:8080/api/video/comment
    @PutMapping("/video/comment")
    public ResponseEntity<VideoComment> updateComment(@RequestBody VideoComment vo, @AuthenticationPrincipal String id) {
        Member member = new Member();
        member.setId(id);
        vo.setMember(member);
        // 아.. 날짜... ㅡㅡ
        vo.setCommentDate(new Date());
        return ResponseEntity.status(HttpStatus.OK).body(comment.update(vo));
    }

    // 댓글 삭제 : DELETE - http://localhost:8080/api/video/comment/1
    @DeleteMapping("/video/comment/{id}")
    public ResponseEntity<VideoComment> deleteComment(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(comment.delete(id));
    }

}








