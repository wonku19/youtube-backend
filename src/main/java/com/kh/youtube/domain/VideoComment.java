package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoComment {
	private int commentCode;
	private String commentDesc;
	private Date commentDate;
	private int commentParent;
	
	private Video video;
	private Member member;

}
