package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class VideoComment {

	@Id
	@Column(name = "comment_code")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoCommSequence")
	@SequenceGenerator(name = "videoCommSequence", sequenceName = "SEQ_VIDEO_COMMENT", allocationSize = 1)
	private int commentCode;

	@Column(name = "comment_desc")
	private String commentDesc;

	@Column(name = "comment_date")
	private Date commentDate;

	@Column(name = "comment_parent")
	private int commentParent;

	@ManyToOne
	@JoinColumn(name = "video_code")
	private Video video;

	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;

}
