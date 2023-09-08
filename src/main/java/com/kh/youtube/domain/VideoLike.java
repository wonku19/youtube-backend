package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoLike {
	
	private int vLikeCode;
	private Date vLikeDate;
	
	private Video video;
	private Member member;


}
