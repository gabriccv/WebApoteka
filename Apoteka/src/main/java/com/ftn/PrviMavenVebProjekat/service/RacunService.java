package com.ftn.PrviMavenVebProjekat.service;

import java.util.ArrayList;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Racun;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;

public interface RacunService {
	int kupovina(Racun racun);
	ArrayList <Racun> findAll();
	int sacuvajStavku(StavkaRacuna stavka);
	List<Racun> findByUser(Long id);
}
