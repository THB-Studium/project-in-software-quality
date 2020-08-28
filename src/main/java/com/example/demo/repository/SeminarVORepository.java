package com.example.demo.repository;

import java.util.UUID;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.demo.model.SeminarVO;

@Named
public interface SeminarVORepository
	extends JpaRepository<SeminarVO, UUID>, JpaSpecificationExecutor<SeminarVO> {
	
}
