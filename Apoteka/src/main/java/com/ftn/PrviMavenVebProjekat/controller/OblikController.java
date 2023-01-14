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

import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.service.OblikService;


@Controller
@RequestMapping(value="/oblici")
public class OblikController implements ServletContextAware {

	public static final String OBLICI = "oblici";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private OblikService oblikService;
	
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
		List<Oblik> oblici = oblikService.findAll();
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("oblici"); // naziv template-a
		rezultat.addObject("oblici", oblici); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeOblika"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, 
			    HttpServletResponse response) throws IOException {		
		Oblik oblik = new Oblik(naziv);
		Oblik saved = oblikService.save(oblik);
		response.sendRedirect(bURL+"oblici");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String naziv,
			    HttpServletResponse response) throws IOException {	
		Oblik oblik = oblikService.findOne(id);
		if(oblik != null) {
			if(naziv != null && !naziv.trim().equals(""))
				oblik.setNaziv(naziv);
			
			
			
		}
		Oblik saved = oblikService.update(oblik);
		response.sendRedirect(bURL+"oblici");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: kategorijeLekova/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Oblik deleted = oblikService.delete(id);
		response.sendRedirect(bURL+"oblici");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Oblik oblik = oblikService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("oblik"); // naziv template-a
		rezultat.addObject("oblik", oblik); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
}
