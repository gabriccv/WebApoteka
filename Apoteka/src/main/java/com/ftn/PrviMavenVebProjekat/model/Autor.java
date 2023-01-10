package com.ftn.PrviMavenVebProjekat.model;

import java.util.ArrayList;
import java.util.List;

public class Autor {
	private Long id;
	private String ime;
	private String prezime;
	private List<Knjiga> knjige;
	
	public Autor() {
		this.knjige = new ArrayList<Knjiga>();
	}
	
	public Autor(Long id, String ime, String prezime) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.knjige = new ArrayList<Knjiga>();
	}
	
	public Autor(String ime, String prezime, List<Knjiga> knjige) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.knjige = knjige;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public List<Knjiga> getKnjige() {
		return knjige;
	}
	public void setKnjige(List<Knjiga> knjige) {
		this.knjige = knjige;
	}

	@Override
	public String toString() {
		return "Autor{" +
				"id=" + id +
				", ime='" + ime + '\'' +
				", prezime='" + prezime + '\'' +
				'}';
	}
}
