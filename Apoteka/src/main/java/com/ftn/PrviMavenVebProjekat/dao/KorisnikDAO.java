package com.ftn.PrviMavenVebProjekat.dao;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Lek;

public interface KorisnikDAO {
	public Korisnik findOne(Long id);
	
	public Korisnik findOne(String email); 
	
	public Korisnik findOne(String email, String lozinka);

	public List<Korisnik> findAll();
	
	public List<Korisnik> findByQuery(String naziv,String uloga);

	public int save(Korisnik korisnik);

	public int update(Korisnik korisnik);

	public int delete(Long id);
	
}
