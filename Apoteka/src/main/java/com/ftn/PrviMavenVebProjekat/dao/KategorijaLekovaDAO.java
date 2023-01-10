package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;

public interface KategorijaLekovaDAO {
	
	public KategorijaLekova findOne(Long id);

	public List<KategorijaLekova> findAll();

	public int save(KategorijaLekova kategorijaLekova);

	public int update(KategorijaLekova kategorijaLekova);

	public int delete(Long id);
}
