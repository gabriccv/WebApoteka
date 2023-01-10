package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ftn.PrviMavenVebProjekat.model.Autor;

public interface AutorDAO {
	public Autor findOne(Long id);

	public List<Autor> findAll();

	public int save(Autor autor);

	public int update(Autor autor);

	public int delete(Long id);
}
