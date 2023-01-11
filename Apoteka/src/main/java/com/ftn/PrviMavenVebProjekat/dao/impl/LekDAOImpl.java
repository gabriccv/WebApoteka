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

import com.ftn.PrviMavenVebProjekat.dao.LekDAO;
import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.ObliciLeka;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;



@Repository
public class LekDAOImpl implements LekDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class LekRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Lek> lekovi = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String naziv = resultSet.getString(index++);
			String sifra = resultSet.getString(index++);
			String opis = resultSet.getString(index++);
			String kontraindikacije = resultSet.getString(index++);
			String oblikString = resultSet.getString(index++);
			float prosekOcena = resultSet.getFloat(index++);
			String slika = resultSet.getString(index++);
			int dostupnaKolicina = resultSet.getInt(index++);
			double cena = resultSet.getDouble(index++);
			
			Proizvodjac proizvodjac = new Proizvodjac();
			String proizvodjacName = resultSet.getString(index++);
			proizvodjac.setNaziv(proizvodjacName);
			
			KategorijaLekova kategorija = new KategorijaLekova();
			String kategorijaNaziv = resultSet.getString(index++);
			kategorija.setNaziv(kategorijaNaziv);

			
			

			Lek lek = lekovi.get(id);
			if (lek == null) {
				lek = new Lek(id, naziv, sifra,opis,kontraindikacije,oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,kategorija);
				lekovi.put(lek.getId(), lek); // dodavanje u kolekciju
			}
		}

		public List<Lek> getLekovi() {
			return new ArrayList<>(lekovi.values());
		}

	}
	
	@Override
	public Lek findOne(Long id) {
		String sql = 
				"SELECT * FROM lek l " + 
				"WHERE l.id = ? " + 
				"ORDER BY l.id";

		LekRowCallBackHandler rowCallbackHandler = new LekRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getLekovi().get(0);
	}

	@Override
	public List<Lek> findAll() {
		String sql = 
				"SELECT * FROM lek l " +  
						"ORDER BY l.id";

		LekRowCallBackHandler rowCallbackHandler = new LekRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getLekovi();
	}
	
	@Transactional
	@Override
	public int save(Lek lek) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO lek (naziv, sifra,opis,kontraindikacije,oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,proizvodjac) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, lek.getNaziv());
				preparedStatement.setString(index++, lek.getSifra());
				preparedStatement.setString(index++, lek.getOpis());
				preparedStatement.setString(index++, lek.getKontraindikacije());
				preparedStatement.setString(index++, lek.getOblik().name());
				preparedStatement.setFloat(index++, lek.getProsekOcena());
				preparedStatement.setString(index++, lek.getSlika());
				preparedStatement.setInt(index++, lek.getDostupnaKolicina());
				preparedStatement.setDouble(index++, lek.getCena());
				preparedStatement.setString(index++, lek.getProizvodjac().getNaziv());
				preparedStatement.setString(index++, lek.getKategorijaLekova().getNaziv());


				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	private Long id;
	 private String naziv;
	 private String sifra;
	 private String opis;
	 private String kontraindikacije;
	 private ObliciLeka oblik;
	 private float prosekOcena;
	 private String slika;
	 private int dostupnaKolicina;
	 private double cena;
	 private Proizvodjac proizvodjac;
	 private KategorijaLekova kategorijaLekova;
	@Transactional
	@Override
	public int update(Lek lek) {
		String sql = "UPDATE lek SET naziv = ?, sifra = ? , opis=?, kontraindikacije=?, oblik=?, prosekOcena=?, slika=?, dostupnaKolicina=?, cena=?, proizvodjac=?, kategorijaLekova=? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, lek.getNaziv() , lek.getSifra(), lek.getOpis(),lek.getKontraindikacije(),lek.getOblik(), lek.getProsekOcena(),lek.getSlika(),lek.getDostupnaKolicina(),lek.getCena(),lek.getProizvodjac(), lek.getKategorijaLekova(), lek.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM lek WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
