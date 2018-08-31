package com.qti.csdlcn.sct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "xd_dailyxangdau")
public class DaiLyXangDau implements Serializable {
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
	public String getTenDaiLy() {
		return tenDaiLy;
	}
	public void setTenDaiLy(String tenDaiLy) {
		this.tenDaiLy = tenDaiLy;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getDienThoai() {
		return dienThoai;
	}
	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Boolean getIsHoatDong() {
		return isHoatDong;
	}
	public void setIsHoatDong(Boolean isHoatDong) {
		this.isHoatDong = isHoatDong;
	}
	public Boolean getIsDauMoi() {
		return isDauMoi;
	}
	public void setIsDauMoi(Boolean isDauMoi) {
		this.isDauMoi = isDauMoi;
	}
	@Column(nullable = false, name ="tendaily")
	private String tenDaiLy;
	@Column(name = "diachi")
	private String diaChi;
	@Column(name = "email")
	private String email;
	@Column(name = "fax")
	private String fax;
	@Column(name = "dienthoai")
	private String dienThoai;
	@Column(name = "website")
	private String website;
	@Column(name = "ishoatdong")
	private Boolean isHoatDong;
	@Column(name = "isdaumoi")
	private Boolean isDauMoi;
	

}
