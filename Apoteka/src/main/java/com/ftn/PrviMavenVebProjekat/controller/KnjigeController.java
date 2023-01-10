package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.model.Knjiga;
import com.ftn.PrviMavenVebProjekat.service.KnjigaService;

@Controller
@RequestMapping(value="/knjige")
public class KnjigeController implements ServletContextAware {

	public static final String KNJIGE_ZA_IZNAJMLJIVANJE = "knjige_za_iznajmljivanje";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private KnjigaService knjigaService;
	
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
		List<Knjiga> knjige = knjigaService.findAll();
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("knjige"); // naziv template-a
		rezultat.addObject("knjige", knjige); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeKnjige"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, @RequestParam String registarskiBrojPrimerka,  
			@RequestParam String jezik, @RequestParam int brojStranica, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum, HttpServletResponse response) throws IOException {		
		Knjiga knjiga = new Knjiga(naziv, registarskiBrojPrimerka, jezik, brojStranica, datum);
		Knjiga saved = knjigaService.save(knjiga);
		response.sendRedirect(bURL+"knjige");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	// POST: knjige/edit
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam String naziv, @RequestParam String registarskiBrojPrimerka,  
			@RequestParam String jezik, @RequestParam int brojStranica, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datum , HttpServletResponse response) throws IOException {	
		Knjiga knjiga = knjigaService.findOne(id);
		if(knjiga != null) {
			if(naziv != null && !naziv.trim().equals(""))
				knjiga.setNaziv(naziv);
			if(jezik != null && !jezik.trim().equals(""))
				knjiga.setJezik(jezik);
			if(brojStranica > 0)
				knjiga.setBrojStranica(brojStranica);
			if(datum != null)
				knjiga.setDatum(datum);
		}
		Knjiga saved = knjigaService.update(knjiga);
		response.sendRedirect(bURL+"knjige");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: knjige/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Knjiga deleted = knjigaService.delete(id);
		response.sendRedirect(bURL+"knjige");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Knjiga knjiga = knjigaService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("knjiga"); // naziv template-a
		rezultat.addObject("knjiga", knjiga); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
//	GET: knjige/zeljene
	@SuppressWarnings("unchecked")
	@GetMapping(value="/zeljene")
	@ResponseBody
	public ModelAndView dodajZeljene(HttpSession session){
		List<Knjiga> zaIznajmljivanje = (List<Knjiga>) session.getAttribute(KNJIGE_ZA_IZNAJMLJIVANJE);
		
		ModelAndView rezultat = new ModelAndView("zaIznajmljivanje"); // naziv template-a
		rezultat.addObject("knjige", zaIznajmljivanje); // podatak koji se šalje template-u

		return rezultat;
	}
	
	// POST: knjige/zeljene/dodaj
	@SuppressWarnings("unchecked")
	@PostMapping(value="/zeljene/ukloni")
	@ResponseBody
	public void ukloniIzZeljenih(@RequestParam Long idKnjige, HttpSession session, HttpServletResponse response) throws IOException {
		List<Knjiga> zaIznajmljivanje = (List<Knjiga>) session.getAttribute(KNJIGE_ZA_IZNAJMLJIVANJE);	

		for (Knjiga knjiga : zaIznajmljivanje) {
			if (knjiga.getId().equals(idKnjige)) {
				zaIznajmljivanje.remove(knjiga);
				break;
			}
		}
		response.sendRedirect(bURL+"knjige/zeljene");
	}
}
