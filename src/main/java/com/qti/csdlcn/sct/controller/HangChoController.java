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

import com.qti.csdlcn.sct.model.HangCho;
import com.qti.csdlcn.sct.repository.HangChoRepository;
import com.qti.csdlcn.sct.repository.PageHangChoRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class HangChoController {

	@Autowired
	private HangChoRepository hangChoRepostory;

	@Autowired
	private PageHangChoRepository pagRepostory;

	////////////////////////////
	// CREATE HANG CHO
	////////////////////////////
	@PostMapping("/hangchos/create")
	public ResponseEntity<?> createHangCho(@Valid @RequestBody HangCho HangCho) {
		System.out.println("Create HangCho: " + HangCho.getTenHang() + "...");

		List<HangCho> dm = hangChoRepostory.findByTenHang(HangCho.getTenHang());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			hangChoRepostory.save(HangCho);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/hangchos/{id}")
	public ResponseEntity<?> updateHangCho(@PathVariable("id") Long id, @RequestBody HangCho hangCho) {
		System.out.println("Update HangCho with ID = " + id + "...");

		Optional<HangCho> hangChoData = hangChoRepostory.findById(id);
		if (hangChoData.isPresent()) {
			HangCho savedHangCho = hangChoData.get();
			savedHangCho.setTenHang(hangCho.getTenHang());

			hangChoRepostory.save(savedHangCho); // SAVE NEW INFO
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/hangchos/{id}")
	public ResponseEntity<?> deleteHangCho(@PathVariable("id") Long id) {
		System.out.println("Delete HangCho with ID = " + id + "...");

		try {
			hangChoRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@GetMapping("/hangchos/")
	public Page<HangCho> getBuiHoaHangChos(@RequestParam(value = "keyword") String TenHang,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging HangChos Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			@SuppressWarnings("deprecation")
			Pageable pageable = (kieu.equals("0"))
					? new PageRequest(page - 1, pageSize, Sort.by(properties_sort).descending())
					: new PageRequest(page - 1, pageSize, Sort.by(properties_sort).ascending());

			// tim kiem noi dung
			Page<HangCho> pagHangCho;
			if (!(TenHang.equals("null"))) {
				pagHangCho = hangChoRepostory.findByTenHangContainingIgnoreCase(TenHang, pageable);
			} else {
				pagHangCho = pagRepostory.findAll(pageable);
			}
			return pagHangCho;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/hangchos/{id}")
	public ResponseEntity<HangCho> getHangCho(@PathVariable("id") Long id) {
		System.out.println("Get HangCho by id...");

		Optional<HangCho> hangChoData = hangChoRepostory.findById(id);
		if (hangChoData.isPresent()) {
			return new ResponseEntity<>(hangChoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// gets
	@GetMapping("/hangchos")
	public List<HangCho> getAllHangChosss() {
		System.out.println("Get all HangChos...");
		List<HangCho> list = new ArrayList<>();
		Iterable<HangCho> HangChos = hangChoRepostory.findAll();
		HangChos.forEach(list::add);
		return list;
	}

	/*
	// gets danhmuc NNKD
	@GetMapping("/hangchos/Search/{TenHang}")
	public List<HangCho> getHangChoTheoTenDaiLy(@PathVariable("TenHang") String TenHang) {
		try {
			List<HangCho> daily = hangChoRepostory.findByTenHang(TenHang);

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
	@GetMapping("/hangchos/{pageNumber}/{pageSize}")
	public Page<HangCho> getAllHangChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize) {
		try {

			System.out.println("Get all paging HangChos...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<HangCho> pagHangCho = pagRepostory.findAll(pageable);
			return pagHangCho;

		} catch (Exception e) {
			return null;
		}

	}

	// sort properties (0 giam dan) pagesize gets
	@GetMapping("/hangchos/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
	public Page<HangCho> getSAllHangChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu) {
		try {

			System.out.println("Get all paging HangChos...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());

			Page<HangCho> pagHangCho = pagRepostory.findAll(pageable);

			return pagHangCho;

		} catch (Exception e) {
			return null;
		}

	}

	// search sort pagesize gets
	@GetMapping("/hangchos/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{TenHang}")
	public Page<HangCho> getSearchAllHangChos(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu, @PathVariable("TenHang") String TenHang) {
		try {

			System.out.println("Get all paging HangChos...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
			Page<HangCho> pagHangCho;
			if (!(TenHang.equals("null"))) {
				pagHangCho = hangChoRepostory.findByTenHang(TenHang, pageable);
			} else {
				// pagHangCho = hangChoRepostory.findByTenDaiLy(pageable);
				pagHangCho = pagRepostory.findAll(pageable);
			}
			return pagHangCho;

		} catch (Exception e) {
			return null;
		}

	}
	
*/
	/* =============GEN HANG CHo EXAMPLE============== */

	@GetMapping("/hangchos/genhangcho")
	public String genDanhMuc() {
		System.out.println("Gen Danh Muc HangCho");
		for (Integer i = 1; i < 10; i++) {
			HangCho nnkd = new HangCho();
			System.out.println("HangCho with ID = " + i);
			nnkd.setTenHang("Chợ Hạng " + String.valueOf(i));
			hangChoRepostory.save(nnkd);
		}
		return "OK";
	}

}
