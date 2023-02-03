package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.KorisnikDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Service
public class DatabaseKorisnikServiceImpl implements KorisnikService{

	@Autowired
	private KorisnikDAO korisnikDAO;
	
	@Override
	public Korisnik findOneById(Long id) {
		return korisnikDAO.findOne(id);
	}

	@Override
	public Korisnik findOne(String email) {
		return korisnikDAO.findOne(email);
	}

	@Override
	public Korisnik findOne(String email, String lozinka) {
		return korisnikDAO.findOne(email, lozinka);
	}

	@Override
	public List<Korisnik> findAll() {
		return korisnikDAO.findAll();
	}

	@Override
	public Korisnik save(Korisnik korisnik) {
		korisnikDAO.save(korisnik);
		return korisnik;
	}

	@Override
	public Korisnik update(Korisnik korisnik) {
		korisnikDAO.update(korisnik);
		return korisnik;
	}

	@Override
	public Korisnik delete(Long id) {
		Korisnik korisnik = korisnikDAO.findOne(id);
		korisnikDAO.delete(id);
		return korisnik;
	}

}
