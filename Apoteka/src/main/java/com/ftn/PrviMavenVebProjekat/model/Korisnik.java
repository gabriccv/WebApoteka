package com.ftn.PrviMavenVebProjekat.model;

import java.sql.Date;

public class Korisnik {
	
	private Long id;
	private String korisnickoIme;
	private String lozinka;
	private String email;
	private String ime;
	private String prezime;
	private Date datumRodjenja;
	private String adresa;
	private String brojTelefona;
	private Date datumIVremeRegistracije;
	private Uloga uloga;
	
	public Korisnik() {}
	

	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime, Date datumRodjenja, String adresa,
			String brojTelefona, Date datumIVremeRegistracije,Uloga uloga) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}
	
	public Korisnik(String korisnickoIme, String lozinka, String email, String ime, String prezime, Date datumRodjenja, String adresa,
			String brojTelefona, Date datumIVremeRegistracije) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		
	}
	
	public Korisnik(Long id, String korisnickoIme, String lozinka, String email, String ime, String prezime, Date datumRodjenja, String adresa,
			String brojTelefona, Date datumIVremeRegistracije,Uloga uloga) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}


	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}


	public Date getDatumRodjenja() {
		return datumRodjenja;
	}


	public void setDatumRodjenja(Date datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}


	public String getAdresa() {
		return adresa;
	}


	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}


	public String getBrojTelefona() {
		return brojTelefona;
	}


	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}


	public Date getDatumIVremeRegistracije() {
		return datumIVremeRegistracije;
	}


	public void setDatumIVremeRegistracije(Date datumIVremeRegistracije) {
		this.datumIVremeRegistracije = datumIVremeRegistracije;
	}


	public Uloga getUloga() {
		return uloga;
	}


	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}


	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getIme() { return ime; }

	public void setIme(String ime) { this.ime = ime; }

	public String getPrezime() { return prezime; }

	public void setPrezime(String prezime) { this.prezime = prezime; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	@Override
	public String toString() {
		return this.ime + " " + this.prezime + " (" + this.email + ")";
	}
	
	public String toFileString() {
		return this.getId() + ";" + this.getKorisnickoIme() + ";" + this.getLozinka() + ";" + 
				this.getEmail() + ";" + this.getIme() + ";" + this.getPrezime() + ";" + this.getDatumRodjenja() + ";" + this.getAdresa()
				+ ";" + this.getDatumIVremeRegistracije() + ";" + this.getUloga();
	}
	
}
