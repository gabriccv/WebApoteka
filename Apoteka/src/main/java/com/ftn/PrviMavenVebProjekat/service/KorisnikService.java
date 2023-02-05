package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;

public interface KorisnikService {
	Korisnik findOneById(Long id);
	Korisnik findOne(String email); 
	Korisnik findOne(String email, String lozinka);
	List<Korisnik> findAll(); 
	List<Korisnik> findByQuery(String naziv,String uloga);
	Korisnik save(Korisnik korisnik); 
	Korisnik update(Korisnik korisnik); 
	Korisnik delete(Long id); 
}
