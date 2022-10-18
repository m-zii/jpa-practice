package com.practice.jpa.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
		name = "IMAGE_SEQ_GENERATOR",
		sequenceName = "IMAGE_SEQ",
		initialValue = 1, allocationSize = 1)
public class Image extends BaseTimeEntity {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_SEQ_GENERATOR")
	@Column(name = "image_id")
	private Long id;
	
	@Column(length = 200)
	private String title;

	@Column(length = 500)
	private String url;
	
	@Lob
	private String contents;

	@Column(name = "file_path", length = 500)
	private String filePath;
	
	private int cnt;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	public void chngMember(Member member) {
		this.member = member;
		member.getImages().add(this);
    }
	
	@Builder
	public Image(Long id, String title, String url, String contents, String filePath, int cnt, Member member) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.contents = contents;
		this.filePath = filePath;
		this.cnt = cnt;
		this.member = member;
	}
}
