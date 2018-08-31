package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import com.qti.csdlcn.sct.model.DaiLyXangDau;

public interface DaiLyXangDauRepository extends CrudRepository<DaiLyXangDau, Long> {
	
	
	public List<DaiLyXangDau> findByTenDaiLy(String tendaily);
	
	public Page<DaiLyXangDau> findByTenDaiLy(Pageable pageable);
	
	public Page<DaiLyXangDau> findByTenDaiLy(String tendaily, Pageable pageable);

	

}
