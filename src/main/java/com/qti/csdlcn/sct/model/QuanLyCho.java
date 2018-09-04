package com.qti.csdlcn.sct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cho_quanlycho")
public class QuanLyCho implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;	
	
	@Column(name = "idhang")
	private Long idHang;
	
	@Column(name = "idloai")
	private Long idLoai;
	
	@Column(name = "idtinhtrang")
	private Long idTinhTrang;
	
	@Column(nullable = false, name ="tencho")
	private String tenCho;
	
	@Column(name = "diachi")
	private String diaChi;

	@Column(name = "maxa")
	private String maXa;

	@Column(name = "mahuyen")
	private String maHuyen;

	@Column(name = "matinh")
	private String maTinh;
	
	@Column(name = "vontrunguong")
	private String vonTrungUong;
	
	@Column(name = "vondiaphuong")
	private String vonDiaPhuong;
	
	@Column(name = "vonkhac")
	private String vonKhac;
	
	@Column(name = "ngaythanhlap")
	private Long ngayThanhLap;
	
	@Column(name = "madonviquanly")
	private String maDonViQuanLy;
	
	@Column(name = "mota")
	private String moTa;
	
	@Column(name = "dientich")
	private double dienTich;
	
	@Column(name = "dientichnhachochinh")
	private double dienTichNhaChoChinh;
	
	@Column(name = "sokiot")
	private int soKiot;
	
	@Column(name = "sohokdthuongxuyen")
	private int soHoKDThuongXuyen;
	
	@Column(name = "sohokdluudong")
	private int soHoKDLuuDong;
	
	@Column(name = "mathang")
	private String matHang;
	
	@Column(name = "nguonhang")
	private String nguonHang;
	
	@Column(name = "thoigianhd")
	private String thoiGianHD;
	
	@Column(name = "vsattp")
	private String vSATTP;
	
	@Column(name = "antt")
	private String aNTT;
	
	@Column(name = "tongthu")
	private String tongThu;
	
	@Column(name = "pccc")
	private boolean pCCC;
	
	@Column(name = "diennuoc")
	private boolean dienNuoc;
	
	@Column(name = "baidoxe")
	private boolean baiDoXe;
	
	@Column(name = "daotaonv")
	private boolean daoTaoNV;
	
	@Column(name = "noiquycho")
	private boolean noiQuyCho;
	
	@Column(name = "ghichu")
	private String ghiChu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdHang() {
		return idHang;
	}

	public void setIdHang(Long idHang) {
		this.idHang = idHang;
	}

	public Long getIdLoai() {
		return idLoai;
	}

	public void setIdLoai(Long idLoai) {
		this.idLoai = idLoai;
	}

	public Long getIdTinhTrang() {
		return idTinhTrang;
	}

	public void setIdTinhTrang(Long idTinhTrang) {
		this.idTinhTrang = idTinhTrang;
	}

	public String getTenCho() {
		return tenCho;
	}

	public void setTenCho(String tenCho) {
		this.tenCho = tenCho;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getMaXa() {
		return maXa;
	}

	public void setMaXa(String maXa) {
		this.maXa = maXa;
	}

	public String getMaHuyen() {
		return maHuyen;
	}

	public void setMaHuyen(String maHuyen) {
		this.maHuyen = maHuyen;
	}

	public String getMaTinh() {
		return maTinh;
	}

	public void setMaTinh(String maTinh) {
		this.maTinh = maTinh;
	}

	public String getVonTrungUong() {
		return vonTrungUong;
	}

	public void setVonTrungUong(String vonTrungUong) {
		this.vonTrungUong = vonTrungUong;
	}

	public String getVonDiaPhuong() {
		return vonDiaPhuong;
	}

	public void setVonDiaPhuong(String vonDiaPhuong) {
		this.vonDiaPhuong = vonDiaPhuong;
	}

	public String getVonKhac() {
		return vonKhac;
	}

	public void setVonKhac(String vonKhac) {
		this.vonKhac = vonKhac;
	}

	public Long getNgayThanhLap() {
		return ngayThanhLap;
	}

	public void setNgayThanhLap(Long ngayThanhLap) {
		this.ngayThanhLap = ngayThanhLap;
	}

	public String getMaDonViQuanLy() {
		return maDonViQuanLy;
	}

	public void setMaDonViQuanLy(String maDonViQuanLy) {
		this.maDonViQuanLy = maDonViQuanLy;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public double getDienTich() {
		return dienTich;
	}

	public void setDienTich(double dienTich) {
		this.dienTich = dienTich;
	}

	public double getDienTichNhaChoChinh() {
		return dienTichNhaChoChinh;
	}

	public void setDienTichNhaChoChinh(double dienTichNhaChoChinh) {
		this.dienTichNhaChoChinh = dienTichNhaChoChinh;
	}

	public int getSoKiot() {
		return soKiot;
	}

	public void setSoKiot(int soKiot) {
		this.soKiot = soKiot;
	}

	public int getSoHoKDThuongXuyen() {
		return soHoKDThuongXuyen;
	}

	public void setSoHoKDThuongXuyen(int soHoKDThuongXuyen) {
		this.soHoKDThuongXuyen = soHoKDThuongXuyen;
	}

	public int getSoHoKDLuuDong() {
		return soHoKDLuuDong;
	}

	public void setSoHoKDLuuDong(int soHoKDLuuDong) {
		this.soHoKDLuuDong = soHoKDLuuDong;
	}

	public String getMatHang() {
		return matHang;
	}

	public void setMatHang(String matHang) {
		this.matHang = matHang;
	}

	public String getNguonHang() {
		return nguonHang;
	}

	public void setNguonHang(String nguonHang) {
		this.nguonHang = nguonHang;
	}

	public String getThoiGianHD() {
		return thoiGianHD;
	}

	public void setThoiGianHD(String thoiGianHD) {
		this.thoiGianHD = thoiGianHD;
	}

	public String getvSATTP() {
		return vSATTP;
	}

	public void setvSATTP(String vSATTP) {
		this.vSATTP = vSATTP;
	}

	public String getaNTT() {
		return aNTT;
	}

	public void setaNTT(String aNTT) {
		this.aNTT = aNTT;
	}

	public String getTongThu() {
		return tongThu;
	}

	public void setTongThu(String tongThu) {
		this.tongThu = tongThu;
	}

	public boolean ispCCC() {
		return pCCC;
	}

	public void setpCCC(boolean pCCC) {
		this.pCCC = pCCC;
	}

	public boolean isDienNuoc() {
		return dienNuoc;
	}

	public void setDienNuoc(boolean dienNuoc) {
		this.dienNuoc = dienNuoc;
	}

	public boolean isBaiDoXe() {
		return baiDoXe;
	}

	public void setBaiDoXe(boolean baiDoXe) {
		this.baiDoXe = baiDoXe;
	}

	public boolean isDaoTaoNV() {
		return daoTaoNV;
	}

	public void setDaoTaoNV(boolean daoTaoNV) {
		this.daoTaoNV = daoTaoNV;
	}

	public boolean isNoiQuyCho() {
		return noiQuyCho;
	}

	public void setNoiQuyCho(boolean noiQuyCho) {
		this.noiQuyCho = noiQuyCho;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	
	
	
}
