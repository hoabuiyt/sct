package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.CoSoVSATTP;


public interface CoSoVSATTPRepository extends CrudRepository<CoSoVSATTP, Long> {	
	
	public List<CoSoVSATTP> findByTenCoSo(String tencoso);
	
	public List<CoSoVSATTP> findByTenCoSoContainingIgnoreCaseOrTenChuCoSoContainingIgnoreCase(String tencoso, String tenchucoso);
	
	public Page<CoSoVSATTP> findByTenCoSo(Pageable pageable);
	
	public Page<CoSoVSATTP> findByTenCoSo(String tencoso, Pageable pageable);		
	
	public Page<CoSoVSATTP> findByTenCoSoContainingIgnoreCaseOrTenChuCoSoContainingIgnoreCase(String tencoso,String tenchucoso, Pageable pageable);


	
}
