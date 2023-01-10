package com.ftn.PrviMavenVebProjekat.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
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
import com.ftn.PrviMavenVebProjekat.dao.KnjigaDAO;
import com.ftn.PrviMavenVebProjekat.model.Autor;
import com.ftn.PrviMavenVebProjekat.model.Knjiga;

@Repository
public class KnjigaDAOImpl implements KnjigaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private AutorDAO autorDAO;
	
	private class KnjigaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Knjiga> knjige = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);
			String registarskiBrojPrimerka = resultSet.getString(index++);
			String jezik = resultSet.getString(index++);
			Integer brojStranica = resultSet.getInt(index++);
			boolean izdata = resultSet.getBoolean(index++);
			LocalDate datum = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();

			Knjiga knjiga = knjige.get(id);
			if (knjiga == null) {
				knjiga = new Knjiga(id, naziv, registarskiBrojPrimerka, jezik, brojStranica, izdata, datum);
				knjige.put(knjiga.getId(), knjiga); // dodavanje u kolekciju
			}
			
			Long autorId = resultSet.getLong(index++);
			if(autorId != 0) {
				Autor autor = autorDAO.findOne(autorId);
				knjiga.getAutori().add(autor);
			}
		}

		public List<Knjiga> getKnjige() {
			return new ArrayList<>(knjige.values());
		}

	}

	@Override
	public Knjiga findOne(Long id) {
		String sql = 
				"SELECT k.id, k.naziv, k.registarskiBrojPrimerka, k.jezik, k.brojStranica, k.izdata, k.datum, a.id FROM knjige k " + 
				"LEFT JOIN knjigeAutori ka ON ka.knjigaId = k.id " + 
				"LEFT JOIN autori a ON ka.autorId = a.id " + 
				"WHERE k.id = ? " + 
				"ORDER BY k.id";

		KnjigaRowCallBackHandler rowCallbackHandler = new KnjigaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKnjige().get(0);
	}

	@Override
	public List<Knjiga> findAll() {
		String sql = 
				"SELECT k.id, k.naziv, k.registarskiBrojPrimerka, k.jezik, k.brojStranica, k.izdata, k.datum, a.id FROM knjige k " + 
				"LEFT JOIN knjigeAutori ka ON ka.knjigaId = k.id " + 
				"LEFT JOIN autori a ON ka.autorId = a.id " +
				"ORDER BY k.id";

		KnjigaRowCallBackHandler rowCallbackHandler = new KnjigaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKnjige();
	}
	
	@Override
	public List<Knjiga> findAll(LocalDate datumOd, LocalDate datumDo) {
		
		ArrayList<Object> listaArgumenata = new ArrayList<Object>();
		
		String sql = 
			"SELECT k.id, k.naziv, k.registarskiBrojPrimerka, k.jezik, k.brojStranica, k.izdata, k.datum, a.id FROM knjige k " + 
			"LEFT JOIN knjigeAutori ka ON ka.knjigaId = k.id " + 
			"LEFT JOIN autori a ON ka.autorId = a.id ";
			
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean imaArgumenata = false;
		
		if(datumOd!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.datum >= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumOd);
		}
		
		if(datumDo!=null) {
			if(imaArgumenata)
				whereSql.append(" AND ");
			whereSql.append("k.datum <= ?");
			imaArgumenata = true;
			listaArgumenata.add(datumDo);
		}
		
		if(imaArgumenata)
			sql=sql + whereSql.toString()+" ORDER BY k.id";
		else
			sql=sql + " ORDER BY k.id";
		System.out.println(sql);
		
		KnjigaRowCallBackHandler rowCallbackHandler = new KnjigaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKnjige();
	}
	
	@Override
	public int findCount(LocalDate datumOd, LocalDate datumDo) {
		String sql = 
				"SELECT count(k.id) FROM knjige k " + 
				"where k.datum >= ? AND k.datum <= ?";

		return jdbcTemplate.queryForObject(sql, Integer.class, datumOd, datumDo);
	}
	
	@Override
	public int findCount(LocalDate datumOd, LocalDate datumDo, Autor autor) {
		String sql = 
				"SELECT count(k.id) FROM knjige k " + 
				"left join knjigeAutori ka on k.id = ka.knjigaId " + 
				"left join autori a on ka.autorId = a.id " + 
				"where datum >= ? and datum <= ? and a.id = ?";

		return jdbcTemplate.queryForObject(sql, Integer.class, datumOd, datumDo, autor.getId());
	}

	@Transactional
	@Override
	public int save(Knjiga knjiga) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO knjige (naziv, registarskiBrojPrimerka, jezik, brojStranica, izdata, datum) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, knjiga.getNaziv());
				preparedStatement.setString(index++, knjiga.getRegistarskiBrojPrimerka());
				preparedStatement.setString(index++, knjiga.getJezik());
				preparedStatement.setInt(index++, knjiga.getBrojStranica());
				preparedStatement.setBoolean(index++, knjiga.isIzdata());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(knjiga.getDatum().atStartOfDay()));

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Knjiga knjiga) {	
		String sql = "DELETE FROM knjigeAutori WHERE knjigaId = ?";
		jdbcTemplate.update(sql, knjiga.getId());
	
		boolean uspeh = true;
		sql = "INSERT INTO knjigeAutori (knjigaId, autorId) VALUES (?, ?)";
		for (Autor itAutor: knjiga.getAutori()) {	
			uspeh = uspeh &&  jdbcTemplate.update(sql, knjiga.getId(), itAutor.getId()) == 1;
		}
		
		sql = "UPDATE knjige SET naziv = ?, registarskiBrojPrimerka = ?, jezik = ?, brojStranica = ?, izdata = ?, datum = ? WHERE id = ?";	
		uspeh = jdbcTemplate.update(sql, knjiga.getNaziv(), knjiga.getRegistarskiBrojPrimerka(), knjiga.getJezik(), knjiga.getBrojStranica(), knjiga.isIzdata(), knjiga.getDatum(), knjiga.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM knjige WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
