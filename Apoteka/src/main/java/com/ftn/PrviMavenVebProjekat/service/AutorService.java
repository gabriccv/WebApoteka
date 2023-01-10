package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Autor;

public interface AutorService {
	public Autor findOne(Long id);

	public List<Autor> findAll();

	public Autor save(Autor autor);

	public Autor update(Autor autor);

	public Autor delete(Long id);
}
