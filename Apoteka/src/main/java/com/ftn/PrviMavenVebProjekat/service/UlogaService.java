package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Uloga;

public interface UlogaService {
	Uloga findOne(Long id); 
	List<Uloga> findAll(); 
	Uloga save(Uloga uloga); 
	Uloga update(Uloga uloga); 
	Uloga delete(Long id); 
}
