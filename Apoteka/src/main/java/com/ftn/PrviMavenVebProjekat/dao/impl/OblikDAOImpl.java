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

import com.ftn.PrviMavenVebProjekat.dao.OblikDAO;
import com.ftn.PrviMavenVebProjekat.model.Oblik;

@Repository
public class OblikDAOImpl implements OblikDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class OblikRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Oblik> oblici = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);

			


			Oblik oblik = oblici.get(id);
			if (oblik == null) {
				oblik = new Oblik(id, naziv);
				oblici.put(oblik.getId(), oblik); // dodavanje u kolekciju
			}
		}

		public List<Oblik> getOblici() {
			return new ArrayList<>(oblici.values());
		}

	}
	
	@Override
	public Oblik findOne(Long id) {
		String sql = 
				"SELECT o.id, o.naziv FROM oblik o " + 
				"WHERE o.id = ? " + 
				"ORDER BY o.id";

		OblikRowCallBackHandler rowCallbackHandler = new OblikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getOblici().get(0);
	}

	@Override
	public List<Oblik> findAll() {
		String sql = 
				"SELECT o.id, o.naziv FROM oblik o " +  
						"ORDER BY o.id";

		OblikRowCallBackHandler rowCallbackHandler = new OblikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getOblici();
	}
	
	@Transactional
	@Override
	public int save(Oblik oblik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO oblik (naziv) VALUES (?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, oblik.getNaziv());
				
				

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Oblik oblik) {
		String sql = "UPDATE oblik SET naziv = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, oblik.getNaziv() ,oblik.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM oblik WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
