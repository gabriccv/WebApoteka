package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.KategorijaLekovaDAO;
import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.service.KategorijaLekovaService;


@Service
public class DatabaseKategorijaLekovaServiceImpl implements KategorijaLekovaService {

	@Autowired
	private KategorijaLekovaDAO kategroijaLekovaDAO;
	
	@Override
	public KategorijaLekova findOne(Long id) {
		return kategroijaLekovaDAO.findOne(id);
	}

	@Override
	public List<KategorijaLekova> findAll() {
		return kategroijaLekovaDAO.findAll();
	}

	@Override
	public KategorijaLekova save(KategorijaLekova kategorijaLekova) {
		kategroijaLekovaDAO.save(kategorijaLekova);
		return kategorijaLekova;
	}

	@Override
	public KategorijaLekova update(KategorijaLekova kategorijaLekova) {
		kategroijaLekovaDAO.update(kategorijaLekova);
		return kategorijaLekova;
	}

	@Override
	public KategorijaLekova delete(Long id) {
		KategorijaLekova kategorijaLekova = kategroijaLekovaDAO.findOne(id);
		kategroijaLekovaDAO.delete(id);
		return kategorijaLekova;
	}

}
