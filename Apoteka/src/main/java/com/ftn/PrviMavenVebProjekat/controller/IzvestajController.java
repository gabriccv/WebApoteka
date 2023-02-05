package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping(value="/izvestaji")
public class IzvestajController implements ServletContextAware {
	public static final String IZVESTAJI= "izvestaji";

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
	public ModelAndView index(String spocetniDatum,String skrajnjiDatum,HttpSession session) {
		Korisnik trenutniKorisnik=(Korisnik) session.getAttribute("korisnik");
		Date pocetniDatum=null;
		Date krajnjiDatum=null;
		if(spocetniDatum!=null && skrajnjiDatum!=null && spocetniDatum!="" && skrajnjiDatum!="") {
			
			try {
				java.util.Date pocetniDatumUtil = new SimpleDateFormat("yyyy-MM-dd").parse(spocetniDatum);
				java.util.Date krajnjiDatumUtil=new SimpleDateFormat("yyyy-MM-dd").parse(skrajnjiDatum);
				pocetniDatum=new Date(pocetniDatumUtil.getTime());
				krajnjiDatum=new Date(krajnjiDatumUtil.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		List<StavkaRacuna> stavkeRacuna =racunService.izvestaj(pocetniDatum,krajnjiDatum);
		ModelAndView rezultat = new ModelAndView("izvestaj"); // naziv template-a
		rezultat.addObject("stavkeRacuna", stavkeRacuna); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
}