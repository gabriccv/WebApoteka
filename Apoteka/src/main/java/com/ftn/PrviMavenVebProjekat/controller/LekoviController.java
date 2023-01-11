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

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.ObliciLeka;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;
import com.ftn.PrviMavenVebProjekat.service.LekService;



@Controller
@RequestMapping(value="/lekovi")
public class LekoviController implements ServletContextAware {

	public static final String LEKOVI = "lekovi";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private LekService lekService;
	
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
		List<Lek> lekovi = lekService.findAll();
		
		ModelAndView rezultat = new ModelAndView("lekovi"); // naziv template-a
		rezultat.addObject("lekovi", lekovi); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeLekova"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv,@RequestParam String sifra,@RequestParam String opis,@RequestParam String kontraindikacije,@RequestParam ObliciLeka oblik,@RequestParam float prosekOcena,
			@RequestParam String slika,@RequestParam int dostupnaKolicina,@RequestParam double cena,@RequestParam Proizvodjac proizvodjac,
			@RequestParam KategorijaLekova kategorijaLekova,  HttpServletResponse response) throws IOException {		
		Lek lek = new Lek(naziv, sifra, opis,kontraindikacije,oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,kategorijaLekova);
		Lek saved = lekService.save(lek);
		response.sendRedirect(bURL+"lekovi");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String naziv,@RequestParam String sifra,@RequestParam String opis,@RequestParam String kontraindikacije,@RequestParam ObliciLeka oblik,@RequestParam float prosekOcena,
			@RequestParam String slika,@RequestParam int dostupnaKolicina,@RequestParam double cena,@RequestParam Proizvodjac proizvodjac,
			@RequestParam KategorijaLekova kategorijaLekova,  HttpServletResponse response) throws IOException {	
		Lek lek = lekService.findOne(id);
		if(lek != null) {
			if(naziv != null && !naziv.trim().equals(""))
				lek.setNaziv(naziv);
			if(sifra != null && !sifra.trim().equals(""))
				lek.setSifra(sifra);
			if(opis != null && !opis.trim().equals(""))
				lek.setOpis(opis);
			if(kontraindikacije != null && !kontraindikacije.trim().equals(""))
				lek.setKontraindikacije(kontraindikacije);
			if (oblik != null) 
			    lek.setOblik(oblik);
			if(prosekOcena >0.00 )
				lek.setProsekOcena(prosekOcena);
			if(slika != null && !slika.trim().equals(""))
				lek.setSlika(slika);
			if(dostupnaKolicina >0 )
				lek.setDostupnaKolicina(dostupnaKolicina);
			if(cena >0 )
				lek.setCena(cena);
			if(proizvodjac != null )
				lek.setProizvodjac(proizvodjac);
			if(kategorijaLekova != null)
				lek.setKategorijaLekova(kategorijaLekova);
			


				
			
			
		}
		Lek saved = lekService.update(lek);
		response.sendRedirect(bURL+"lekovi");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: kategorijeLekova/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Lek deleted = lekService.delete(id);
		response.sendRedirect(bURL+"lekovi");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Lek lek = lekService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("lek"); // naziv template-a
		rezultat.addObject("lek", lek); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
}
