package com.ftn.PrviMavenVebProjekat.model;

public class Oblik{
	private Long id;
	private String naziv;
	
	
	
	public Oblik() {
		
	}

	public Oblik(Long id, String naziv) {
		super();
		this.id = id;
		this.naziv = naziv;
		
		
	}

	

	public Oblik(String naziv) {
		super();
		this.naziv = naziv;
		
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	@Override
	public String toString() {
		return this.id+";"+this.naziv;
		
	
	}
}