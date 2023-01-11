
package com.ftn.PrviMavenVebProjekat.model;

public class Lek{
	 private Long id;
	 private String naziv;
	 private String sifra;
	 private String opis;
	 private String kontraindikacije;
	 private ObliciLeka oblik;
	 private float prosekOcena;
	 private String slika;
	 private int dostupnaKolicina;
	 private double cena;
	 private Proizvodjac proizvodjac;
	 private KategorijaLekova kategorijaLekova;
	
	
	 public Lek() {
		
	 }


	public Lek(String naziv, String sifra, String opis, String kontraindikacije, ObliciLeka oblik, float prosekOcena,
			String slika, int dostupnaKolicina, double cena, Proizvodjac proizvodjac,
			KategorijaLekova kategorijaLekova) {
		super();
		this.naziv = naziv;
		this.sifra = sifra;
		this.opis = opis;
		this.kontraindikacije = kontraindikacije;
		this.oblik = oblik;
		this.prosekOcena = prosekOcena;
		this.slika = slika;
		this.dostupnaKolicina = dostupnaKolicina;
		this.cena = cena;
		this.proizvodjac = proizvodjac;
		this.kategorijaLekova = kategorijaLekova;
	}
	
	public Lek(Long id,String naziv, String sifra, String opis, String kontraindikacije, ObliciLeka oblik, float prosekOcena,
			String slika, int dostupnaKolicina, double cena, Proizvodjac proizvodjac,
			KategorijaLekova kategorijaLekova) {
		super();
		this.id=id;
		this.naziv = naziv;
		this.sifra = sifra;
		this.opis = opis;
		this.kontraindikacije = kontraindikacije;
		this.oblik = oblik;
		this.prosekOcena = prosekOcena;
		this.slika = slika;
		this.dostupnaKolicina = dostupnaKolicina;
		this.cena = cena;
		this.proizvodjac = proizvodjac;
		this.kategorijaLekova = kategorijaLekova;
	}



	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getSifra() {
		return sifra;
	}


	public void setSifra(String sifra) {
		this.sifra = sifra;
	}


	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public String getKontraindikacije() {
		return kontraindikacije;
	}


	public void setKontraindikacije(String kontraindikacije) {
		this.kontraindikacije = kontraindikacije;
	}


	public ObliciLeka getOblik() {
		return oblik;
	}


	public void setOblik(ObliciLeka oblik) {
		this.oblik = oblik;
	}


	public float getProsekOcena() {
		return prosekOcena;
	}


	public void setProsekOcena(float prosekOcena) {
		this.prosekOcena = prosekOcena;
	}


	public String getSlika() {
		return slika;
	}


	public void setSlika(String slika) {
		this.slika = slika;
	}


	public int getDostupnaKolicina() {
		return dostupnaKolicina;
	}


	public void setDostupnaKolicina(int dostupnaKolicina) {
		this.dostupnaKolicina = dostupnaKolicina;
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
	}


	public Proizvodjac getProizvodjac() {
		return proizvodjac;
	}


	public void setProizvodjac(Proizvodjac proizvodjac) {
		this.proizvodjac = proizvodjac;
	}


	public KategorijaLekova getKategorijaLekova() {
		return kategorijaLekova;
	}


	public void setKategorijaLekova(KategorijaLekova kategorijaLekova) {
		this.kategorijaLekova = kategorijaLekova;
	}
	@Override
	public String toString() {
		return this.id+";"+this.naziv+";"+this.sifra+";"+this.opis+";"+this.kontraindikacije+";"+this.oblik+";"+this.prosekOcena+";"+this.slika+";"
	+this.dostupnaKolicina+";"+this.cena+";"+this.proizvodjac+";"+this.kategorijaLekova;
		
	
	}

	
}