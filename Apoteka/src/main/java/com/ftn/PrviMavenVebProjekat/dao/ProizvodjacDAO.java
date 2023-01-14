package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;

public interface ProizvodjacDAO {
	
	public Proizvodjac findOne(Long id);

	public List<Proizvodjac> findAll();

	public int save(Proizvodjac proizvodjac);

	public int update(Proizvodjac proizvodjac);

	public int delete(Long id);
}
