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
	public ResponseEntity<?> createLoaiCho(@Valid @RequestBody LoaiCho LoaiCho) {
		System.out.println("Create LoaiCho: " + LoaiCho.getTenLoai() + "...");

		List<LoaiCho> dm = loaiChoRepostory.findByTenLoai(LoaiCho.getTenLoai());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			loaiChoRepostory.save(LoaiCho);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/loaichos/{id}")
	public ResponseEntity<?> updateLoaiCho(@PathVariable("id") Long id,
			@RequestBody LoaiCho loaiCho) {
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
	public ResponseEntity<?> deleteLoaiCho(@PathVariable("id") Long id) {
		System.out.println("Delete LoaiCho with ID = " + id + "...");

		try {
			loaiChoRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}


	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@GetMapping("/loaichos/")
	public Page<LoaiCho> getBuiHoaLoaiChos(@RequestParam(value = "keyword") String TenLoai,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging LoaiChos Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			@SuppressWarnings("deprecation")
			Pageable pageable = (kieu.equals("0"))
					? new PageRequest(page - 1, pageSize, Sort.by(properties_sort).descending())
					: new PageRequest(page - 1, pageSize, Sort.by(properties_sort).ascending());

			// tim kiem noi dung
			Page<LoaiCho> pagLoaiCho;
			if (!(TenLoai.equals("null"))) {
				pagLoaiCho = loaiChoRepostory.findByTenLoaiContainingIgnoreCase(TenLoai, pageable);
			} else {
				pagLoaiCho = pagRepostory.findAll(pageable);
			}
			return pagLoaiCho;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/loaichos/{id}")
	public ResponseEntity<LoaiCho> getLoaiCho(@PathVariable("id") Long id) {
		System.out.println("Get LoaiCho by id...");

		Optional<LoaiCho> loaiChoData = loaiChoRepostory.findById(id);
		if (loaiChoData.isPresent()) {
			return new ResponseEntity<>(loaiChoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// gets
	@GetMapping("/loaichos")
	public List<LoaiCho> getAllLoaiChosss() {
		System.out.println("Get all LoaiChos...");
		List<LoaiCho> list = new ArrayList<>();
		Iterable<LoaiCho> LoaiChos = loaiChoRepostory.findAll();
		LoaiChos.forEach(list::add);
		return list;
	}
/*
	// gets danhmuc NNKD
	@GetMapping("/loaichos/Search/{TenLoai}")
	public List<LoaiCho> getLoaiChoTheoTenDaiLy(@PathVariable("TenLoai") String TenLoai) {
		try {
			List<LoaiCho> daily = loaiChoRepostory.findByTenLoai(TenLoai);

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
	@GetMapping("/loaichos/{pageNumber}/{pageSize}")
	public Page<LoaiCho> getAllLoaiChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize) {
		try {

			System.out.println("Get all paging LoaiChos...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<LoaiCho> pagLoaiCho = pagRepostory.findAll(pageable);
			return pagLoaiCho;

		} catch (Exception e) {
			return null;
		}

	}

	// sort properties (0 giam dan) pagesize gets
	@GetMapping("/loaichos/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
	public Page<LoaiCho> getSAllLoaiChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu) {
		try {

			System.out.println("Get all paging LoaiChos...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());

			Page<LoaiCho> pagLoaiCho = pagRepostory.findAll(pageable);

			return pagLoaiCho;

		} catch (Exception e) {
			return null;
		}

	}

	// search sort pagesize gets
	@GetMapping("/loaichos/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{TenLoai}")
	public Page<LoaiCho> getSearchAllLoaiChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu, @PathVariable("TenLoai") String TenLoai) {
		try {

			System.out.println("Get all paging LoaiChos...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
			Page<LoaiCho> pagLoaiCho;
			if (!(TenLoai.equals("null"))) {
				pagLoaiCho = loaiChoRepostory.findByTenLoai(TenLoai, pageable);
			} else {
				// pagLoaiCho = loaiChoRepostory.findByTenDaiLy(pageable);
				pagLoaiCho = pagRepostory.findAll(pageable);
			}
			return pagLoaiCho;

		} catch (Exception e) {
			return null;
		}

	}
	
*/
	
	/* =============GEN HANG CHo EXAMPLE============== */
	
	 @GetMapping("/loaichos/genloaicho") 
	 public String genDanhMuc() 
	 {
		 System.out.println("Gen Danh Muc LoaiCho"); 
		 for (Integer i = 1; i <10; i++) { 
			 LoaiCho nnkd = new LoaiCho();
			 System.out.println("LoaiCho with ID = " + i);
			 nnkd.setTenLoai("Chợ Loại " + String.valueOf(i));
			 loaiChoRepostory.save(nnkd); 
			 } 
		 return "OK"; 
	 }
	 
	
}
