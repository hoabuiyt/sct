package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.QuanLyCho;


public interface QuanLyChoRepository extends CrudRepository<QuanLyCho, Long> {	
	
	public List<QuanLyCho> findByTenCho(String tencho);
	
	public List<QuanLyCho> findByTenChoContainingIgnoreCase(String tencho);
	
	public Page<QuanLyCho> findByTenCho(Pageable pageable);
	
	public Page<QuanLyCho> findByTenCho(String tencho, Pageable pageable);		
	
	public Page<QuanLyCho> findByTenChoContainingIgnoreCase(String tencho, Pageable pageable);

	//public Page<QuanLyCho> findByTenChoContainingIgnoreCase(String string, Pageable pageable);


	
}
