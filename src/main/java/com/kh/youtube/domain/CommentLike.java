package com.kh.youtube.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLike {

	private int commLikeCode;
	private Date commLikeDate;
	
	private VideoComment comment;
	private Member member;

}
