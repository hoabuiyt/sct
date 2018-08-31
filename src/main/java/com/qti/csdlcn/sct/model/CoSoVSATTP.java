package com.qti.csdlcn.sct.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "attp_cosovsattp")
public class CoSoVSATTP implements Serializable {

	private static final long serialVersionUID = 4910225916550731446L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "tencoso")
	private String tenCoSo;

	@Column(name = "tenchucoso")
	private String tenChuCoSo;

	@Column(name = "diachicoso")
	private String diaChiCoSo;

	@Column(name = "maxa")
	private String maXa;

	@Column(name = "mahuyen")
	private String maHuyen;

	@Column(name = "matinh")
	private String maTinh;

	@Column(name = "iddanhmuc")
	private Long idDanhMuc;

	@Column(name = "sogiaycn")
	private String soGiayCN;

	@Column(name = "ngaycapcn")
	private Long ngayCapCN;

	@Column(name = "ghichu")
	private String ghiChu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenCoSo() {
		return tenCoSo;
	}

	public void setTenCoSo(String tenCoSo) {
		this.tenCoSo = tenCoSo;
	}

	public String getTenChuCoSo() {
		return tenChuCoSo;
	}

	public void setTenChuCoSo(String tenChuCoSo) {
		this.tenChuCoSo = tenChuCoSo;
	}

	public String getDiaChiCoSo() {
		return diaChiCoSo;
	}

	public void setDiaChiCoSo(String diaChiCoSo) {
		this.diaChiCoSo = diaChiCoSo;
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

	public Long getIdDanhMuc() {
		return idDanhMuc;
	}

	public void setIdDanhMuc(Long idDanhMuc) {
		this.idDanhMuc = idDanhMuc;
	}

	public String getSoGiayCN() {
		return soGiayCN;
	}

	public void setSoGiayCN(String soGiayCN) {
		this.soGiayCN = soGiayCN;
	}

	public Long getNgayCapCN() {
		return ngayCapCN;
	}

	public void setNgayCapCN(Long ngayCapCN) {
		this.ngayCapCN = ngayCapCN;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	
	
	
}
