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

import com.ftn.PrviMavenVebProjekat.dao.KategorijaLekovaDAO;
import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;

@Repository
public class KategorijaLekovaDAOImpl implements KategorijaLekovaDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KategorijaLekovaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, KategorijaLekova> kategorijeLekova = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);
			String namena = resultSet.getString(index++);
			String opis = resultSet.getString(index++);


			KategorijaLekova kategorijaLekova = kategorijeLekova.get(id);
			if (kategorijaLekova == null) {
				kategorijaLekova = new KategorijaLekova(id, naziv, namena,opis);
				kategorijeLekova.put(kategorijaLekova.getId(), kategorijaLekova); // dodavanje u kolekciju
			}
		}

		public List<KategorijaLekova> getKategorijeLekova() {
			return new ArrayList<>(kategorijeLekova.values());
		}

	}
	
	@Override
	public KategorijaLekova findOne(Long id) {
		String sql = 
				"SELECT k.id, k.naziv, k.namena,k.opis FROM kategorijalekova k " + 
				"WHERE k.id = ? " + 
				"ORDER BY k.id";

		KategorijaLekovaRowCallBackHandler rowCallbackHandler = new KategorijaLekovaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKategorijeLekova().get(0);
	}

	@Override
	public List<KategorijaLekova> findAll() {
		String sql = 
				"SELECT k.id, k.naziv, k.namena,k.opis FROM kategorijalekova k " +  
						"ORDER BY k.id";

		KategorijaLekovaRowCallBackHandler rowCallbackHandler = new KategorijaLekovaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKategorijeLekova();
	}
	
	@Transactional
	@Override
	public int save(KategorijaLekova kategorijaLekova) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO kategorijaLekova (naziv, namena,opis) VALUES (?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, kategorijaLekova.getNaziv());
				preparedStatement.setString(index++, kategorijaLekova.getNamena());
				preparedStatement.setString(index++, kategorijaLekova.getOpis());

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(KategorijaLekova kategorijaLekova) {
		String sql = "UPDATE kategorijalekova SET naziv = ?, namena = ? , opis=? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, kategorijaLekova.getNaziv() , kategorijaLekova.getNamena(), kategorijaLekova.getOpis(), kategorijaLekova.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM kategorijalekova WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
