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

import com.ftn.PrviMavenVebProjekat.dao.RacunDAO;
import com.ftn.PrviMavenVebProjekat.model.Korisnik;
import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.model.Racun;
import com.ftn.PrviMavenVebProjekat.model.StavkaRacuna;
import com.ftn.PrviMavenVebProjekat.service.KorisnikService;
import com.ftn.PrviMavenVebProjekat.service.ProizvodjacService;

@Repository
public class RacunDAOImpl implements RacunDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private KorisnikService korisnikService;
	
	private class RacunRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Racun> racuni = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Long korisnikId = resultSet.getLong(index++);
			Korisnik korisnik=korisnikService.findOneById(korisnikId);
			Double cena = resultSet.getDouble(index++);
			Date datum = resultSet.getDate(index++);
			


			Racun racun = racuni.get(id);
			if (racun == null) {
				
				racun = new Racun(id, korisnik, cena,datum);
				racuni.put(racun.getId(), racun); // dodavanje u kolekciju
			}
		}
//
		public List<Racun> getRacuni() {
			return new ArrayList<>(racuni.values());
		}
//
	}


	@Override
	public List<Racun> findAll() {
		String sql = 
				"SELECT * FROM racun r " +  
						"ORDER BY r.id";

		RacunRowCallBackHandler rowCallbackHandler = new RacunRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getRacuni();
	}
	
	@Override
	public int sacuvajStavku(StavkaRacuna stavka) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO stavkaRacuna (racun, lek,kolicina,cena) VALUES (?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, stavka.getRacun().getId());
				preparedStatement.setLong(index++, stavka.getLek().getId());
				preparedStatement.setInt(index++, stavka.getKolicina());
				preparedStatement.setDouble(index++, stavka.getLek().getCena());
				

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	@Override
	public int kupovina(Racun racun) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO racun (korisnik, cena,datum) VALUES (?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, racun.getKorisnik().getId());
				preparedStatement.setDouble(index++, racun.getCena());
				preparedStatement.setDate(index++,  racun.getDatum());
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		int id=keyHolder.getKey().intValue();
		return uspeh?id:0;
	}

	@Override
	public ArrayList<Racun> findByUser(Long id) {
		
		String sql = 
				"SELECT * FROM racun r " +  
						"WHERE r.korisnik = ? " + 
						" ORDER BY r.id";
						

		RacunRowCallBackHandler rowCallbackHandler = new RacunRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return (ArrayList<Racun>) rowCallbackHandler.getRacuni();
	}
	
}

