package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.LoaiCho;


public interface LoaiChoRepository extends CrudRepository<LoaiCho, Long> {
	
	public List<LoaiCho> findByTenLoai(String tenloai);
	
	public Page<LoaiCho> findByTenLoai(Pageable pageable);
	
	public Page<LoaiCho> findByTenLoai(String tenloai, Pageable pageable);

	public Page<LoaiCho> findByTenLoaiContainingIgnoreCase(String tenloai, Pageable pageable);

}
