package com.ftn.PrviMavenVebProjekat.model;

public class KategorijaLekova{
	private Long id;
	private String naziv;
	private String namena;
	private String opis;
	
	
	public KategorijaLekova() {
		
	}

	public KategorijaLekova(Long id, String naziv, String namena, 
			String opis) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.namena = namena;
		this.opis = opis;
		
	}

	

	public KategorijaLekova(String naziv, String namena, String opis) {
		super();
		this.naziv = naziv;
		this.namena = namena;
		this.opis = opis;
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


	public String getNamena() {
		return namena;
	}


	public void setNamena(String namena) {
		this.namena = namena;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
		
		
	}
	@Override
	public String toString() {
		return this.id+";"+this.naziv+";"+this.namena+";"+this.opis;
		
	
	}
}