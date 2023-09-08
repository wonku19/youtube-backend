package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

	@Id
	@Column(name="category_code")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "categorySeq")
	@SequenceGenerator(name="categorySeq", sequenceName = "SEQ_CATEGORY", allocationSize=1)
	//allocationSize = "증가할 숫자"
	private int categoryCode;

	@Column(name="category_name")
	private String categoryName;

}
