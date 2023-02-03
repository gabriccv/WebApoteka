package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.UlogaDAO;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.UlogaService;




@Service
public class DatabaseUlogaServiceImpl implements UlogaService {

	@Autowired
	private UlogaDAO ulogaDAO;
	
	@Override
	public Uloga findOne(Long id) {
		return ulogaDAO.findOne(id);
	}

	@Override
	public List<Uloga> findAll() {
		return ulogaDAO.findAll();
	}

	@Override
	public Uloga save(Uloga uloga) {
		ulogaDAO.save(uloga);
		return uloga;
	}

	@Override
	public Uloga update(Uloga uloga) {
		ulogaDAO.update(uloga);
		return uloga;
	}

	@Override
	public Uloga delete(Long id) {
		Uloga uloga = ulogaDAO.findOne(id);
		ulogaDAO.delete(id);
		return uloga;
	}

}
