package com.ftn.PrviMavenVebProjekat.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Knjiga {
	
	private Long id;
	private String naziv;
	private String registarskiBrojPrimerka;	// 
	private String jezik;
	private int brojStranica;
	private boolean izdata;
	private LocalDate datum;
	private List<Autor> autori;
	
	public Knjiga() {
		this.setAutori(new ArrayList<Autor>());
	}

	public Knjiga(Long id, String naziv, String registarskiBrojPrimerka, 
			String jezik, int brojStranica) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.registarskiBrojPrimerka = registarskiBrojPrimerka;
		this.jezik = jezik;
		this.brojStranica = brojStranica;
		this.izdata = false;
		this.setAutori(new ArrayList<Autor>());
	}
	
	public Knjiga(String naziv, String registarskiBrojPrimerka, 
			String jezik, int brojStranica, LocalDate datum) {
		super();
		this.naziv = naziv;
		this.registarskiBrojPrimerka = registarskiBrojPrimerka;
		this.jezik = jezik;
		this.brojStranica = brojStranica;
		this.izdata = false;
		this.datum = datum;
		this.setAutori(new ArrayList<Autor>());
	}
	
	public Knjiga(Long id, String naziv, String registarskiBrojPrimerka, 
			String jezik, int brojStranica, boolean izdata, LocalDate datum) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.registarskiBrojPrimerka = registarskiBrojPrimerka;
		this.jezik = jezik;
		this.brojStranica = brojStranica;
		this.izdata = izdata;
		this.datum = datum;
		this.setAutori(new ArrayList<Autor>());
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getNaziv() { return naziv; }

	public void setNaziv(String naziv) { this.naziv = naziv; }

	public String getRegistarskiBrojPrimerka() { return registarskiBrojPrimerka; }

	public void setRegistarskiBrojPrimerka(String registarskiBrojPrimerka) { this.registarskiBrojPrimerka = registarskiBrojPrimerka; }

	public String getJezik() { return jezik; }

	public void setJezik(String jezik) { this.jezik = jezik;	}

	public int getBrojStranica() { return brojStranica; }

	public void setBrojStranica(int brojStranica) {	this.brojStranica = brojStranica;	}

	public boolean isIzdata() { return izdata; }

	public void setIzdata(boolean izdata) { this.izdata = izdata; }

	public List<Autor> getAutori() {
		return autori;
	}

	public void setAutori(List<Autor> autori) {
		this.autori = autori;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return this.getId() + ";" + this.getNaziv() + ";" + this.getRegistarskiBrojPrimerka()
		 + ";" + this.getJezik() + ";" + this.getBrojStranica() + ";" + this.izdata + ";" +
				this.getAutori();
	}
	
}
