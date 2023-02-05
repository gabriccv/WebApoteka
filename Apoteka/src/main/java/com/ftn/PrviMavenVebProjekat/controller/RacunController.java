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
@RequestMapping(value="/racuni")
public class RacunController implements ServletContextAware {
	public static final String RACUNI= "racuni";

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
		Korisnik trenutniKorisnik=(Korisnik) session.getAttribute("korisnik");
		Long id=trenutniKorisnik.getId();
		List<Racun> listaRacuna =racunService.findByUser(id);
		ModelAndView rezultat = new ModelAndView("racun"); // naziv template-a
		rezultat.addObject("listaRacuna", listaRacuna); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
}