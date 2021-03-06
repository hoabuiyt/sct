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

import com.qti.csdlcn.sct.model.LoaiCho;
import com.qti.csdlcn.sct.repository.LoaiChoRepository;
import com.qti.csdlcn.sct.repository.PageLoaiChoRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class LoaiChoController {

	@Autowired
	private LoaiChoRepository loaiChoRepostory;

	@Autowired
	private PageLoaiChoRepository pagRepostory;

	////////////////////////////
	// CREATE LOAI CHO
	////////////////////////////
	@PostMapping("/loaichos/create")
	public ResponseEntity<?> createLoaiCho(@Valid @RequestBody LoaiCho loaiCho) {
		System.out.println("Create LoaiCho: " + loaiCho.getTenLoai() + "...");

		List<LoaiCho> dm = loaiChoRepostory.findByTenLoai(loaiCho.getTenLoai());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			loaiChoRepostory.save(loaiCho);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/loaichos/{id}")
	public ResponseEntity<?> updateLoaiChoById(@PathVariable("id") Long id, @RequestBody LoaiCho loaiCho) {
		System.out.println("Update LoaiCho with ID = " + id + "...");

		Optional<LoaiCho> loaiChoData = loaiChoRepostory.findById(id);
		if (loaiChoData.isPresent()) {
			LoaiCho savedLoaiCho = loaiChoData.get();
			savedLoaiCho.setTenLoai(loaiCho.getTenLoai());

			loaiChoRepostory.save(savedLoaiCho);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/loaichos/{id}")
	public ResponseEntity<?> deleteLoaiChoById(@PathVariable("id") Long id) {
		System.out.println("Delete LoaiCho with ID = " + id + "...");

		try {
			loaiChoRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/loaichos/{id}")
	public ResponseEntity<?> getLoaiChoById(@PathVariable("id") Long id) {
		System.out.println("Get LoaiCho by id...");

		Optional<LoaiCho> loaiChoData = loaiChoRepostory.findById(id);
		if (loaiChoData.isPresent()) {
			return new ResponseEntity<>(loaiChoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND ,HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/loaichos/")
	public Page<LoaiCho> getBuiHoaLoaiChos(@RequestParam(value = "keyword") String tenLoai,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging LoaiChos Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();
			
			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = loaiChoRepostory.findByTenLoaiContainingIgnoreCase(tenLoai).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<LoaiCho> pagLoaiCho;
			if (tenLoai != "") {
				pagLoaiCho = loaiChoRepostory.findByTenLoaiContainingIgnoreCase(tenLoai, pageable);
			} else {
				pagLoaiCho = pagRepostory.findAll(pageable);
			}
			return pagLoaiCho;	
		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	

}
