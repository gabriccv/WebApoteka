package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;

public interface LekService {
	Lek findOne(Long id); 
	List<Lek> findAll(); 
	List<Lek> findByQuery(String naziv,String kategorijaLeka,double donjaCena,double gornjaCena, String proizvodjac,String kontraindikacije,
			String opis,String oblik,float prosekOcena);
	 
	Lek save(Lek lek); 
	Lek update(Lek lek); 
	Lek delete(Long id); 
	void smanjivanjeKolicineLeka(StavkaRacuna stavka);
}
