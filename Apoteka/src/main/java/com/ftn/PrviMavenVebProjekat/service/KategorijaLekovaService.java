package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;

public interface KategorijaLekovaService {
	KategorijaLekova findOne(Long id); 
	List<KategorijaLekova> findAll(); 
	KategorijaLekova save(KategorijaLekova kategorijaLekova); 
	KategorijaLekova update(KategorijaLekova kategorijaLekova); 
	KategorijaLekova delete(Long id); 
}
