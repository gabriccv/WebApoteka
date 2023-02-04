package com.ftn.PrviMavenVebProjekat.dao;


import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Lek;

public interface LekDAO {
	
	public Lek findOne(Long id);

	public List<Lek> findAll();
	
	public List<Lek> findByQuery(String naziv,String kategorijaLeka,double donjaCena,double gornjaCena, String proizvodjac,String kontraindikacije,
			String opis,String oblik,float prosekOcena);

	public int save(Lek lek);

	public int update(Lek lek);

	public int delete(Long id);
}
