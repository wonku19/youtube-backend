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
public class Video {

	@Id
	@Column(name = "video_code")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "videoSequence")
	@SequenceGenerator(name = "videoSequence", sequenceName = "SEQ_VIDEO", allocationSize = 1)
	private int videoCode;

	@Column(name = "video_title")
	private String videoTitle;

	@Column(name = "video_desc")
	private String videoDesc;

	@Column(name = "video_date")
	private Date videoDate;

	@Column(name = "video_views")
	private int videoViews;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "video_photo")
	private String videoPhoto;

	@ManyToOne
	@JoinColumn(name="category_code")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "channel_code")
	private Channel channel;

	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;

	@Column(name = "upload_path")
	private String uploadPath;

}