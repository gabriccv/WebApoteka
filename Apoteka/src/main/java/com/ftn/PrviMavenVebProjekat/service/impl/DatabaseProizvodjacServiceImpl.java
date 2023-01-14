package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.ProizvodjacDAO;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;
import com.ftn.PrviMavenVebProjekat.service.ProizvodjacService;



@Service
public class DatabaseProizvodjacServiceImpl implements ProizvodjacService {

	@Autowired
	private ProizvodjacDAO proizvodjacDAO;
	
	@Override
	public Proizvodjac findOne(Long id) {
		return proizvodjacDAO.findOne(id);
	}

	@Override
	public List<Proizvodjac> findAll() {
		return proizvodjacDAO.findAll();
	}

	@Override
	public Proizvodjac save(Proizvodjac proizvodjac) {
		proizvodjacDAO.save(proizvodjac);
		return proizvodjac;
	}

	@Override
	public Proizvodjac update(Proizvodjac proizvodjac) {
		proizvodjacDAO.update(proizvodjac);
		return proizvodjac;
	}

	@Override
	public Proizvodjac delete(Long id) {
		Proizvodjac proizvodjac = proizvodjacDAO.findOne(id);
		proizvodjacDAO.delete(id);
		return proizvodjac;
	}

}
