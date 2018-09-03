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

import com.qti.csdlcn.sct.model.DanhMucNNKD;
import com.qti.csdlcn.sct.repository.DanhMucNNKDRepository;
import com.qti.csdlcn.sct.repository.PageDanhMucNNKDRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class DanhMucNNKDController {

	@Autowired
	private DanhMucNNKDRepository dmnnkdRepostory;

	@Autowired
	private PageDanhMucNNKDRepository pagRepostory;

	////////////////////////////
	// CREATE DANH MUC NNKD
	////////////////////////////
	@PostMapping("/danhmucnnkds/create")
	public ResponseEntity<?> createDanhMucNNKD(@Valid @RequestBody DanhMucNNKD danhMucNNKD) {
		System.out.println("Create DanhMuc NNKD: " + danhMucNNKD.getTenDanhMuc() + "...");
		List<DanhMucNNKD> dm = dmnnkdRepostory.findByTenDanhMuc(danhMucNNKD.getTenDanhMuc());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			dmnnkdRepostory.save(danhMucNNKD);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/danhmucnnkds/{id}")
	public ResponseEntity<?> updateDanhMucNNKDById(@PathVariable("id") Long id, @RequestBody DanhMucNNKD danhMucNNKD) {
		System.out.println("Update DanhMucNNKD with ID = " + id + "...");

		Optional<DanhMucNNKD> DanhMucNNKDData = dmnnkdRepostory.findById(id);
		if (DanhMucNNKDData.isPresent()) {
			DanhMucNNKD savedDanhMucNNKD = DanhMucNNKDData.get();

			savedDanhMucNNKD.setTenDanhMuc(danhMucNNKD.getTenDanhMuc());
			dmnnkdRepostory.save(savedDanhMucNNKD);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/danhmucnnkds/{id}")
	public ResponseEntity<?> deleteDanhMucNNKDById(@PathVariable("id") Long id) {
		System.out.println("Delete DanhMucNNKD with ID = " + id + "...");

		try {
			dmnnkdRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/danhmucnnkds/{id}")
	public ResponseEntity<?> getDanhMucNNKDById(@PathVariable("id") Long id) {
		System.out.println("Get DanhMucNNKD by id...");

		Optional<DanhMucNNKD> DanhMucNNKDData = dmnnkdRepostory.findById(id);
		if (DanhMucNNKDData.isPresent()) {
			return new ResponseEntity<>(DanhMucNNKDData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND,HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/danhmucnnkds/")
	public Page<DanhMucNNKD> getBuiHoaDanhMucNNKDs(@RequestParam(value = "keyword") String tendanhmuc,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("### Get paging DanhMucNNKDs Bui Hoa...");

			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort; // kiểm

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();

			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = dmnnkdRepostory.findByTenDanhMucContainingIgnoreCase(tendanhmuc).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<DanhMucNNKD> pagDanhMucNNKD;
			if (tendanhmuc != "") {
				pagDanhMucNNKD = dmnnkdRepostory.findByTenDanhMucContainingIgnoreCase(tendanhmuc, pageable);
			} else {
				pagDanhMucNNKD = pagRepostory.findAll(pageable);
			}
			return pagDanhMucNNKD;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

}
