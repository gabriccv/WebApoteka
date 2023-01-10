package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.KnjigaDAO;
import com.ftn.PrviMavenVebProjekat.model.Knjiga;
import com.ftn.PrviMavenVebProjekat.service.KnjigaService;

@Service
public class DatabaseKnjigaService implements KnjigaService {
	
	@Autowired
	private KnjigaDAO knjigaDAO;

	@Override
	public Knjiga findOne(Long id) {
		return knjigaDAO.findOne(id);
	}

	@Override
	public List<Knjiga> findAll() {
		return knjigaDAO.findAll();
	}

	@Override
	public Knjiga save(Knjiga knjiga) {
		knjigaDAO.save(knjiga);
		return knjiga;
	}

	@Override
	public Knjiga update(Knjiga knjiga) {
		knjigaDAO.update(knjiga);
		return knjiga;
	}

	@Override
	public Knjiga delete(Long id) {
		Knjiga knjiga = knjigaDAO.findOne(id);
		if(knjiga != null) {
			knjigaDAO.delete(id);
		}
		return knjiga;
	}

}
