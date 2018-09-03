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


import com.qti.csdlcn.sct.model.DaiDienBQL;
import com.qti.csdlcn.sct.repository.DaiDienBQLRepository;
import com.qti.csdlcn.sct.repository.PageDaiDienBQLRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class DaiDienBQLController {

	@Autowired
	private DaiDienBQLRepository daiDienBQLRepostory;

	@Autowired
	private PageDaiDienBQLRepository pagRepostory;

	////////////////////////////
	// CREATE DAI DIEN BQL
	////////////////////////////
	@PostMapping("/daidienbqls/create")
	public ResponseEntity<?> createDaiDienBQL(@Valid @RequestBody DaiDienBQL daiDienBQL) {
		System.out.println("Create daiDienBQL: " + daiDienBQL.getHoTen() + "...");

		List<DaiDienBQL> dm = daiDienBQLRepostory.findByHoTen(daiDienBQL.getHoTen());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			daiDienBQLRepostory.save(daiDienBQL);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/daidienbqls/{id}")
	public ResponseEntity<?> updateDaiDienBQLById(@PathVariable("id") Long id, @RequestBody DaiDienBQL daiDienBQL) {
		System.out.println("Update DaiDienBQL with ID = " + id + "...");

		Optional<DaiDienBQL> daiDienBQLData = daiDienBQLRepostory.findById(id);
		if (daiDienBQLData.isPresent()) {
			DaiDienBQL savedDaiDienBQL = daiDienBQLData.get();

			savedDaiDienBQL.setIdCho(daiDienBQL.getIdCho());
			savedDaiDienBQL.setHoTen(daiDienBQL.getHoTen());
			
			savedDaiDienBQL.setChucVu(daiDienBQL.getChucVu());
			savedDaiDienBQL.setDienThoai(daiDienBQL.getDienThoai());

			daiDienBQLRepostory.save(savedDaiDienBQL);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/daidienbqls/{id}")
	public ResponseEntity<?> deleteDaiDienBQLById(@PathVariable("id") Long id) {
		System.out.println("Delete DaiDienBQL with ID = " + id + "...");

		try {
			daiDienBQLRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/daidienbqls/{id}")
	public ResponseEntity<?> getDaiDienBQLById(@PathVariable("id") Long id) {
		System.out.println("Get DaiDienBQL by id...");

		Optional<DaiDienBQL> daiDienBQLData = daiDienBQLRepostory.findById(id);
		if (daiDienBQLData.isPresent()) {
			return new ResponseEntity<>(daiDienBQLData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/daidienbqls/")
	public Page<DaiDienBQL> getBuiHoaDaiDienBQLs(@RequestParam(value = "keyword") String keyWord,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging daiDienBQLs Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();

			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = daiDienBQLRepostory.findByHoTenContainingIgnoreCaseOrChucVuContainingIgnoreCaseOrDienThoaiContainingIgnoreCase(
						keyWord.toString(), keyWord.toString(), keyWord.toString()).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<DaiDienBQL> pagDaiDienBQL;
			if (keyWord != "") {
				pagDaiDienBQL = daiDienBQLRepostory.findByHoTenContainingIgnoreCaseOrChucVuContainingIgnoreCaseOrDienThoaiContainingIgnoreCase(
						keyWord.toString(), keyWord.toString(), keyWord.toString(), pageable);
			} else {
				pagDaiDienBQL = pagRepostory.findAll(pageable);
			}
			return pagDaiDienBQL;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	

}
