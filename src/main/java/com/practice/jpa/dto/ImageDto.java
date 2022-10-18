package com.practice.jpa.dto;

import com.practice.jpa.entity.Image;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ImageDto {
	
	String title;
	String url;
	String contents;
	String filePath;
	int cnt;
	
	@QueryProjection
	public ImageDto(String title, String url, String contents, String filePath, int cnt) {
		this.title = title;
		this.url = url;
		this.contents = contents;
		this.filePath = filePath;
		this.cnt = cnt;
	}
	
	public Image toEntity() {
		return Image.builder()
				.title(title)
				.url(url)
				.contents(contents)
				.filePath(filePath)
				.cnt(cnt)
				.build();
	}
}
