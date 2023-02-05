package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Racun;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;
import com.ftn.PrviMavenVebProjekat.service.KategorijaLekovaService;
import com.ftn.PrviMavenVebProjekat.service.LekService;
import com.ftn.PrviMavenVebProjekat.service.RacunService;

@Controller
@RequestMapping(value="/korpa")
public class KorpaController implements ServletContextAware {

	public static final String KORPA= "korpa";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private RacunService racunService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@GetMapping
	public ModelAndView index(HttpSession session) {
		List<StavkaRacuna> listaStavki =(List<StavkaRacuna>) session.getAttribute("stavkeKorpe");
		ModelAndView rezultat = new ModelAndView("korpa"); // naziv template-a
		rezultat.addObject("listaStavki", listaStavki); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@PostMapping(value="/kupovina")
	public void create(HttpSession session, HttpServletResponse response){
		Date datum=new Date(System.currentTimeMillis());
		Korisnik korisnik=(Korisnik) session.getAttribute("korisnik");
		List<StavkaRacuna> listaStavki =(List<StavkaRacuna>) session.getAttribute("stavkeKorpe");
		List<Racun>racuni=racunService.findAll();
		Racun racun=new Racun(korisnik,0.0,datum);
		Double cena=0.0;
		for(StavkaRacuna stavka:listaStavki) {
			cena+=stavka.getCena();
		}
		racun.setCena(cena);
		int racunId=racunService.kupovina(racun);
		if(racunId!=0) {
			racun.setId((long) racunId);
			for(StavkaRacuna stavka:listaStavki) {
				stavka.setRacun(racun);
				racunService.sacuvajStavku(stavka);
			}
		}
		
		try {
			response.sendRedirect(bURL+"racuni");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
