package com.qti.csdlcn.sct.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qti.csdlcn.sct.model.DaiLyXangDau;

public interface PageDaiLyXangDauRepository extends PagingAndSortingRepository<DaiLyXangDau, Long> {
	

}
