package com.practice.jpa.repository;

import com.practice.jpa.dto.ImageDto;
import com.practice.jpa.dto.QImageDto;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import static com.practice.jpa.entity.QImage.image;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public ImageDto getImageInfo(Long imageId, Long memberId) {
		return jpaQueryFactory
				.select(new QImageDto(
						image.title,
						image.url,
						image.contents,
						image.filePath,
						image.cnt
						))
				.from(image)
				.where(image.id.eq(imageId), image.member.id.eq(memberId))
				.fetchOne();
	}

	@Override
	public long updateImageCnt(Long imageId, Long memberId) {
		return jpaQueryFactory
				.update(image).set(image.cnt, image.cnt.add(1))
				.where(image.id.eq(imageId), image.member.id.eq(memberId))
				.execute();
	}

	@Override
	public Page<ImageDto> getImageList(Long memberId, Pageable pageable) {
		QueryResults<ImageDto> result = jpaQueryFactory
				.select(new QImageDto(
						image.title, 
						image.url,
						image.contents, 
						image.filePath,
						image.cnt))
				.from(image)
				.where(image.member.id.eq(memberId))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		return new PageImpl<>(result.getResults(),pageable,result.getTotal());
	}

	@Override
	public long deleteImageInfo(Long memberId) {
		return jpaQueryFactory.delete(image).where(image.member.id.eq(memberId)).execute();
	}

	@Override
	public long updateImageInfo(Long imageId, Long memberId, ImageDto imageDto) {
		return jpaQueryFactory
				.update(image).set(image.title, imageDto.getTitle())
				.set(image.contents, imageDto.getContents())
				.where(image.id.eq(imageId), image.member.id.eq(memberId))
				.execute();
	}
}
