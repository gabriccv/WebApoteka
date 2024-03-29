package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
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

import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;
import com.ftn.PrviMavenVebProjekat.service.ProizvodjacService;


@Controller
@RequestMapping(value="/proizvodjaci")
public class ProizvodjaciController implements ServletContextAware {

	public static final String PROIZVODJACI = "proizvodjaci";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private ProizvodjacService proizvodjacService;
	
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
	public ModelAndView index() {
		List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("proizvodjaci"); // naziv template-a
		rezultat.addObject("proizvodjaci", proizvodjaci); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeProizvodjaca"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, @RequestParam String drzava,
			    HttpServletResponse response) throws IOException {		
		Proizvodjac proizvodjac = new Proizvodjac(naziv, drzava);
		Proizvodjac saved = proizvodjacService.save(proizvodjac);
		response.sendRedirect(bURL+"proizvodjaci");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String naziv, @RequestParam String drzava,
			    HttpServletResponse response) throws IOException {	
		Proizvodjac proizvodjac = proizvodjacService.findOne(id);
		if(proizvodjac != null) {
			if(naziv != null && !naziv.trim().equals(""))
				proizvodjac.setNaziv(naziv);
			if(drzava != null && !drzava.trim().equals(""))
				proizvodjac.setDrzava(drzava);
			
			
		}
		Proizvodjac saved = proizvodjacService.update(proizvodjac);
		response.sendRedirect(bURL+"proizvodjaci");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: kategorijeLekova/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Proizvodjac deleted = proizvodjacService.delete(id);
		response.sendRedirect(bURL+"proizvodjaci");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Proizvodjac proizvodjac = proizvodjacService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("proizvodjac"); // naziv template-a
		rezultat.addObject("proizvodjac", proizvodjac); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
}
