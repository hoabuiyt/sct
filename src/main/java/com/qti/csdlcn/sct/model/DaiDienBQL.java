package com.qti.csdlcn.sct.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cho_daidienbql")
public class DaiDienBQL implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;	
	
	@Column(name = "idcho")
	private Long idCho;	
	
	@Column(nullable = false, name ="hoten")
	private String hoTen;
	
	@Column(nullable = false, name ="chucvu")
	private String chucVu;
	
	@Column(nullable = false, name ="dienthoai")
	private String dienThoai;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCho() {
		return idCho;
	}
	public void setIdCho(Long idCho) {
		this.idCho = idCho;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public String getDienThoai() {
		return dienThoai;
	}
	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}
	
	
}
