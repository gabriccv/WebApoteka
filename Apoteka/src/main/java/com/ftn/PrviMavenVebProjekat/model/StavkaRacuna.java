package com.ftn.PrviMavenVebProjekat.model;

public class StavkaRacuna{
	private Long id;
	private Racun racun;
	private Lek lek;
	private int kolicina;
	private double cena;
	public StavkaRacuna() {
		
	}
	public StavkaRacuna( Racun racun, Lek lek, int kolicina, double cena) {
		super();
	
		this.racun = racun;
		this.lek = lek;
		this.kolicina = kolicina;
		this.cena = cena;
	}
	
	public StavkaRacuna( Lek lek, int kolicina, double cena) {
		super();
		this.lek = lek;
		this.kolicina = kolicina;
		this.cena = cena;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Racun getRacun() {
		return racun;
	}



	public void setRacun(Racun racun) {
		this.racun = racun;
	}


	public Lek getLek() {
		return lek;
	}



	public void setLek(Lek lek) {
		this.lek = lek;
	}



	public int getKolicina() {
		return kolicina;
	}



	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
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
	
	
	
	
	
	
}