package com.practice.jpa.repository;

import com.practice.jpa.dto.ImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageRepositoryCustom {

	public ImageDto getImageInfo(Long imageId, Long memberId);
	
	public long updateImageCnt(Long imageId, Long memberId);
	
	public Page<ImageDto> getImageList(Long memberId, Pageable pageable);
	
	public long deleteImageInfo(Long memberId);
	
	public long updateImageInfo(Long imageId, Long memberId, ImageDto imageDto);
}
