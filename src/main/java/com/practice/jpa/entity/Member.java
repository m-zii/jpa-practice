package com.practice.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1, allocationSize = 1)
public class Member {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	@Column(name = "member_id")
	private Long id;
	
	@Builder
	public Member(Long id) {
		this.id = id;
	}
}
