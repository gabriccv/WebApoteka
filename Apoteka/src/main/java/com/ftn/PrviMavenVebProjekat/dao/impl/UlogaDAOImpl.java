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

import com.ftn.PrviMavenVebProjekat.dao.UlogaDAO;
import com.ftn.PrviMavenVebProjekat.model.Uloga;

@Repository
public class UlogaDAOImpl implements UlogaDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class UlogaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Uloga> uloge = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);

			


			Uloga uloga = uloge.get(id);
			if (uloga == null) {
				uloga = new Uloga(id, naziv);
				uloge.put(uloga.getId(), uloga); // dodavanje u kolekciju
			}
		}

		public List<Uloga> getUloge() {
			return new ArrayList<>(uloge.values());
		}

	}
	
	@Override
	public Uloga findOne(Long id) {
		String sql = 
				"SELECT u.id, u.naziv FROM uloge u " + 
				"WHERE u.id = ? " + 
				"ORDER BY u.id";

		UlogaRowCallBackHandler rowCallbackHandler = new UlogaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getUloge().get(0);
	}

	@Override
	public List<Uloga> findAll() {
		String sql = 
				"SELECT u.id, u.naziv FROM uloge u " +  
						"ORDER BY u.id";

		UlogaRowCallBackHandler rowCallbackHandler = new UlogaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getUloge();
	}
	
	@Transactional
	@Override
	public int save(Uloga uloga) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO uloge (naziv) VALUES (?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, uloga.getNaziv());
				
				

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Uloga uloga) {
		String sql = "UPDATE uloge SET naziv = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, uloga.getNaziv() ,uloga.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM uloge WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
