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

import com.qti.csdlcn.sct.model.CoSoVSATTP;
import com.qti.csdlcn.sct.model.DanhMucNNKD;
import com.qti.csdlcn.sct.repository.CoSoVSATTPRepository;
import com.qti.csdlcn.sct.repository.DanhMucNNKDRepository;
import com.qti.csdlcn.sct.repository.PageCoSoVSATTPRepository;
import com.qti.csdlcn.sct.util.AppConstants;


@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class CoSoVSATTPController {
	@Autowired
	private CoSoVSATTPRepository csvsattpRepostory;
	
	@Autowired
	private DanhMucNNKDRepository dmnnkdRepostory;

	@Autowired
	private PageCoSoVSATTPRepository pagRepostory;

	////////////////////////////
	// CREATE CO SO VS ATTP
	////////////////////////////
	@PostMapping("/cosovsattps/create")
	public ResponseEntity<?> createCoSoVSATTP(@Valid @RequestBody CoSoVSATTP coSoVSATTP) {
		System.out.println("Create CoSoVSATTP: " + coSoVSATTP.getTenCoSo() + "...");

		List<CoSoVSATTP> dm = csvsattpRepostory.findByTenCoSo(coSoVSATTP.getTenCoSo());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			csvsattpRepostory.save(coSoVSATTP);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/cosovsattps/{id}")
	public ResponseEntity<?> updateCoSoVSATTPById(@PathVariable("id") Long id, @RequestBody CoSoVSATTPUpdate coSoVSATTPNewData) {
		System.out.println("Update CoSoVSATTP with ID = " + id + "...");

		Optional<CoSoVSATTP> coSoVSATTPData = csvsattpRepostory.findById(id);
		if (coSoVSATTPData.isPresent()) {
			DanhMucNNKD nnkdUpdate = dmnnkdRepostory.findByid(coSoVSATTPNewData.getIdDanhMuc());  
			if(nnkdUpdate == null)
			{
				return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
			}
			
			CoSoVSATTP savedCoSoVSATTP = coSoVSATTPData.get();

			savedCoSoVSATTP.setTenCoSo(coSoVSATTPNewData.getTenCoSo());
			savedCoSoVSATTP.setTenChuCoSo(coSoVSATTPNewData.getTenChuCoSo());
			savedCoSoVSATTP.setDiaChiCoSo(coSoVSATTPNewData.getDiaChiCoSo());
			savedCoSoVSATTP.setMaXa(coSoVSATTPNewData.getMaXa());
			savedCoSoVSATTP.setMaHuyen(coSoVSATTPNewData.getMaHuyen());
			
			savedCoSoVSATTP.setSoGiayCN(coSoVSATTPNewData.getSoGiayCN());
			savedCoSoVSATTP.setNgayCapCN(coSoVSATTPNewData.getNgayCapCN());
			savedCoSoVSATTP.setGhiChu(coSoVSATTPNewData.getGhiChu());

			savedCoSoVSATTP.setDanhMucNNKD(nnkdUpdate);
			
			csvsattpRepostory.save(savedCoSoVSATTP);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}


	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/cosovsattps/{id}")
	public ResponseEntity<?> deleteCoSoVSATTPById(@PathVariable("id") Long id) {
		System.out.println("Delete CoSoVSATTP with ID = " + id + "...");

		try {
			csvsattpRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/cosovsattps/{id}")
	public ResponseEntity<?> getCoSoVSATTPById(@PathVariable("id") Long id) {
		System.out.println("Get CoSoVSATTP by id...");

		Optional<CoSoVSATTP> coSoVSATTPData = csvsattpRepostory.findById(id);
		if (coSoVSATTPData.isPresent()) {
			return new ResponseEntity<>(coSoVSATTPData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/cosovsattps/")
	public Page<CoSoVSATTP> getBuiHoaCoSoVSATTPs(@RequestParam(value = "keyword") String keyWord,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging CoSoVSATTPs Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();

			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = csvsattpRepostory.findByTenCoSoContainingIgnoreCaseOrTenChuCoSoContainingIgnoreCase(
						keyWord.toString(), keyWord.toString()).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<CoSoVSATTP> pagCoSoVSATTP;
			if (keyWord != "") {
				pagCoSoVSATTP = csvsattpRepostory.findByTenCoSoContainingIgnoreCaseOrTenChuCoSoContainingIgnoreCase(
						keyWord.toString(), keyWord.toString(), pageable);
			} else {
				pagCoSoVSATTP = pagRepostory.findAll(pageable);
			}
			return pagCoSoVSATTP;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}
}

class CoSoVSATTPUpdate extends CoSoVSATTP {
	private static final long serialVersionUID = 1L;
	private Long idDanhMuc;

	public Long getIdDanhMuc() {
		return idDanhMuc;
	}

	public void setIdDanhMuc(Long idDanhMuc) {
		this.idDanhMuc = idDanhMuc;
	}
	
}
