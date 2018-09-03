package com.qti.csdlcn.sct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.qti.csdlcn.sct.model.DaiDienBQL;
import com.qti.csdlcn.sct.model.HangCho;
import com.qti.csdlcn.sct.model.LoaiCho;
import com.qti.csdlcn.sct.model.TinhTrangCho;
import com.qti.csdlcn.sct.repository.DaiDienBQLRepository;
import com.qti.csdlcn.sct.repository.HangChoRepository;
import com.qti.csdlcn.sct.repository.LoaiChoRepository;
import com.qti.csdlcn.sct.repository.TinhTrangChoRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class GeneratorController {

	@Autowired
	private LoaiChoRepository loaiChoRepostory;
	
	@Autowired
	private HangChoRepository hangChoRepostory;
	
	@Autowired
	private TinhTrangChoRepository tinhTrangChoRepostory;
	
	@Autowired
	private DaiDienBQLRepository daiDienBQLRepostory;


	
	/* =============GEN LOAI CHO EXAMPLE============== */
	@GetMapping("/gen/genloaicho")
	public String genLoaiCho() {
		System.out.println("Gen Danh Muc LoaiCho");
		for (Integer i = 1; i < 10; i++) {
			LoaiCho nnkd = new LoaiCho();
			System.out.println("LoaiCho with ID = " + i);
			nnkd.setTenLoai("Chợ Loại " + String.valueOf(i));
			loaiChoRepostory.save(nnkd);
		}
		return "OK";
	}	

	/* =============GEN HANG CHO EXAMPLE============== */
	@GetMapping("/gen/genhangcho")
	public String genHangCho() {
		System.out.println("Gen Danh Muc HangCho");
		for (Integer i = 1; i < 10; i++) {
			HangCho nnkd = new HangCho();
			System.out.println("HangCho with ID = " + i);
			nnkd.setTenHang("Chợ Hạng " + String.valueOf(i));
			hangChoRepostory.save(nnkd);
		}
		return "OK";
	}
	
	/* =============GEN TINH TRANG CHO EXAMPLE============== */
	@GetMapping("/gen/gentinhtrangcho")
	public String genTinhTrangCho() {
		System.out.println("Gen Tinh Trang Cho");
		for (Integer i = 1; i < 10; i++) {
			TinhTrangCho tinhTrangCho =  new TinhTrangCho();
			System.out.println("Tinh Trang Cho with ID = " + i);
			tinhTrangCho.setTenTinhTrang("Tình trạng chợ " + String.valueOf(i));
			tinhTrangChoRepostory.save(tinhTrangCho);
		}
		return "OK";
	}
	
	/* =============GEN CO SO VS ATTP EXAMPLE============== */
	@GetMapping("/gen/gendaidienbql")
	public String genDaiDienBQL(@RequestParam(value = "n") Integer n) {
		Faker faker = new Faker();
		n = ((n != null) ? n : 1000);
		for (int i = 1; i < n; i++) {
			String hero = faker.superhero().name();
			String name = faker.name().fullName();

			DaiDienBQL cs = new DaiDienBQL();
			cs.setHoTen(name);
			cs.setChucVu(hero);
			cs.setDienThoai("0" + (Math.random() * 9999999));

			long x = 1 + (int) (Math.random() * 5);
			cs.setIdCho(x);

			daiDienBQLRepostory.save(cs);
		}
		return "OK";
	}
}
