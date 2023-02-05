package com.ftn.PrviMavenVebProjekat.service.impl;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.RacunDAO;
import com.ftn.PrviMavenVebProjekat.model.Racun;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;
import com.ftn.PrviMavenVebProjekat.service.RacunService;


@Service
public class DatabaseRacunServiceImpl implements RacunService {
	
	@Autowired
	private RacunDAO racunDAO;

	public int kupovina(Racun racun) {
		return racunDAO.kupovina(racun);
		
	}
	public ArrayList <Racun> findAll(){
		return (ArrayList<Racun>) racunDAO.findAll();
		
	};
	public ArrayList<Racun> findByUser(Long id){
		return racunDAO.findByUser(id);
	};
	
	public int sacuvajStavku(StavkaRacuna stavka) {
		return racunDAO.sacuvajStavku(stavka);
	};
	public ArrayList<StavkaRacuna> izvestaj(Date pocetniDatum,Date krajnjiDatum){
		return racunDAO.izvestaj(pocetniDatum,krajnjiDatum);
	};
	
	
	
}
