package com.qti.csdlcn.sct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.qti.csdlcn.sct.model.CuaHangXangDau;


public interface CuaHangXangDauRepository extends CrudRepository<CuaHangXangDau, Long> {

	public List<CuaHangXangDau> findByTenCuaHang(String tenCuaHang);
	
	public Page<CuaHangXangDau> findByTenCuaHang(Pageable pageable);
	
	public Page<CuaHangXangDau> findByTenCuaHang(String tencuahang, Pageable pageable);
	
	public Page<CuaHangXangDau> findByTenCuaHang(Integer iddaily, Pageable pageable);

	public Page<CuaHangXangDau> findByTenCuaHang(String tencuahang, Integer iddaily, Pageable pageable);

	

}
