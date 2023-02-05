package com.ftn.PrviMavenVebProjekat.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;
import com.ftn.PrviMavenVebProjekat.service.UlogaService;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {
	
	public static final String KORISNIK_KEY = "korisnici";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private UlogaService ulogaService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 

	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		System.out.println(email);
		System.out.println(lozinka);
		postLogin(email, lozinka, session, response);
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public void postLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		
//		Korisnik korisnik = korisnikService.findOne(email, sifra);
//		String greska = "";
//		if (korisnik == null)
//			greska = "neispravni kredencijali";
//
//		if (!greska.equals("")) {
//			PrintWriter out;
//			out = response.getWriter();
//			File htmlFile = new File("C:/greska.html");
//			Document doc = Jsoup.parse(htmlFile, "UTF-8");
//
//			Element body = doc.select("body").first();
//
//			if (!greska.equals("")) {
//				Element divGreska = new Element(Tag.valueOf("div"), "").text(greska);
//				body.appendChild(divGreska);
//			}
//			
//			Element loginForm = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "korisnici/login");
//			Element table = new Element(Tag.valueOf("table"), "");
//			Element caption = new Element(Tag.valueOf("caption"), "").text("Prijava korisnika na sistem");
//			Element trEmail = new Element(Tag.valueOf("tr"), "");
//			Element thEmail = new Element(Tag.valueOf("th"), "").text("Email:");
//			Element tdEmail = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "email"));
//			Element trLozinka = new Element(Tag.valueOf("tr"), "");
//			Element thLozinka = new Element(Tag.valueOf("th"), "").text("Sifra:");
//			Element tdLozinka = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "sifra"));
//			Element trSubmit = new Element(Tag.valueOf("tr"), "");
//			Element thSubmit = new Element(Tag.valueOf("th"), "");
//			Element tdSubmit = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Prijavi se"));
//			
//			trEmail.appendChild(thEmail);
//			trEmail.appendChild(tdEmail);
//			trLozinka.appendChild(thLozinka);
//			trLozinka.appendChild(tdLozinka);
//			trSubmit.appendChild(thSubmit);
//			trSubmit.appendChild(tdSubmit);
//
//			table.appendChild(caption);
//			table.appendChild(trEmail);
//			table.appendChild(trLozinka);
//			table.appendChild(trSubmit);
//			
//			loginForm.appendChild(table);
//
//			body.appendChild(loginForm);
//			
//			out.write(doc.html());
//			return;
//		}
//
//		if (session.getAttribute(KORISNIK_KEY) != null)
//			greska = "korisnik je već prijavljen na sistem morate se prethodno odjaviti<br/>";
//
//		if (!greska.equals("")) {
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out;
//			out = response.getWriter();
//
//			StringBuilder retVal = new StringBuilder();
//			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
//					+ "	<base href=\"/PrviiMavenVebProjekat/\">	\r\n" + "	<title>Prijava korisnika</title>\r\n"
//					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
//					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
//					+ "</head>\r\n" + "<body>\r\n" + "	<ul>\r\n"
//					+ "		<li><a href=\"registracija.html\">Registruj se</a></li>\r\n" + "	</ul>\r\n");
//			if (!greska.equals(""))
//				retVal.append("	<div>" + greska + "</div>\r\n");
//			retVal.append("	<a href=\"index.html\">Povratak</a>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");
//
//			out.write(retVal.toString());
//			return;
//		}
		
		Korisnik korisnik = korisnikService.findOne(email, lozinka);
		String greska = "";
		  if (korisnik == null) {
			  greska = "neispravni kredencijali";;
		  }
		  
		  
		  
		  else{
			  session.setAttribute("korisnik", korisnik);
		  }
		  ArrayList<StavkaRacuna> stavkeKorpe=new ArrayList<StavkaRacuna>();

		  session.setAttribute("stavkeKorpe", stavkeKorpe);
		  
		  response.sendRedirect(bURL + "lekovi");
		  
			System.out.println(korisnik.getUloga().getNaziv());

			
//		session.setAttribute(KORISNIK_KEY, korisnik);
//
//		response.sendRedirect(bURL + "lekovi");
	}
	
	@GetMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {	

//		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(KORISNIK_KEY);
//		String greska = "";
//		if(korisnik==null)
//			greska="korisnik nije prijavljen<br/>";
//		
//		if(!greska.equals("")) {
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out;	
//			out = response.getWriter();
//			
//			StringBuilder retVal = new StringBuilder();
//			retVal.append(
//					"<!DOCTYPE html>\r\n" + 
//					"<html>\r\n" + 
//					"<head>\r\n" +
//					"	<meta charset=\"UTF-8\">\r\n" + 
//					"	<base href=\"/PrviMavenVebProjekat/\">	\r\n" + 
//					"	<title>Prijava korisnika</title>\r\n" + 
//					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
//					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n" + 
//					"</head>\r\n" + 
//					"<body>\r\n" + 
//					"	<ul>\r\n" + 
//					"		<li><a href=\"registracija.html\">Registruj se</a></li>\r\n" + 
//					"	</ul>\r\n");
//			if(!greska.equals(""))
//				retVal.append(
//					"	<div>"+greska+"</div>\r\n");
//			retVal.append(
//					"	<form method=\"post\" action=\"PrijavaOdjava/Login\">\r\n" + 
//					"		<table>\r\n" + 
//					"			<caption>Prijava korisnika na sistem</caption>\r\n" + 
//					"			<tr><th>Email:</th><td><input type=\"text\" value=\"\" name=\"email\" required/></td></tr>\r\n" + 
//					"			<tr><th>Šifra:</th><td><input type=\"password\" value=\"\" name=\"sifra\" required/></td></tr>\r\n" + 
//					"			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n" + 
//					"		</table>\r\n" + 
//					"	</form>\r\n" + 
//					"	<br/>\r\n" + 
//					"	<ul>\r\n" + 
//					"		<li><a href=\"korisnici/logout\">Odjavi se</a></li>\r\n" + 
//					"	</ul>" +
//					"</body>\r\n" + 
//					"</html>");
//			
//			out.write(retVal.toString());
//			return;
//		}
//		
//		
//		request.getSession().removeAttribute(KORISNIK_KEY);
//		request.getSession().invalidate();
		session.removeAttribute("stavkeKorpe");
		session.removeAttribute("korisnik");
		  
		response.sendRedirect(bURL+"");
		System.out.println(session.getAttribute("korisnik"));
	}
	
//	@GetMapping(value="/registracija")
//	public String create(HttpSession session, HttpServletResponse response){
//		return "registracija"; // stranica za dodavanje knjige
//	}
//	
	@PostMapping(value = "/registracija")
	public void registracija(@RequestParam(required = true) String email, @RequestParam(required = true) String lozinka,
			@RequestParam(required = true) String korisnickoIme,
			@RequestParam(required = true) String ime, @RequestParam(required = true) String prezime, 
			@RequestParam(required = true) Date datumRodjenja,@RequestParam(required = true) String adresa,
			@RequestParam(required = true) String brojTelefona,@RequestParam(required = true) Date datumIVremeRegistracije,
			
			HttpSession session, HttpServletResponse response) throws IOException {

	   
 
		Korisnik korisnik = new Korisnik(korisnickoIme,lozinka, email, ime,prezime,datumRodjenja,adresa,brojTelefona,datumIVremeRegistracije);
		korisnikService.save(korisnik);
		
		response.sendRedirect(bURL + "");
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam (required=false) String naziv,@RequestParam (required=false) String uloga,HttpSession session) {
		List<Korisnik> korisnici=new ArrayList<Korisnik>();
		 if(naziv==null && uloga==null) {
			 korisnici = korisnikService.findAll();
		 }
		 else {
			 korisnici = korisnikService.findByQuery(naziv,uloga);

		 }
		
		ModelAndView rezultat = new ModelAndView("korisnici"); // naziv template-a
		rezultat.addObject("korisnici", korisnici);
		// podatak koji se šalje template-u

		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String korisnickoIme,@RequestParam String lozinka,@RequestParam String email,
			@RequestParam String ime,@RequestParam String prezime,@RequestParam Date datumRodjenja,
			@RequestParam String adresa,@RequestParam String brojTelefona,@RequestParam Date datumIVremeRegistracije,
			@RequestParam Long ulogaId, HttpServletResponse response) throws IOException {	
		Korisnik korisnik = korisnikService.findOneById(id);
		if(korisnik != null) {
			if(korisnickoIme != null && !korisnickoIme.trim().equals(""))
				korisnik.setKorisnickoIme(korisnickoIme);
			if(lozinka != null && !lozinka.trim().equals(""))
				korisnik.setLozinka(lozinka);
			if(email != null && !email.trim().equals(""))
				korisnik.setEmail(email);
			if(ime != null && !ime.trim().equals(""))
				korisnik.setIme(ime);
			if(prezime != null && !prezime.trim().equals(""))
				korisnik.setPrezime(prezime);
			if(datumRodjenja !=null )
				korisnik.setDatumRodjenja(datumRodjenja);
			if(adresa != null && !adresa.trim().equals(""))
				korisnik.setAdresa(adresa);
			if(brojTelefona != null && !brojTelefona.trim().equals(""))
				korisnik.setBrojTelefona(brojTelefona);
			if(ulogaId != null )
			{
				Uloga uloga=ulogaService.findOne(ulogaId);

				korisnik.setUloga(uloga);
			}
			
		}
		Korisnik saved = korisnikService.update(korisnik);
		response.sendRedirect(bURL+"lekovi");
	}
	
//	@GetMapping
//	@ResponseBody
//	public ModelAndView getKorisnici(HttpSession session, HttpServletResponse response){
//		List<Korisnik> korisnici = korisnikService.findAll();
//		
//		// podaci sa nazivom template-a
//		ModelAndView rezultat = new ModelAndView("korisnici"); // naziv template-a
//		rezultat.addObject("korisnici", korisnici); // podatak koji se šalje template-u
//
//		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
//	}
	
	@PostMapping(value="/obrisi")
	public void obrisiKorisnika(@RequestParam Long id, HttpServletResponse response) throws IOException {				
		korisnikService.delete(id);

		response.sendRedirect(bURL+"korisnici");
	}
	
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Korisnik korisnik = korisnikService.findOneById(id);
		List<Uloga> uloge=ulogaService.findAll();
		
		// podaci sa nazivom template-a
		ModelAndView rezultat = new ModelAndView("korisnik"); // naziv template-a
		rezultat.addObject("korisnik", korisnik); // podatak koji se šalje template-u
		rezultat.addObject("uloge", uloge);
		return rezultat; // prosleđivanje zahteva zajedno sa podacima template-u
	}

}
