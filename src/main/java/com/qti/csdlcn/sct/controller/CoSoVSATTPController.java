package com.qti.csdlcn.sct.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.github.javafaker.Faker;
import com.qti.csdlcn.sct.model.CoSoVSATTP;
import com.qti.csdlcn.sct.repository.CoSoVSATTPRepository;
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
	private PageCoSoVSATTPRepository pagRepostory;

	////////////////////////////
	// CREATE CO SO VS ATTP
	////////////////////////////
	@PostMapping("/cosovsattps/create")
	public ResponseEntity<?> createCoSoVSATTP(@Valid @RequestBody CoSoVSATTP CoSoVSATTP) {
		System.out.println("Create CoSoVSATTP: " + CoSoVSATTP.getTenCoSo() + "...");

		List<CoSoVSATTP> dm = csvsattpRepostory.findByTenCoSo(CoSoVSATTP.getTenCoSo());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			csvsattpRepostory.save(CoSoVSATTP);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/cosovsattps/{id}")
	public ResponseEntity<?> updateCoSoVSATTP(@PathVariable("id") Long id,
			@RequestBody CoSoVSATTP CoSoVSATTP) {
		System.out.println("Update CoSoVSATTP with ID = " + id + "...");

		Optional<CoSoVSATTP> CoSoVSATTPData = csvsattpRepostory.findById(id);
		if (CoSoVSATTPData.isPresent()) {
			CoSoVSATTP savedCoSoVSATTP = CoSoVSATTPData.get();
			savedCoSoVSATTP.setTenCoSo(CoSoVSATTP.getTenCoSo());
			
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
	public ResponseEntity<?> deleteCoSoVSATTP(@PathVariable("id") Long id) {
		System.out.println("Delete CoSoVSATTP with ID = " + id + "...");

		try {
			csvsattpRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
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
			@SuppressWarnings("deprecation")
			Pageable pageable = (kieu.equals("0"))
					? new PageRequest(page - 1, pageSize, Sort.by(properties_sort).descending())
					: new PageRequest(page - 1, pageSize, Sort.by(properties_sort).ascending());

			// tim kiem noi dung
			Page<CoSoVSATTP> pagCoSoVSATTP;
			if (!(keyWord.equals("null"))) {
				// pagCoSoVSATTP =
				// csvsattpRepostory.findByTenCoSoOrTenChuCoSoContainingIgnoreCase(keyWord.toString(),
				// keyWord.toString(), pageable);
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

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/cosovsattps/{id}")
	public ResponseEntity<CoSoVSATTP> getCoSoVSATTP(@PathVariable("id") Long id) {
		System.out.println("Get CoSoVSATTP by id...");

		Optional<CoSoVSATTP> CoSoVSATTPData = csvsattpRepostory.findById(id);
		if (CoSoVSATTPData.isPresent()) {
			return new ResponseEntity<>(CoSoVSATTPData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// gets
	@GetMapping("/cosovsattps")
	public List<CoSoVSATTP> getAllCoSoVSATTPsss() {
		System.out.println("Get all CoSoVSATTPs...");
		List<CoSoVSATTP> list = new ArrayList<>();
		Iterable<CoSoVSATTP> CoSoVSATTPs = csvsattpRepostory.findAll();
		CoSoVSATTPs.forEach(list::add);
		return list;
	}
	/*
	// gets CoSo NNKD
	@GetMapping("/cosovsattps/Search/{tenCoSo}")
	public List<CoSoVSATTP> getCoSoVSATTPTheoTenDaiLy(@PathVariable("tenCoSo") String tenCoSo) {
		try {
			List<CoSoVSATTP> daily = csvsattpRepostory.findByTenCoSo(tenCoSo);

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
	@GetMapping("/cosovsattps/{pageNumber}/{pageSize}")
	public Page<CoSoVSATTP> getAllCoSoVSATTPs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize) {
		try {

			System.out.println("Get all paging CoSoVSATTPs...");
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			Page<CoSoVSATTP> pagCoSoVSATTP = pagRepostory.findAll(pageable);
			return pagCoSoVSATTP;

		} catch (Exception e) {
			return null;
		}

	}

	// sort properties (0 giam dan) pagesize gets
	@GetMapping("/cosovsattps/{pageNumber}/{pageSize}/{properties_sort}/{kieu}")
	public Page<CoSoVSATTP> getSAllCoSoVSATTPs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu) {
		try {

			System.out.println("Get all paging CoSoVSATTPs...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());

			Page<CoSoVSATTP> pagCoSoVSATTP = pagRepostory.findAll(pageable);

			return pagCoSoVSATTP;

		} catch (Exception e) {
			return null;
		}

	}

	// search sort pagesize gets
	@GetMapping("/cosovsattps/{pageNumber}/{pageSize}/{properties_sort}/{kieu}/{tenCoSo}")
	public Page<CoSoVSATTP> getSearchAllCoSoVSATTPs(@PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @PathVariable("properties_sort") String properties_sort,
			@PathVariable("kieu") Integer kieu, @PathVariable("tenCoSo") String tenCoSo) {
		try {

			System.out.println("Get all paging CoSoVSATTPs...");
			Pageable pageable;
			if (kieu == 0)
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).descending());
			else
				pageable = new PageRequest(pageNumber, pageSize, Sort.by(properties_sort).ascending());
			Page<CoSoVSATTP> pagCoSoVSATTP;
			if (!(tenCoSo.equals("null"))) {
				pagCoSoVSATTP = csvsattpRepostory.findByTenCoSo(tenCoSo, pageable);
			} else {
				// pagCoSoVSATTP = csvsattpRepostory.findByTenDaiLy(pageable);
				pagCoSoVSATTP = pagRepostory.findAll(pageable);
			}
			return pagCoSoVSATTP;

		} catch (Exception e) {
			return null;
		}

	}*/

	
	/* =============GEN CO SO VS ATTP EXAMPLE============== */
	@GetMapping("/cosovsattps/gencoso")
	public String genDanhMuc(@RequestParam(value = "n") Integer n) {
		Date dateBegin, dateEnd;
		long ngaycap = 0;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Faker faker = new Faker();
		n = ((n != null) ? n : 1000);
		for (int i = 1; i < n; i++) {
			String hero = faker.superhero().name();
			String name = faker.name().fullName();
			String streetAddress = faker.address().streetAddress();
			String ghichu = faker.lorem().sentence();
			try {
				dateBegin = (Date) formatter.parse("01-07-2018");
				dateEnd = (Date) formatter.parse("30-08-2018");
				ngaycap = faker.date().between(dateBegin, dateEnd).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			CoSoVSATTP cs = new CoSoVSATTP();
			cs.setTenCoSo(hero);
			cs.setTenChuCoSo(name);
			cs.setDiaChiCoSo(streetAddress);
			cs.setMaXa("Mã xã " + i);
			cs.setMaHuyen("Mã huyện " + i);
			cs.setMaTinh("Mã tỉnh " + i);

			// cs.setIdDanhMuc((long)i);
			cs.setSoGiayCN("Số giấy CN " + i);
			cs.setNgayCapCN(ngaycap);
			cs.setGhiChu("Ghi chú " + ghichu);

			long x = 100 + (int) (Math.random() * 500);
			cs.setIdDanhMuc(x);

			createCoSoVSATTP(cs);
		}
		return "OK";
	}

}
