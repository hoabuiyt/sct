package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.DaiDienBQL;


public interface DaiDienBQLRepository extends CrudRepository<DaiDienBQL, Long> {	
	
	public List<DaiDienBQL> findByHoTen(String hoten);
	
	public List<DaiDienBQL> findByHoTenContainingIgnoreCaseOrChucVuContainingIgnoreCaseOrDienThoaiContainingIgnoreCase(String hoten, String chucvu, String dienthoai);
	
	public Page<DaiDienBQL> findByHoTen(Pageable pageable);
	
	public Page<DaiDienBQL> findByHoTen(String hoten, Pageable pageable);		
	
	public Page<DaiDienBQL> findByHoTenContainingIgnoreCaseOrChucVuContainingIgnoreCaseOrDienThoaiContainingIgnoreCase(String tencoso,String tenchucoso, String dienthoai, Pageable pageable);


	
}
