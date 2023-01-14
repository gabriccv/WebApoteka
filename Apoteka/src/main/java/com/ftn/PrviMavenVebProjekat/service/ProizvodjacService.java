package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;

public interface ProizvodjacService {
	Proizvodjac findOne(Long id); 
	List<Proizvodjac> findAll(); 
	Proizvodjac save(Proizvodjac proizvodjac); 
	Proizvodjac update(Proizvodjac proizvodjac); 
	Proizvodjac delete(Long id); 
}
