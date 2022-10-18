package com.practice.jpa.controller;

import java.util.Collections;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import com.practice.jpa.dto.ImageDto;
import com.practice.jpa.dto.PageRequest;
import com.practice.jpa.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
	
	private final Logger logger = LoggerFactory.getLogger(ImageController.class);
	private final ImageService imageService;

	/**
	 * 스크랩 이미지 상세조회
	 */
	@GetMapping("/get")
	public ResponseEntity<Object> getImageInfo(@RequestParam(value="image_id") Long imageId, @RequestParam(value="member_id") Long memberId) {
		try {
			return ResponseEntity.ok(imageService.getImageInfo(imageId, memberId));
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "Cannot Find Infomation"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<>(Collections.singletonMap("error", "Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 스크랩 이미지 목록조회
	 */
	@GetMapping("/list")
	public ResponseEntity<Object> getImageList(@RequestParam(value="member_id") Long memberId, @PageableDefault(page = 1, size = 10) PageRequest pageRequest) {
		try {
			return ResponseEntity.ok(imageService.getImageList(memberId, pageRequest.of()));
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "Cannot Find Infomation"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<>(Collections.singletonMap("error", "Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 스크랩 이미지 데이터 삭제
	 */
	@DeleteMapping("/remove")
	public ResponseEntity<Map<String, Object>> deleteImageInfo(@RequestParam(value="member_id") Long memberId) {
		try {
			long result = imageService.deleteImageInfo(memberId);
			return new ResponseEntity<>(Collections.singletonMap("deleted cnt", result), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "Cannot Find Infomation"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<>(Collections.singletonMap("error", "Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * 스크랩 이미지 데이터 수정
	 */
	@PatchMapping("/modify")
	public ResponseEntity<Map<String, Object>> updateImageInfo(@RequestParam(value="image_id") Long imageId, @RequestParam(value="member_id") Long memberId, @RequestBody ImageDto imageDto) {
		try {
			long result = imageService.updateImageInfo(imageId, memberId, imageDto);
			return new ResponseEntity<>(Collections.singletonMap("updated cnt", result), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "Cannot Find Infomation"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<>(Collections.singletonMap("error", "Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 스크랩 이미지 생성
	 */
	@PostMapping("/create")
	public ResponseEntity<Map<String, Object>> insertImageInfo(@RequestParam(value="member_id") Long memberId, @RequestBody ImageDto imageDto) {
		try {
			long result = imageService.insertImageInfo(memberId, imageDto);
			return new ResponseEntity<>(Collections.singletonMap("created id", result), HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonMap("error", "Cannot Find Infomation"), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<>(Collections.singletonMap("error", "Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
