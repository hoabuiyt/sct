package com.qti.csdlcn.sct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "xd_cuahangxangdau")
public class CuaHangXangDau implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenCuaHang() {
		return tenCuaHang;
	}
	public void setTenCuaHang(String tenCuaHang) {
		this.tenCuaHang = tenCuaHang;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getGiamDoc() {
		return giamDoc;
	}
	public void setGiamDoc(String giamDoc) {
		this.giamDoc = giamDoc;
	}
	public Long getIdDaiLy() {
		return idDaiLy;
	}
	public void setIdDaiLy(Long idDaiLy) {
		this.idDaiLy = idDaiLy;
	}
	public String getDienThoai() {
		return dienThoai;
	}
	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}
	public String getHdDaiLy() {
		return hdDaiLy;
	}
	public void setHdDaiLy(String hdDaiLy) {
		this.hdDaiLy = hdDaiLy;
	}
	public String getMaTinh() {
		return maTinh;
	}
	public void setMaTinh(String maTinh) {
		this.maTinh = maTinh;
	}
	public String getMaHuyen() {
		return maHuyen;
	}
	public void setMaHuyen(String maHuyen) {
		this.maHuyen = maHuyen;
	}
	public String getMaXa() {
		return maXa;
	}
	public void setMaXa(String maXa) {
		this.maXa = maXa;
	}
	public Boolean getIsHoatDong() {
		return isHoatDong;
	}
	public void setIsHoatDong(Boolean isHoatDong) {
		this.isHoatDong = isHoatDong;
	}
	@Column(nullable = false, name="tencuahang")
	private String tenCuaHang;
	@Column(name = "diachi")
	private String diaChi;
	@Column(name = "giamdoc")
	private String giamDoc;
	@Column(name = "iddaily")
	private Long idDaiLy;
	@Column(name = "dienthoai")
	private String dienThoai;
	@Column(name = "hddaily")
	private String hdDaiLy;
	@Column(name = "matinh")
	private String maTinh;
	@Column(name = "mahuyen")
	private String maHuyen;
	@Column(name = "maxa")
	private String maXa;
	@Column(name = "ishoatdong")
	private Boolean isHoatDong;
		

}
