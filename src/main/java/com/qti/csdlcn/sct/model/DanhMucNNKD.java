package com.qti.csdlcn.sct.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "attp_danhmucnnkd")
public class DanhMucNNKD implements Serializable{
	private static final long serialVersionUID = 4910225916550731446L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "tendanhmuc")
	private String tenDanhMuc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenDanhMuc() {
		return tenDanhMuc;
	}

	public void setTenDanhMuc(String tenDanhMuc) {
		this.tenDanhMuc = tenDanhMuc;
	}
	
	public DanhMucNNKD(String _tenDanhMuc) {
		this.tenDanhMuc = _tenDanhMuc;
	}

	public DanhMucNNKD() {
	}
	
	
}
