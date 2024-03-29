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

import com.ftn.PrviMavenVebProjekat.dao.ProizvodjacDAO;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;

@Repository
public class ProizvodjacDAOImpl implements ProizvodjacDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class ProizvodjacRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Proizvodjac> proizvodjaci = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);
			String drzava = resultSet.getString(index++);
			


			Proizvodjac proizvodjac = proizvodjaci.get(id);
			if (proizvodjac == null) {
				
				proizvodjac = new Proizvodjac(id, naziv, drzava);
				proizvodjaci.put(proizvodjac.getId(), proizvodjac); // dodavanje u kolekciju
			}
		}

		public List<Proizvodjac> getProizvodjaci() {
			return new ArrayList<>(proizvodjaci.values());
		}

	}
	
	@Override
	public Proizvodjac findOne(Long id) {
		
		String sql = 
				"SELECT p.id, p.naziv, p.drzava FROM proizvodjac p " + 
				"WHERE p.id = ? " + 
				"ORDER BY p.id";

		ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);
		
		return rowCallbackHandler.getProizvodjaci().get(0);
	}

	@Override
	public List<Proizvodjac> findAll() {
		String sql = 
				"SELECT p.id, p.naziv, p.drzava FROM proizvodjac p " +  
						"ORDER BY p.id";

		ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getProizvodjaci();
	}
	
	@Transactional
	@Override
	public int save(Proizvodjac proizvodjac) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO proizvodjac (naziv, drzava) VALUES (?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, proizvodjac.getNaziv());
				preparedStatement.setString(index++, proizvodjac.getDrzava());
				

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Proizvodjac proizvodjac) {
		String sql = "UPDATE proizvodjac SET naziv = ?, drzava = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, proizvodjac.getNaziv() , proizvodjac.getDrzava(),proizvodjac.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM proizvodjac WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
