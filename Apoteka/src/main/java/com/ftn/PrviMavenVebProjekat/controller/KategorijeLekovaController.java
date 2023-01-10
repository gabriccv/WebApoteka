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
import com.ftn.PrviMavenVebProjekat.service.KategorijaLekovaService;

@Controller
@RequestMapping(value="/kategorijeLekova")
public class KategorijeLekovaController implements ServletContextAware {

	public static final String KATEGORIJE_LEKOVA = "kategorije_lekova";

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private KategorijaLekovaService kategorijaLekovaService;
	
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
		List<KategorijaLekova> kategorijeLekova = kategorijaLekovaService.findAll();
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("kategorijeLekova"); // naziv template-a
		rezultat.addObject("kategorijeLekova", kategorijeLekova); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeKategorijeLekova"; // stranica za dodavanje knjige
	}

	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: knjige/add
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String naziv, @RequestParam String namena,
			   @RequestParam String opis,  HttpServletResponse response) throws IOException {		
		KategorijaLekova kategorijaLekova = new KategorijaLekova(naziv, namena, opis);
		KategorijaLekova saved = kategorijaLekovaService.save(kategorijaLekova);
		response.sendRedirect(bURL+"kategorijeLekova");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev */
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String naziv, @RequestParam String namena,
			   @RequestParam String opis,  HttpServletResponse response) throws IOException {	
		KategorijaLekova kategorijaLekova = kategorijaLekovaService.findOne(id);
		if(kategorijaLekova != null) {
			if(naziv != null && !naziv.trim().equals(""))
				kategorijaLekova.setNaziv(naziv);
			if(namena != null && !namena.trim().equals(""))
				kategorijaLekova.setNamena(namena);
			if(opis != null && !opis.trim().equals(""))
				kategorijaLekova.setOpis(opis);
			
		}
		KategorijaLekova saved = kategorijaLekovaService.update(kategorijaLekova);
		response.sendRedirect(bURL+"kategorijeLekova");
	}
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: kategorijeLekova/delete
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		KategorijaLekova deleted = kategorijaLekovaService.delete(id);
		response.sendRedirect(bURL+"kategorijeLekova");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		KategorijaLekova kategorijaLekova = kategorijaLekovaService.findOne(id);
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("kategorijaLekova"); // naziv template-a
		rezultat.addObject("kategorijaLekova", kategorijaLekova); // podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
}
