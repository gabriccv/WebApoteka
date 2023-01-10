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

import com.ftn.PrviMavenVebProjekat.dao.AutorDAO;
import com.ftn.PrviMavenVebProjekat.model.Autor;

@Repository
public class AutorDAOImpl implements AutorDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class AutorRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Autor> autori = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);

			Autor autor = autori.get(id);
			if (autor == null) {
				autor = new Autor(id, ime, prezime);
				autori.put(autor.getId(), autor); // dodavanje u kolekciju
			}
		}

		public List<Autor> getAutori() {
			return new ArrayList<>(autori.values());
		}

	}
	
	@Override
	public Autor findOne(Long id) {
		String sql = 
				"SELECT a.id, a.ime, a.prezime FROM autori a " + 
				"WHERE a.id = ? " + 
				"ORDER BY a.id";

		AutorRowCallBackHandler rowCallbackHandler = new AutorRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getAutori().get(0);
	}

	@Override
	public List<Autor> findAll() {
		String sql = 
				"SELECT a.id, a.ime, a.prezime FROM autori a " + 
				"ORDER BY a.id";

		AutorRowCallBackHandler rowCallbackHandler = new AutorRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getAutori();
	}
	
	@Transactional
	@Override
	public int save(Autor autor) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO autori (ime, prezime) VALUES (?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, autor.getIme());
				preparedStatement.setString(index++, autor.getPrezime());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Autor autor) {
		String sql = "UPDATE autor SET ime = ?, prezime = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, autor.getIme() , autor.getPrezime(), autor.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM autori WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
