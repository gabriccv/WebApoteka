package com.ftn.PrviMavenVebProjekat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.bean.SecondConfiguration.ApplicationMemory;
import com.ftn.PrviMavenVebProjekat.model.ClanskaKarta;
import com.ftn.PrviMavenVebProjekat.model.Knjiga;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.service.ClanskaKartaService;
import com.ftn.PrviMavenVebProjekat.service.KnjigaService;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;

@Controller
@RequestMapping(value="/clanskeKarte")
public class ClanskeKarteController implements ServletContextAware {
	
	public static final String CLANSKE_KARTE_KEY = "clanske_karte";
	public static final String KORISNICI_KEY = "korisnici";
	public static final String CLANSKA_KARTA = "clanska_karta";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private ClanskaKartaService clanskaKartaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private KnjigaService knjigaService;

	/** pristup ApplicationContext */
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
		List<ClanskaKarta> clanskeKarte = clanskaKartaService.findAll();		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("clanskeKarte"); // naziv template-a
		rezultat.addObject("clanskeKarte", clanskeKarte); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public ModelAndView create() {
		List<Korisnik> korisnici = korisnikService.findAll();
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("dodavanjeClanskeKarte"); // naziv template-a
		rezultat.addObject("korisnici", korisnici); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: clanskeKarte/add
	@PostMapping(value="/add")
	public void create(@RequestParam String registarskiBroj, @RequestParam Long idKorisnika, HttpServletResponse response) throws IOException {				
		Korisnik korisnik = korisnikService.findOneById(idKorisnika);
		if (korisnik == null) {
			//todo domaci vrati gresku
		}
		ClanskaKarta clanskaKarta = new ClanskaKarta(registarskiBroj, korisnik);
		clanskaKartaService.save(clanskaKarta);
		response.sendRedirect(bURL+"clanskeKarte");
	}
	
	// POST: clanskeKarte/izdajKnjigu -> izdajKnjige sve sa sesije
	@SuppressWarnings("unchecked")
	@PostMapping(value="/izdajKnjige")
	public void izdajKnjigu(@RequestParam String registarskiBroj, HttpSession session, HttpServletResponse response) throws IOException {		
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);
		if (ck == null) {
			//todo
		}
		List<Knjiga> zaIznajmljivanje = (List<Knjiga>) session.getAttribute(KnjigeController.KNJIGE_ZA_IZNAJMLJIVANJE);	

		for (Knjiga k : zaIznajmljivanje) {
			if (ck != null) {
				ck.getIznajmljenjeKnjige().add(k);
				ck = clanskaKartaService.update(ck);
				if (ck != null) {
					k.setIzdata(true);
					knjigaService.update(k);
				}
			}	
		}
		
		session.setAttribute(KnjigeController.KNJIGE_ZA_IZNAJMLJIVANJE, new ArrayList<Knjiga>());

		response.sendRedirect(bURL+"knjige");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam String registarskiBroj) {
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("clanskaKarta"); // naziv template-a
		rezultat.addObject("clanskaKarta", ck); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	// POST: clanskeKarte/vratiKnjigu
	@PostMapping(value="/vratiKnjigu")
	public void vratiKnjigu(@RequestParam Long idKnjige, @RequestParam String registarskiBroj, HttpServletResponse response) throws IOException {		
		ClanskaKarta ck = clanskaKartaService.findOneByRegistarskiBroj(registarskiBroj);

		if (ck != null) {
			Knjiga knjiga = knjigaService.findOne(idKnjige);
			for(Knjiga itKnjiga: ck.getIznajmljenjeKnjige()) {
				if(itKnjiga.getId().equals(knjiga.getId())) {
					ck.getIznajmljenjeKnjige().remove(itKnjiga);
					break;
				}
			}
			
			ck = clanskaKartaService.update(ck);
			if (ck != null) {
				knjiga.setIzdata(false);
				knjigaService.update(knjiga);
			}
		}

		response.sendRedirect(bURL+"knjige");
	}

}
