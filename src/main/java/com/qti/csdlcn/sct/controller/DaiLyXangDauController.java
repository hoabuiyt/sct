package com.qti.csdlcn.sct.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.qti.csdlcn.sct.model.DaiLyXangDau;
import com.qti.csdlcn.sct.repository.DaiLyXangDauRepository;
import com.qti.csdlcn.sct.repository.PageDaiLyXangDauRepository;



@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DaiLyXangDauController {
	
	@Autowired
	private DaiLyXangDauRepository dlxdRepostory;
	
	@Autowired
	private PageDaiLyXangDauRepository pagRepostory;
	
	//create
	@PostMapping("/DaiLyXangDaus/create")
	public DaiLyXangDau createDaiLyXangDau(@Valid @RequestBody DaiLyXangDau dailyxd) {
		System.out.println("Create DaiLyXangDau: " + dailyxd.getTenDaiLy() + "...");
		
		List<DaiLyXangDau> daily = dlxdRepostory.findByTenDaiLy(dailyxd.getTenDaiLy());
		if (daily.size() > 0) {
			return null;
		} else {
			return dlxdRepostory.save(dailyxd);
		}
		
	}
	
	//gets
	@GetMapping("/DaiLyXangDaus")
	public List<DaiLyXangDau> getAllDaiLyXangDausss() {
		System.out.println("Get all DaiLyXangDaus...");
		List<DaiLyXangDau> list = new ArrayList<>();
		Iterable<DaiLyXangDau> dailyxangdaus = dlxdRepostory.findAll();
		dailyxangdaus.forEach(list::add);
		return list;
	}
	
	// gets tendaily
	@GetMapping("/DaiLyXangDaus/Search/{tenDaiLy}")
	public List<DaiLyXangDau> getDaiLyXangDauTheoTenDaiLy(@PathVariable("tenDaiLy") String tenDaiLy) {
		try {
			List<DaiLyXangDau> daily = dlxdRepostory.findByTenDaiLy(tenDaiLy);

			if (daily.size() >0) {
				return daily;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
		
	
	//pagesize gets
	@GetMapping("/DaiLyXangDaus/{pageNumber}/{pageSize}")
	public Page<DaiLyXangDau> getAllDaiLyXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize ) {
		try {
			
			System.out.println("Get all paging DaiLyXangDaus...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<DaiLyXangDau> pagdailyxangdau = pagRepostory.findAll(pageable);
			return pagdailyxangdau;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	
	//sort properties (0 giam dan) pagesize gets
		@GetMapping("/DaiLyXangDaus/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
		public Page<DaiLyXangDau> getSAllDaiLyXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize,
				@PathVariable("properties_sort") String properties_sort, @PathVariable("kieu") Integer kieu) {
			try {
				
				System.out.println("Get all paging DaiLyXangDaus...");
				Pageable pageable;
				if(kieu == 0) 			
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
				else
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
				
				Page<DaiLyXangDau> pagdailyxangdau = pagRepostory.findAll(pageable);

				return pagdailyxangdau;
				
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
			
		}
		
		//search sort pagesize gets
		@GetMapping("/DaiLyXangDaus/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{tendaily}")
		public Page<DaiLyXangDau> getSearchAllDaiLyXangDaus(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize,
				@PathVariable("properties_sort") String properties_sort, @PathVariable("kieu") Integer kieu, @PathVariable("tendaily") String tendaily) {
			try {
				
				System.out.println("Get all paging DaiLyXangDaus...");
				Pageable pageable;
				if(kieu == 0) 			
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
				else
					pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
				Page<DaiLyXangDau> pagdailyxangdau;
				if(!(tendaily.equals("null"))) {
					pagdailyxangdau = dlxdRepostory.findByTenDaiLy(tendaily, pageable);
				}
				else {
					//pagdailyxangdau = dlxdRepostory.findByTenDaiLy(pageable);
					pagdailyxangdau = pagRepostory.findAll(pageable);
				}
				return pagdailyxangdau;
				
			} catch (Exception e) {
				// TODO: handle exception
				return null;
			}
			
		}
	
	
	//get id
	@GetMapping("/DaiLyXangDaus/{id}")
	public ResponseEntity<DaiLyXangDau> getDaiLyXangDau(@PathVariable("id") Long id) {
		System.out.println("Get DaiLyXangDau by id...");

		Optional<DaiLyXangDau> DaiLyXangDauData = dlxdRepostory.findById(id);
		if (DaiLyXangDauData.isPresent()) {
			return new ResponseEntity<>(DaiLyXangDauData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//update
	@PutMapping("/DaiLyXangDaus/{id}")
	public ResponseEntity<DaiLyXangDau> updateDaiLyXangDau(@PathVariable("id") Long id, @RequestBody DaiLyXangDau DaiLyXangDau) {
		System.out.println("Update DaiLyXangDau with ID = " + id + "...");

		Optional<DaiLyXangDau> DaiLyXangDauData = dlxdRepostory.findById(id);
		if (DaiLyXangDauData.isPresent()) {
			DaiLyXangDau savedDaiLyXangDau = DaiLyXangDauData.get();
			savedDaiLyXangDau.setTenDaiLy(DaiLyXangDau.getTenDaiLy());
			savedDaiLyXangDau.setDiaChi(DaiLyXangDau.getDiaChi());
			savedDaiLyXangDau.setEmail(DaiLyXangDau.getEmail());
			savedDaiLyXangDau.setFax(DaiLyXangDau.getFax());
			savedDaiLyXangDau.setDienThoai(DaiLyXangDau.getDienThoai());
			savedDaiLyXangDau.setWebsite(DaiLyXangDau.getWebsite());
			savedDaiLyXangDau.setIsHoatDong(DaiLyXangDau.getIsHoatDong());
			savedDaiLyXangDau.setIsDauMoi(DaiLyXangDau.getIsDauMoi());

			DaiLyXangDau updatedDaiLyXangDau = dlxdRepostory.save(savedDaiLyXangDau);
			return new ResponseEntity<>(updatedDaiLyXangDau, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	//delete
	@DeleteMapping("/DaiLyXangDaus/{id}")
	public ResponseEntity<String> deleteDaiLyXangDau(@PathVariable("id") Long id) {
		System.out.println("Delete DaiLyXangDau with ID = " + id + "...");

		try {
			dlxdRepostory.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("DaiLyXangDau has been deleted!", HttpStatus.OK);
	}
		
}
