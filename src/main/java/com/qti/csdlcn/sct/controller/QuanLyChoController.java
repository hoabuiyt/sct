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


import com.qti.csdlcn.sct.model.QuanLyCho;
import com.qti.csdlcn.sct.repository.QuanLyChoRepository;
import com.qti.csdlcn.sct.repository.PageQuanLyChoRepository;
import com.qti.csdlcn.sct.util.AppConstants;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class QuanLyChoController {

	@Autowired
	private QuanLyChoRepository quanLyChoRepostory;

	@Autowired
	private PageQuanLyChoRepository pagRepostory;

	////////////////////////////
	// CREATE QUAN LY CHO
	////////////////////////////
	@PostMapping("/quanlychos/create")
	public ResponseEntity<?> createQuanLyCho(@Valid @RequestBody QuanLyCho quanLyCho) {
		System.out.println("Create QuanLyCho: " + quanLyCho.getTenCho() + "...");

		List<QuanLyCho> dm = quanLyChoRepostory.findByTenCho(quanLyCho.getTenCho());
		if (dm.size() > 0) {
			return new ResponseEntity<>(AppConstants.CREATE_NAME_EXIST, HttpStatus.BAD_REQUEST);
		} else {
			quanLyChoRepostory.save(quanLyCho);
			return new ResponseEntity<>(AppConstants.CREATE_SUCCESS, HttpStatus.OK);
		}
	}

	//////////////////
	// UPDATE BY ID
	//////////////////
	@PutMapping("/quanlychos/{id}")
	public ResponseEntity<?> updateQuanLyChoById(@PathVariable("id") Long id, @RequestBody QuanLyCho quanLyCho) {
		System.out.println("Update QuanLyCho with ID = " + id + "...");

		Optional<QuanLyCho> quanLyChoData = quanLyChoRepostory.findById(id);
		if (quanLyChoData.isPresent()) {
			QuanLyCho savedQuanLyCho = quanLyChoData.get();

			savedQuanLyCho.setIdHang(quanLyCho.getIdHang());
			savedQuanLyCho.setIdLoai(quanLyCho.getIdLoai());
			savedQuanLyCho.setIdTinhTrang(quanLyCho.getIdTinhTrang());
			
			savedQuanLyCho.setTenCho(quanLyCho.getTenCho());
			savedQuanLyCho.setDiaChi(quanLyCho.getDiaChi());
			
			savedQuanLyCho.setMaXa(quanLyCho.getMaXa());
			savedQuanLyCho.setMaHuyen(quanLyCho.getMaHuyen());
			savedQuanLyCho.setMaTinh(quanLyCho.getMaTinh());
						
			savedQuanLyCho.setVonTrungUong(quanLyCho.getVonTrungUong());
			savedQuanLyCho.setVonDiaPhuong(quanLyCho.getVonDiaPhuong());
			savedQuanLyCho.setVonKhac(quanLyCho.getVonKhac());
			
			savedQuanLyCho.setNgayThanhLap(quanLyCho.getNgayThanhLap());			
			savedQuanLyCho.setMaDonViQuanLy(quanLyCho.getMaDonViQuanLy());
			savedQuanLyCho.setMoTa(quanLyCho.getMoTa());
			savedQuanLyCho.setDienTich(quanLyCho.getDienTich());
			savedQuanLyCho.setDienTichNhaChoChinh(quanLyCho.getDienTichNhaChoChinh());
			
			savedQuanLyCho.setSoKiot(quanLyCho.getSoKiot());
			savedQuanLyCho.setSoHoKDThuongXuyen(quanLyCho.getSoHoKDThuongXuyen());
			savedQuanLyCho.setSoHoKDLuuDong(quanLyCho.getSoHoKDLuuDong());
			
			savedQuanLyCho.setMatHang(quanLyCho.getMatHang());
			savedQuanLyCho.setNguonHang(quanLyCho.getNguonHang());
			savedQuanLyCho.setThoiGianHD(quanLyCho.getThoiGianHD());
			savedQuanLyCho.setvSATTP(quanLyCho.getvSATTP());			
			savedQuanLyCho.setaNTT(quanLyCho.getaNTT());			
			savedQuanLyCho.setTongThu(quanLyCho.getTongThu());
			
			savedQuanLyCho.setpCCC(quanLyCho.ispCCC());
			savedQuanLyCho.setDienNuoc(quanLyCho.isDienNuoc());
			savedQuanLyCho.setBaiDoXe(quanLyCho.isBaiDoXe());
			savedQuanLyCho.setDaoTaoNV(quanLyCho.isDaoTaoNV());			
			savedQuanLyCho.setNoiQuyCho(quanLyCho.isNoiQuyCho());
			
			savedQuanLyCho.setGhiChu(quanLyCho.getGhiChu());
			

			quanLyChoRepostory.save(savedQuanLyCho);
			return new ResponseEntity<>(AppConstants.UPDATE_SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.UPDATE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	/////////////////
	// DELETE BY ID
	/////////////////
	@DeleteMapping("/quanlychos/{id}")
	public ResponseEntity<?> deleteQuanLyChoById(@PathVariable("id") Long id) {
		System.out.println("Delete QuanLyCho with ID = " + id + "...");

		try {
			quanLyChoRepostory.deleteById(id);
			return new ResponseEntity<>(AppConstants.DELETE_SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(AppConstants.DELETE_FAILED, HttpStatus.EXPECTATION_FAILED);
		}
	}

	////////////////
	// GET BY ID
	////////////////
	@GetMapping("/quanlychos/{id}")
	public ResponseEntity<?> getQuanLyChoById(@PathVariable("id") Long id) {
		System.out.println("Get QuanLyCho by id...");

		Optional<QuanLyCho> QuanLyChoData = quanLyChoRepostory.findById(id);
		if (QuanLyChoData.isPresent()) {
			return new ResponseEntity<>(QuanLyChoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(AppConstants.NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	////////////////////////////////////////////
	// GET DANH MUC NNKD WITH ALL PARAMETER
	////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	@GetMapping("/quanlychos/")
	public Page<QuanLyCho> getBuiHoaQuanLyChos(@RequestParam(value = "keyword") String keyWord,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "pagesize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
			@RequestParam(value = "properties_sort") String properties_sort,
			@RequestParam(value = "type_sort") String kieu) {
		try {
			System.out.println("Get paging QuanLyChos Bui Hoa...");

			// kiểm tra properties_sort ==> mặc định trả về id
			properties_sort = (properties_sort.isEmpty() || properties_sort.toString() == "") ? "id" : properties_sort;

			// Phan trang PAGEABLE va SORT
			Pageable pageable;
			Sort sort = (kieu.equals("0")) ? Sort.by(properties_sort).descending() : Sort.by(properties_sort).ascending();

			if (pageSize <= 0) { // Truong hợp đặc biệt
				int lstSize = quanLyChoRepostory.findByTenChoContainingIgnoreCase(keyWord.toString()).size();
				pageable = new PageRequest(page - 1, lstSize, sort);
			} else {
				pageable = new PageRequest(page - 1, pageSize, sort);
			}

			// tim kiem noi dung
			Page<QuanLyCho> pagQuanLyCho;
			if (keyWord != "") {
				pagQuanLyCho = quanLyChoRepostory.findByTenChoContainingIgnoreCase(keyWord.toString(), pageable);
			} else {
				pagQuanLyCho = pagRepostory.findAll(pageable);
			}
			return pagQuanLyCho;

		} catch (Exception e) {
			System.out.println("ERROR : " + e.toString());
			return null;
		}

	}

	

}
