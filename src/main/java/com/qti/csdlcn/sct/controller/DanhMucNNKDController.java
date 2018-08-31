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
	public ResponseEntity<?> updateDanhMucNNKD(@PathVariable("id") Long id,
			@RequestBody DanhMucNNKD danhMucNNKD) {
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
	public ResponseEntity<?> deleteDanhMucNNKD(@PathVariable("id") Long id) {
		System.out.println("Delete DanhMucNNKD with ID = " + id + "...");

		try {
			dmnnkdRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@GetMapping("/danhmucnnkds/")
	public Page<DanhMucNNKD> getBuiHoaDanhMucNNKDs(@RequestParam(value = "keyword") String tendanhmuc,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging DanhMucNNKDs Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			@SuppressWarnings("deprecation")
			Pageable pageable = (kieu.equals("0"))
					? new PageRequest(page - 1, pageSize, Sort.by(properties_sort).descending())
					: new PageRequest(page - 1, pageSize, Sort.by(properties_sort).ascending());

			// tim kiem noi dung
			Page<DanhMucNNKD> pagDanhMucNNKD;
			if (!(tendanhmuc.equals("null"))) {
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

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/danhmucnnkds/{id}")
	public ResponseEntity<DanhMucNNKD> getDanhMucNNKD(@PathVariable("id") Long id) {
		System.out.println("Get DanhMucNNKD by id...");

		Optional<DanhMucNNKD> DanhMucNNKDData = dmnnkdRepostory.findById(id);
		if (DanhMucNNKDData.isPresent()) {
			return new ResponseEntity<>(DanhMucNNKDData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// gets
	@GetMapping("/danhmucnnkds")
	public List<DanhMucNNKD> getAllDanhMucNNKDsss() {
		System.out.println("Get all DanhMucNNKDs...");
		List<DanhMucNNKD> list = new ArrayList<>();
		Iterable<DanhMucNNKD> DanhMucNNKDs = dmnnkdRepostory.findAll();
		DanhMucNNKDs.forEach(list::add);
		return list;
	}
	/*
	// gets danhmuc NNKD
	@GetMapping("/danhmucnnkds/Search/{tenDanhMuc}")
	public List<DanhMucNNKD> getDanhMucNNKDTheoTenDaiLy(@PathVariable("tenDanhMuc") String tenDanhMuc) {
		try {
			List<DanhMucNNKD> daily = dmnnkdRepostory.findByTenDanhMuc(tenDanhMuc);

			if (daily.size() > 0) {
				return daily;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// pagesize gets
	@GetMapping("/danhmucnnkds/{pageNumber}/{pageSize}")
	public Page<DanhMucNNKD> getAllDanhMucNNKDs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize) {
		try {

			System.out.println("Get all paging DanhMucNNKDs...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<DanhMucNNKD> pagDanhMucNNKD = pagRepostory.findAll(pageable);
			return pagDanhMucNNKD;

		} catch (Exception e) {
			return null;
		}

	}

	// sort properties (0 giam dan) pagesize gets
	@GetMapping("/danhmucnnkds/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
	public Page<DanhMucNNKD> getSAllDanhMucNNKDs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu) {
		try {

			System.out.println("Get all paging DanhMucNNKDs...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());

			Page<DanhMucNNKD> pagDanhMucNNKD = pagRepostory.findAll(pageable);

			return pagDanhMucNNKD;

		} catch (Exception e) {
			return null;
		}

	}

	// search sort pagesize gets
	@GetMapping("/danhmucnnkds/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{tendanhmuc}")
	public Page<DanhMucNNKD> getSearchAllDanhMucNNKDs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu, @PathVariable("tendanhmuc") String tendanhmuc) {
		try {

			System.out.println("Get all paging DanhMucNNKDs...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
			Page<DanhMucNNKD> pagDanhMucNNKD;
			if (!(tendanhmuc.equals("null"))) {
				pagDanhMucNNKD = dmnnkdRepostory.findByTenDanhMuc(tendanhmuc, pageable);
			} else {
				// pagDanhMucNNKD = dmnnkdRepostory.findByTenDaiLy(pageable);
				pagDanhMucNNKD = pagRepostory.findAll(pageable);
			}
			return pagDanhMucNNKD;

		} catch (Exception e) {
			return null;
		}

	}
	
*/
	/* =============GEN DANH MUC NNKD EXAMPLE============== */

	@GetMapping("/danhmucnnkds/gendanhmuc")
	public String genDanhMuc() {
		System.out.println("Gen Danh Muc DanhMucNNKD");
		for (Integer i = 1; i < 1000; i++) {
			DanhMucNNKD nnkd = new DanhMucNNKD();
			System.out.println("DanhMucNNKD with ID = " + i);
			nnkd.setTenDanhMuc("danh mục " + String.valueOf(i) + "!!!");
			dmnnkdRepostory.save(nnkd);
		}
		return "OK";
	}

}
