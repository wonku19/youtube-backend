package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
	private int videoCode;
	private String videoTitle;
	private String videoDesc;
	private Date videoDate;
	private int videoViews;
	private String videoUrl;
	private String videoPhoto;
	
	private Category category;
	private Channel channel;
	private Member member;
	

}
