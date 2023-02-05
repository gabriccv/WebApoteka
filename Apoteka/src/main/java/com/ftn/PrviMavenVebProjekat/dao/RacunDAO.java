package com.ftn.PrviMavenVebProjekat.dao;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Racun;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;

public interface RacunDAO {
	

	public List<Racun> findAll();
	public int sacuvajStavku(StavkaRacuna stavka);
	public int kupovina(Racun racun);
	public ArrayList<Racun> findByUser(Long id);
	public ArrayList<StavkaRacuna> izvestaj(Date pocetniDatum, Date krajnjiDatum);

	}
