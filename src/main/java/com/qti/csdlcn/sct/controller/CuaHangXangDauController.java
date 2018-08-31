package com.qti.csdlcn.sct.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.qti.csdlcn.sct.model.CuaHangXangDau;

import com.qti.csdlcn.sct.repository.CuaHangXangDauRepository;
import com.qti.csdlcn.sct.repository.PageCuaHangXangDauRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class CuaHangXangDauController {
	
	@Autowired
	private CuaHangXangDauRepository dlxdRepostory;
	
	@Autowired
	private PageCuaHangXangDauRepository pagRepostory;
	
	//create
	@PostMapping("/CuaHangXangDaus/create")
	public CuaHangXangDau createCuaHangXangDau(@Valid @RequestBody CuaHangXangDau cuahangxd) {
		System.out.println("Create CuaHangXangDau: " + cuahangxd.getTenCuaHang() + "...");
		
		List<CuaHangXangDau> cuahang = dlxdRepostory.findByTenCuaHang(cuahangxd.getTenCuaHang());
		if (cuahang.size() > 0) {
			return null;
		} else {
			return dlxdRepostory.save(cuahangxd);
		}
		
	}
	
	//gets
	@GetMapping("/CuaHangXangDaus")
	public List<CuaHangXangDau> getAllCuaHangXangDausss() {
		System.out.println("Get all CuaHangXangDaus...");
		List<CuaHangXangDau> list = new ArrayList<>();
		Iterable<CuaHangXangDau> CuaHangXangDaus = dlxdRepostory.findAll();
		CuaHangXangDaus.forEach(list::add);
		return list;
	}
	
	// gets tencuahang
		@GetMapping("/CuaHangXangDaus/Search/{tenCuaHang}")
		public List<CuaHangXangDau> getDaiLyXangDauTheoTenDaiLy(@PathVariable("tenDaiLy") String tenCuaHang) {
			try {
				List<CuaHangXangDau> cuahang = dlxdRepostory.findByTenCuaHang(tenCuaHang);

				if (cuahang.size()>0) {
					return cuahang;
				} else {
					return null;
				}
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
		}
		
		
		
		//search sort pagesize gets
		@GetMapping("/CuaHangXangDaus/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{tencuahang}/{iddaily}")
		public Page<CuaHangXangDau> getSearchAllCuaHangXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize,
				@PathVariable("properties_sort") String properties_sort, @PathVariable("kieu") Integer kieu, @PathVariable("tencuahang") String tencuahang,
				@PathVariable("iddaily") Integer iddaily) {
			try {
				
				System.out.println("Get all paging DaiLyXangDaus...");
				Pageable pageable;
				if(kieu == 0) 			
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
				else
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
				
				Page<CuaHangXangDau> pagcuahangxangdau;
				
				if(!(tencuahang.equals("null")) && !(iddaily == 0 || iddaily==null)) {
					pagcuahangxangdau = dlxdRepostory.findByTenCuaHang(tencuahang, iddaily, pageable);
				}
				
				else if(!(tencuahang.equals("null")) && (iddaily == 0 || iddaily==null)) {
					pagcuahangxangdau = dlxdRepostory.findByTenCuaHang(tencuahang, pageable);
				}
				
				else if((tencuahang.equals("null")) && !(iddaily == 0 || iddaily==null)) {
					pagcuahangxangdau = dlxdRepostory.findByTenCuaHang(iddaily, pageable);
				}
				
				else {
					//pagcuahangxangdau = dlxdRepostory.findByTenCuaHang(pageable);
					pagcuahangxangdau = pagRepostory.findAll(pageable);
				}

				return pagcuahangxangdau;
				
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
			
		}
	
	//pagesize gets
	@GetMapping("/CuaHangXangDaus/{pageNumber}/{pageSize}")
	public Page<CuaHangXangDau> getAllCuaHangXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize ) {
		try {
			
			System.out.println("Get all paging CuaHangXangDaus...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<CuaHangXangDau> pagCuaHangXangDau = pagRepostory.findAll(pageable);
			return pagCuaHangXangDau;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	
//	//paging and sorting
	
	@GetMapping("/CuaHangXangDaus/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
	public Page<CuaHangXangDau> getAllSortCuaHangXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize,
			@PathVariable("properties_sort") String properties_sort, @PathVariable("kieu") Integer kieu) {
		try {
			
			System.out.println("Get all paging CuaHangXangDaus...");
			Pageable pageable;
			if(kieu == 0) 			
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
			
			Page<CuaHangXangDau> pagCuaHangXangDau = pagRepostory.findAll(pageable);
			return pagCuaHangXangDau;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	//get id
	@GetMapping("/CuaHangXangDaus/{id}")
	public ResponseEntity<CuaHangXangDau> getCuaHangXangDau(@PathVariable("id") Long id) {
		System.out.println("Get CuaHangXangDau by id...");

		Optional<CuaHangXangDau> CuaHangXangDauData = dlxdRepostory.findById(id);
		if (CuaHangXangDauData.isPresent()) {
			return new ResponseEntity<>(CuaHangXangDauData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//update
	@PutMapping("/CuaHangXangDaus/{id}")
	public ResponseEntity<CuaHangXangDau> updateCuaHangXangDau(@PathVariable("id") Long id, @RequestBody CuaHangXangDau CuaHangXangDau) {
		System.out.println("Update CuaHangXangDau with ID = " + id + "...");

		Optional<CuaHangXangDau> CuaHangXangDauData = dlxdRepostory.findById(id);
		if (CuaHangXangDauData.isPresent()) {
			CuaHangXangDau savedCuaHangXangDau = CuaHangXangDauData.get();
			savedCuaHangXangDau.setTenCuaHang(CuaHangXangDau.getTenCuaHang());
			savedCuaHangXangDau.setDiaChi(CuaHangXangDau.getDiaChi());
			savedCuaHangXangDau.setGiamDoc(CuaHangXangDau.getGiamDoc());
			savedCuaHangXangDau.setIdDaiLy(CuaHangXangDau.getIdDaiLy());
			savedCuaHangXangDau.setDienThoai(CuaHangXangDau.getDienThoai());
			savedCuaHangXangDau.setHdDaiLy(CuaHangXangDau.getHdDaiLy());
			savedCuaHangXangDau.setMaTinh(CuaHangXangDau.getMaTinh());
			savedCuaHangXangDau.setMaHuyen(CuaHangXangDau.getMaHuyen());
			savedCuaHangXangDau.setMaXa(CuaHangXangDau.getMaXa());
			savedCuaHangXangDau.setIsHoatDong(CuaHangXangDau.getIsHoatDong());
			

			CuaHangXangDau updatedCuaHangXangDau = dlxdRepostory.save(savedCuaHangXangDau);
			return new ResponseEntity<>(updatedCuaHangXangDau, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//delete
	@DeleteMapping("/CuaHangXangDaus/{id}")
	public ResponseEntity<String> deleteCuaHangXangDau(@PathVariable("id") Long id) {
		System.out.println("Delete CuaHangXangDau with ID = " + id + "...");

		try {
			dlxdRepostory.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("CuaHangXangDau has been deleted!", HttpStatus.OK);
	}
		
}
