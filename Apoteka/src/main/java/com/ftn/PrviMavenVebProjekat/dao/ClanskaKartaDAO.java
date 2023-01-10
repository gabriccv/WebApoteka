package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.ClanskaKarta;

public interface ClanskaKartaDAO {
	public ClanskaKarta findOne(Long id);
	
	public ClanskaKarta findOneByRegistarskiBroj(String registarskiBroj);

	public List<ClanskaKarta> findAll();

	public int save(ClanskaKarta clanskaKarta);

	public int update(ClanskaKarta clanskaKarta);

	public int delete(Long id);
}
