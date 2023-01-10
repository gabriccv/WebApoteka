package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.PrviMavenVebProjekat.dao.AutorDAO;
import com.ftn.PrviMavenVebProjekat.model.Autor;
import com.ftn.PrviMavenVebProjekat.service.AutorService;

public class DatabaseAutorServiceImpl implements AutorService {

	@Autowired
	private AutorDAO autorDAO;
	
	@Override
	public Autor findOne(Long id) {
		return autorDAO.findOne(id);
	}

	@Override
	public List<Autor> findAll() {
		return autorDAO.findAll();
	}

	@Override
	public Autor save(Autor autor) {
		autorDAO.save(autor);
		return autor;
	}

	@Override
	public Autor update(Autor autor) {
		autorDAO.update(autor);
		return autor;
	}

	@Override
	public Autor delete(Long id) {
		Autor autor = autorDAO.findOne(id);
		autorDAO.delete(id);
		return autor;
	}

}
