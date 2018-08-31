package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.HangCho;


public interface HangChoRepository extends CrudRepository<HangCho, Long> {
	
	public List<HangCho> findByTenHang(String tenhang);
	
	public Page<HangCho> findByTenHang(Pageable pageable);
	
	public Page<HangCho> findByTenHang(String tenhang, Pageable pageable);

	public Page<HangCho> findByTenHangContainingIgnoreCase(String tenhang, Pageable pageable);

}
