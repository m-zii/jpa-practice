package com.practice.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
	
	@CreatedDate
	@Column(name="reg_dtime", updatable = false)
	private LocalDateTime regDtime;
	
	@LastModifiedDate
	@Column(name="upt_dtime")
    private LocalDateTime uptDtime;
}