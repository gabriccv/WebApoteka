package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Uloga;


public interface UlogaDAO {
	
	public Uloga findOne(Long id);

	public List<Uloga> findAll();

	public int save(Uloga uloga);

	public int update(Uloga uloga);

	public int delete(Long id);
}
