package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.TinhTrangCho;


public interface TinhTrangChoRepository extends CrudRepository<TinhTrangCho, Long> {
	
	public List<TinhTrangCho> findByTenTinhTrang(String tentinhtrang);
	
	public List<TinhTrangCho> findByTenTinhTrangContainingIgnoreCase(String tentinhtrang);
	
	public Page<TinhTrangCho> findByTenTinhTrang(Pageable pageable);
	
	public Page<TinhTrangCho> findByTenTinhTrang(String tentinhtrang, Pageable pageable);

	public Page<TinhTrangCho> findByTenTinhTrangContainingIgnoreCase(String tentinhtrang, Pageable pageable);

}
