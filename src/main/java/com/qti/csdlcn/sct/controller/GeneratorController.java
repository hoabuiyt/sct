package com.qti.csdlcn.sct.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.qti.csdlcn.sct.model.CoSoVSATTP;
import com.qti.csdlcn.sct.model.DaiDienBQL;
import com.qti.csdlcn.sct.model.DanhMucNNKD;
import com.qti.csdlcn.sct.model.HangCho;
import com.qti.csdlcn.sct.model.LoaiCho;
import com.qti.csdlcn.sct.model.QuanLyCho;
import com.qti.csdlcn.sct.model.TinhTrangCho;
import com.qti.csdlcn.sct.repository.CoSoVSATTPRepository;
import com.qti.csdlcn.sct.repository.DaiDienBQLRepository;
import com.qti.csdlcn.sct.repository.DanhMucNNKDRepository;
import com.qti.csdlcn.sct.repository.HangChoRepository;
import com.qti.csdlcn.sct.repository.LoaiChoRepository;
import com.qti.csdlcn.sct.repository.QuanLyChoRepository;
import com.qti.csdlcn.sct.repository.TinhTrangChoRepository;


@CrossOrigin
@RestController
@RequestMapping("/api")
@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
public class GeneratorController {
	
	@Autowired
	private CoSoVSATTPRepository coSoVSATTPRepostory;

	@Autowired
	private LoaiChoRepository loaiChoRepostory;
	
	@Autowired
	private HangChoRepository hangChoRepostory;
	
	@Autowired
	private TinhTrangChoRepository tinhTrangChoRepostory;
	
	@Autowired
	private DaiDienBQLRepository daiDienBQLRepostory;
	
	@Autowired
	private QuanLyChoRepository quanLyChoRepostory;
	
	@Autowired
	private DanhMucNNKDRepository dmnnkdRepostory;


	/* =============GEN CO SO VS ATTP EXAMPLE============== */
	@GetMapping("/gen/gencoso")
	public String genCSVSATTP(@RequestParam(value = "n") Integer n) {
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
			DanhMucNNKD nnkdUpdate = dmnnkdRepostory.findByid(x);
			cs.setDanhMucNNKD(nnkdUpdate);

			coSoVSATTPRepostory.save(cs);
		}
		return "OK";
	}
	
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
	
	/* =============GEN CO SO VS ATTP EXAMPLE============== */
	@GetMapping("/gen/genquanlycho")
	public String genQuanLyCHo(@RequestParam(value = "n") Integer n) {
		Faker faker = new Faker();
		n = ((n != null) ? n : 1000);
		for (int i = 1; i < n; i++) {
			String hero = faker.superhero().name();
			//String name = faker.name().fullName();

			QuanLyCho cs = new QuanLyCho();
			cs.setTenCho(hero);			

			long x = 1 + (int) (Math.random() * 5);
			cs.setIdLoai(x);

			quanLyChoRepostory.save(cs);
		}
		return "OK";
	}
}
