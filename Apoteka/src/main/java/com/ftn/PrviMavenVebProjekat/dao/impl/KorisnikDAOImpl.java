package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
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

@Repository
public class KorisnikDAOImpl implements KorisnikDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			String email = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, ime, prezime, email, lozinka);
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
				"SELECT kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka FROM korisnici kor " + 
				"WHERE kor.id = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKorisnici().get(0);
	}
	
	@Override
	public Korisnik findOne(String email) {
		String sql = 
				"SELECT kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka FROM korisnici kor " + 
				"WHERE kor.email = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email);

		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public Korisnik findOne(String email, String sifra) {
		String sql = 
				"SELECT kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka FROM korisnici kor " + 
				"WHERE kor.email = ? AND " +
				"kor.lozinka = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email, sifra);

		if(rowCallbackHandler.getKorisnici().size() == 0) {
			return null;
		}
		
		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public List<Korisnik> findAll() {
		String sql = 
				"SELECT kor.id, kor.ime, kor.prezime, kor.email, kor.lozinka FROM korisnici kor " + 
				"ORDER BY kor.id";

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
				String sql = "INSERT INTO korisnici (ime, prezime, email, lozinka) VALUES (?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, korisnik.getIme());
				preparedStatement.setString(index++, korisnik.getPrezime());
				preparedStatement.setString(index++, korisnik.getEmail());
				preparedStatement.setString(index++, korisnik.getLozinka());

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
		String sql = "UPDATE korisnici SET ime = ?, prezime = ?, email = ?, lozinka = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, korisnik.getIme() , korisnik.getPrezime(), korisnik.getEmail(), korisnik.getLozinka(), korisnik.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
