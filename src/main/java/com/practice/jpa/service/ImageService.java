package com.practice.jpa.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.practice.jpa.dto.ImageDto;
import com.practice.jpa.entity.Image;
import com.practice.jpa.entity.Member;
import com.practice.jpa.repository.ImageRepository;
import com.practice.jpa.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final Logger logger = LoggerFactory.getLogger(ImageService.class);
	
	private final ImageRepository imageRepository;
	private final MemberRepository memberRepository;
	
	@Value("${user.path.upload-images}")
    private String path;

	@Transactional
	public ImageDto getImageInfo(Long imageId, Long memberId) {
		memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
		ImageDto imageDto = imageRepository.getImageInfo(imageId, memberId);

		if(!ObjectUtils.isEmpty(imageDto)) {
			imageRepository.updateImageCnt(imageId, memberId);
		}
		
		return imageDto;
	}
	
	public Page<ImageDto> getImageList(Long memberId, Pageable pageable) {
		memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
		return imageRepository.getImageList(memberId, pageable);
	}
	
	@Transactional
	public long deleteImageInfo(Long memberId) {
		memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
		return imageRepository.deleteImageInfo(memberId);
	}
	
	@Transactional
	public long updateImageInfo(Long imageId, Long memberId, ImageDto imageDto) {
		memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
		return imageRepository.updateImageInfo(imageId, memberId, imageDto);
	}
	
	@Transactional
	public long insertImageInfo(Long memberId, ImageDto imageDto) throws Exception {
		// �̹��� ����
		String imagePath = imageDto.getUrl();
		String fileNm = "";
		
		try {
			URL url = new URL(imagePath);
			String extension = imagePath.substring(imagePath.lastIndexOf('.') + 1);
			fileNm = imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.length());
			
			BufferedImage bImage = ImageIO.read(url);
			File file = new File(path + fileNm);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			ImageIO.write(bImage, extension, file);
		} catch (IOException e) {
			logger.debug(e.toString());
			throw new IOException("���� ���忡 �����߽��ϴ�.");
		}
		File rootPath = new File("").getAbsoluteFile();
		
		Member member = memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
		Image image = imageDto.toEntity();
		image.chngMember(member);
		image.setFilePath(rootPath + File.separator + Paths.get(path, fileNm).toString());
		
		Long imageId = imageRepository.save(image).getId();

		return imageId;
	}
}
