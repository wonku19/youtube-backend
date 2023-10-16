package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoCommentDTO {
    private int commentCode;
    private String commentDesc;
    private Date commentDate;
    private int videoCode;
    private Member member;
    private List<VideoComment> replies = new ArrayList<>();
}