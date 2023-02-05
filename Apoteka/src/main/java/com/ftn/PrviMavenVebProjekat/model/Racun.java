package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Date;
import java.util.ArrayList;

public class Racun{
	private Long id;
	private Korisnik korisnik;
	private double cena;
	private Date datum;
	private ArrayList<StavkaRacuna> stavke;
	
	
	public Racun() {
		
	}



	public Racun(Long id, Korisnik korisnik, double cena, Date datum) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.cena = cena;
		this.datum = datum;
	}

	public Racun(Korisnik korisnik, double cena, Date datum) {
		super();
	
		this.korisnik = korisnik;
		this.cena = cena;
		this.datum = datum;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	

	public Korisnik getKorisnik() {
		return korisnik;
	}



	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}



	public double getCena() {
		return cena;
	}



	public void setCena(double cena) {
		this.cena = cena;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}



	public Date getDatum() {
		return datum;
	}



	public void setDatum(Date datum) {
		this.datum = datum;
	}

	
	public ArrayList<StavkaRacuna> getStavke() {
		return stavke;
	}



	public void setStavke(ArrayList<StavkaRacuna> stavke) {
		this.stavke = stavke;
	}
	
}

