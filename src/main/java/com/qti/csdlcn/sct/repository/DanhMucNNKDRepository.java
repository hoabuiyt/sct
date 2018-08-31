package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.DanhMucNNKD;

public interface DanhMucNNKDRepository extends CrudRepository<DanhMucNNKD, Long> {
	
	
	public List<DanhMucNNKD> findByTenDanhMuc(String tendanhmuc);
	
	public List<DanhMucNNKD> findByTenDanhMucContainingIgnoreCase(String tendanhmuc);
	
	public Page<DanhMucNNKD> findByTenDanhMuc(Pageable pageable);
	
	public Page<DanhMucNNKD> findByTenDanhMuc(String tendanhmuc, Pageable pageable);

	public Page<DanhMucNNKD> findByTenDanhMucContainingIgnoreCase(String tendanhmuc, Pageable pageable);

}
