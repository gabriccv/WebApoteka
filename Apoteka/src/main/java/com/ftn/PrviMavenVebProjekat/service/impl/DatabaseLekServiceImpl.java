package com.ftn.PrviMavenVebProjekat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.PrviMavenVebProjekat.dao.LekDAO;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.service.LekService;



@Service
public class DatabaseLekServiceImpl implements LekService {

	@Autowired
	private LekDAO lekDAO;
	
	@Override
	public Lek findOne(Long id) {
		return lekDAO.findOne(id);
	}

	@Override
	public List<Lek> findAll() {
		return lekDAO.findAll();
	}

	@Override
	public Lek save(Lek lek) {
		lekDAO.save(lek);
		return lek;
	}

	@Override
	public Lek update(Lek lek) {
		lekDAO.update(lek);
		return lek;
	}
	
	@Override
	public List<Lek> findByQuery(String naziv,String kategorijaLeka,double donjaCena,double gornjaCena, String proizvodjac,String kontraindikacije,
			String opis,String oblik,float prosekOcena){
		return lekDAO.findByQuery(naziv,kategorijaLeka, donjaCena, gornjaCena,proizvodjac,kontraindikacije,opis,oblik,prosekOcena);
	}
	

	@Override
	public Lek delete(Long id) {
		Lek lek = lekDAO.findOne(id);
		lekDAO.delete(id);
		return lek;
	}

}
