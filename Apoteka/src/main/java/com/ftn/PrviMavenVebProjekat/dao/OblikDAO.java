package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Oblik;

public interface OblikDAO {
	
	public Oblik findOne(Long id);

	public List<Oblik> findAll();

	public int save(Oblik oblik);

	public int update(Oblik oblik);

	public int delete(Long id);
}
