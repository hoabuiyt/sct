package com.qti.csdlcn.sct.controller;

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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qti.csdlcn.sct.model.TinhTrangCho;
import com.qti.csdlcn.sct.repository.TinhTrangChoRepository;
import com.qti.csdlcn.sct.repository.PageTinhTrangChoRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class TinhTrangChoController {

	@Autowired
	private TinhTrangChoRepository tinhTrangChoRepostory;

	@Autowired
	private PageTinhTrangChoRepository pagRepostory;

	////////////////////////////
	// CREATE TINH TRANG CHO
	////////////////////////////
	@PostMapping("/tinhtrangchos/create")
	public ResponseEntity<?> createTinhTrangCho(@Valid @RequestBody TinhTrangCho tinhTrangCho) {
		System.out.println("Create TinhTrangCho: " + tinhTrangCho.getTenTinhTrang() + "...");

		List<TinhTrangCho> dm = tinhTrangChoRepostory.findByTenTinhTrang(tinhTrangCho.getTenTinhTrang());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			tinhTrangChoRepostory.save(tinhTrangCho);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/tinhtrangchos/{id}")
	public ResponseEntity<?> updateTinhTrangChoById(@PathVariable("id") Long id, @RequestBody TinhTrangCho tinhTrangCho) {
		System.out.println("Update TinhTrangCho with ID = " + id + "...");

		Optional<TinhTrangCho> tinhTrangChoData = tinhTrangChoRepostory.findById(id);
		if (tinhTrangChoData.isPresent()) {
			TinhTrangCho savedTinhTrangCho = tinhTrangChoData.get();
			savedTinhTrangCho.setTenTinhTrang(tinhTrangCho.getTenTinhTrang());

			tinhTrangChoRepostory.save(savedTinhTrangCho);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/tinhtrangchos/{id}")
	public ResponseEntity<?> deleteTinhTrangChoById(@PathVariable("id") Long id) {
		System.out.println("Delete TinhTrangCho with ID = " + id + "...");

		try {
			tinhTrangChoRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/tinhtrangchos/{id}")
	public ResponseEntity<?> getTinhTrangChoById(@PathVariable("id") Long id) {
		System.out.println("Get TinhTrangCho by id...");

		Optional<TinhTrangCho> TinhTrangChoData = tinhTrangChoRepostory.findById(id);
		if (TinhTrangChoData.isPresent()) {
			return new ResponseEntity<>(TinhTrangChoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND ,HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET TINH TRANG CHO WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/tinhtrangchos/")
	public Page<TinhTrangCho> getBuiHoaTinhTrangChos(@RequestParam(value = "keyword") String tenTinhTrang,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging TinhTrangChos Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();
			
			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = tinhTrangChoRepostory.findByTenTinhTrangContainingIgnoreCase(tenTinhTrang).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<TinhTrangCho> pagTinhTrangCho;
			if (tenTinhTrang != "") {
				pagTinhTrangCho = tinhTrangChoRepostory.findByTenTinhTrangContainingIgnoreCase(tenTinhTrang, pageable);
			} else {
				pagTinhTrangCho = pagRepostory.findAll(pageable);
			}
			return pagTinhTrangCho;	
		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	

}
