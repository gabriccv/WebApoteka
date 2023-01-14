package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.OblikDAO;
import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.service.OblikService;



@Service
public class DatabaseOblikServiceImpl implements OblikService {

	@Autowired
	private OblikDAO oblikDAO;
	
	@Override
	public Oblik findOne(Long id) {
		return oblikDAO.findOne(id);
	}

	@Override
	public List<Oblik> findAll() {
		return oblikDAO.findAll();
	}

	@Override
	public Oblik save(Oblik oblik) {
		oblikDAO.save(oblik);
		return oblik;
	}

	@Override
	public Oblik update(Oblik oblik) {
		oblikDAO.update(oblik);
		return oblik;
	}

	@Override
	public Oblik delete(Long id) {
		Oblik oblik = oblikDAO.findOne(id);
		oblikDAO.delete(id);
		return oblik;
	}

}
