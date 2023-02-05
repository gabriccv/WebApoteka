package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.PrviMavenVebProjekat.dao.KorisnikDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.model.Uloga;
import com.ftn.PrviMavenVebProjekat.service.UlogaService;

@Repository
public class KorisnikDAOImpl implements KorisnikDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UlogaService ulogaService;
	
	private class KorisnikRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String korisnickoIme = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);
			String email = resultSet.getString(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			Date datumRodjenja = resultSet.getDate(index++);
			String adresa = resultSet.getString(index++);
			String brojTelefona = resultSet.getString(index++);
			Date datumIVremeRegistracije = resultSet.getDate(index++);
			Long ulogaId = resultSet.getLong(index++);
			Uloga uloga=ulogaService.findOne(ulogaId);

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				

				korisnik = new Korisnik(id, korisnickoIme, lozinka, email, ime,prezime,datumRodjenja,adresa,brojTelefona,datumIVremeRegistracije,uloga);
				korisnici.put(korisnik.getId(), korisnik); // dodavanje u kolekciju
			}
		}

		public List<Korisnik> getKorisnici() {
			return new ArrayList<>(korisnici.values());
		}

	}
	
	@Override
	public Korisnik findOne(Long id) {
		String sql = 
				"SELECT * FROM korisnici kor " + 
				"WHERE kor.id = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKorisnici().get(0);
	}
	
	@Override
	public Korisnik findOne(String email) {
		String sql = 
				"SELECT * FROM korisnici kor " + 
				"WHERE kor.email = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email);

		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public Korisnik findOne(String email, String lozinka) {
		String sql = 
				"SELECT * FROM korisnici kor " + 
				"WHERE kor.email = ? AND " +
				"kor.lozinka = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email, lozinka);

		if(rowCallbackHandler.getKorisnici().size() == 0) {
			return null;
		}
		
		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public List<Korisnik> findAll() {
		String sql = 
				"SELECT * FROM korisnici kor " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnici();
	}
	
	@Override
	public List<Korisnik> findByQuery(String naziv,String uloga){
		String sql="SELECT k.* FROM korisnici k ,uloge u where ";
		int i = 0;
		if (naziv != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(k.ime like '%"+naziv+"%' )";
			i=+1;
			
		}
		if(uloga !=null){
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(k.uloga=u.id and u.naziv like '%"+uloga+"%' ) ";
			i=+1;
		}
		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);
		return rowCallbackHandler.getKorisnici();
	}
	
	@Transactional
	@Override
	public int save(Korisnik korisnik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				;
				
				String sql = "INSERT INTO korisnici (korisnickoIme, lozinka, email, ime,prezime,datumRodjenja,adresa, brojTelefona, "
						+ "datumIVremeRegistracije) VALUES (?, ?, ?, ?,?,?,?,?,now())";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, korisnik.getKorisnickoIme());
				preparedStatement.setString(index++, korisnik.getLozinka());
				preparedStatement.setString(index++, korisnik.getEmail());
				preparedStatement.setString(index++, korisnik.getIme());
				preparedStatement.setString(index++, korisnik.getPrezime());
				preparedStatement.setDate(index++, korisnik.getDatumRodjenja());
				preparedStatement.setString(index++, korisnik.getAdresa());
				preparedStatement.setString(index++, korisnik.getBrojTelefona());
//				preparedStatement.setDate(index++, korisnik.getDatumIVremeRegistracije());
//				preparedStatement.setLong(index++, korisnik.getUloga().getId());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET korisnickoIme = ?, lozinka = ?, email = ?, ime = ? ,prezime= ? , datumRodjenja= ? , adresa=?,"
				+ " brojTelefona=?, datumIVremeRegistracije=? , uloga=? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, korisnik.getKorisnickoIme() , korisnik.getLozinka(), korisnik.getEmail(),
				korisnik.getIme(), korisnik.getPrezime(),korisnik.getDatumRodjenja(),korisnik.getAdresa(),
				korisnik.getBrojTelefona(), korisnik.getDatumIVremeRegistracije(),korisnik.getUloga().getId(),korisnik.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
