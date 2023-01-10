package com.ftn.PrviMavenVebProjekat.dao;

import java.time.LocalDate;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Autor;
import com.ftn.PrviMavenVebProjekat.model.Knjiga;

public interface KnjigaDAO {
	
	public Knjiga findOne(Long id);

	public List<Knjiga> findAll();
	
	public List<Knjiga> findAll(LocalDate datumOd, LocalDate datumDo);
	
	public int findCount(LocalDate datumOd, LocalDate datumDo);
	
	public int findCount(LocalDate datumOd, LocalDate datumDo, Autor autor);

	public int save(Knjiga knjiga);

	public int update(Knjiga knjiga);

	public int delete(Long id);
}
