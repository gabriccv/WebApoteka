package com.ftn.PrviMavenVebProjekat.service;

import java.util.List;

import com.ftn.PrviMavenVebProjekat.model.Oblik;

public interface OblikService {
	Oblik findOne(Long id); 
	List<Oblik> findAll(); 
	Oblik save(Oblik oblik); 
	Oblik update(Oblik oblik); 
	Oblik delete(Long id); 
}
