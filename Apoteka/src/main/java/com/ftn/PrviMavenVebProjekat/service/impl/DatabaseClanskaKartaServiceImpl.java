package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.ClanskaKartaDAO;
import com.ftn.PrviMavenVebProjekat.model.ClanskaKarta;
import com.ftn.PrviMavenVebProjekat.service.ClanskaKartaService;

@Service
public class DatabaseClanskaKartaServiceImpl implements ClanskaKartaService{

	@Autowired
	private ClanskaKartaDAO clanskaKartaDAO;
	
	@Override
	public ClanskaKarta findOne(Long id) {
		return clanskaKartaDAO.findOne(id);
	}

	@Override
	public List<ClanskaKarta> findAll() {
		return clanskaKartaDAO.findAll();
	}

	@Override
	public ClanskaKarta save(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.save(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta update(ClanskaKarta clanskaKarta) {
		clanskaKartaDAO.update(clanskaKarta);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta delete(Long id) {
		ClanskaKarta clanskaKarta = clanskaKartaDAO.findOne(id);
		clanskaKartaDAO.delete(id);
		return clanskaKarta;
	}

	@Override
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj) {
		return clanskaKartaDAO.findOneByRegistarskiBroj(registarskiBroj);
	}

}
