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
import com.ftn.PrviMavenVebProjekat.dao.ProizvodjacDAO;
import com.ftn.PrviMavenVebProjekat.model.KategorijaLekova;
import com.ftn.PrviMavenVebProjekat.model.Lek;
import com.ftn.PrviMavenVebProjekat.model.ObliciLeka;
import com.ftn.PrviMavenVebProjekat.model.Oblik;
import com.ftn.PrviMavenVebProjekat.model.Proizvodjac;
import com.ftn.PrviMavenVebProjekat.service.KategorijaLekovaService;
import com.ftn.PrviMavenVebProjekat.service.OblikService;
import com.ftn.PrviMavenVebProjekat.service.ProizvodjacService;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;



@Repository
public class LekDAOImpl implements LekDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProizvodjacService proizvodjacService;
	
	@Autowired
	private KategorijaLekovaService kategorijaLekovaService;
	
	@Autowired
	private OblikService oblikService;
	
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
			
			
			Long oblikId = resultSet.getLong(index++);
			Oblik oblik=oblikService.findOne(oblikId);
			
			float prosekOcena = resultSet.getFloat(index++);
			String slika = resultSet.getString(index++);
			int dostupnaKolicina = resultSet.getInt(index++);
			double cena = resultSet.getDouble(index++);
			
			
			Long proizvodjacId = resultSet.getLong(index++);
			Proizvodjac proizvodjac=proizvodjacService.findOne(proizvodjacId);
			
			
			Long kategorijaId = resultSet.getLong(index++);
			KategorijaLekova kategorijaLekova=kategorijaLekovaService.findOne(kategorijaId);

			
			

			Lek lek = lekovi.get(id);
			if (lek == null) {
				lek = new Lek(id, naziv, sifra,opis,kontraindikacije,oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,kategorijaLekova);
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
				"SELECT * FROM lek l"
				+ " WHERE (l.id=?) " + 
				"ORDER BY l.id";

		LekRowCallBackHandler rowCallbackHandler = new LekRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getLekovi().get(0);
	}

	@Override
	public List<Lek> findAll() {
		String sql = 
				"SELECT * FROM lek" ;

		LekRowCallBackHandler rowCallbackHandler = new LekRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getLekovi();
	}
	
	@Override
	public List<Lek> findByQuery(String naziv,String kategorijaLeka,double donjaCena,double gornjaCena, String proizvodjac,String kontraindikacije,
			String opis,String oblik,float prosekOcena){
		String sql="SELECT l.* FROM lek l ,proizvodjac p,kategorijaLekova k, oblik o where ";
		int i = 0;
		if (naziv != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.naziv like '%"+naziv+"%' )";
			i=+1;
			
		}
		if(kategorijaLeka !=null){
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.kategorijaLeka=k.id and k.naziv like '%"+kategorijaLeka+"%' ) ";
			i=+1;
		}
		
		if (donjaCena > 0 && gornjaCena>donjaCena) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.cena between "+donjaCena+" and "+gornjaCena+")";
			i=+1;
		}
		
		if (proizvodjac != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.proizvodjac=p.id and p.naziv like '%"+proizvodjac+"%' )";
			i=+1;
		}
		
		if (kontraindikacije != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.kontraindikacije like '%"+kontraindikacije+"%' )";
			i=+1;
		}
		
		if (opis != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.opis like '%"+opis+"%' )";
			i=+1;
			
		}
		if (oblik != null) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.oblik=o.id and o.naziv like '%"+oblik+"%')";
			i=+1;
		}
		
		if (prosekOcena != 0) {
			if (i!=0) {
				sql=sql+" and ";
				
			}
			sql=sql+"(l.prosekOcena >="+prosekOcena+" )";
			i=+1;
		}
		System.out.println(sql);
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
				String sql = "INSERT INTO lek (naziv, sifra,opis,kontraindikacije,"
						+ "oblik,prosekOcena,slika,dostupnaKolicina,cena,proizvodjac,kategorijaLeka) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, lek.getNaziv());
				preparedStatement.setString(index++, lek.getSifra());
				preparedStatement.setString(index++, lek.getOpis());
				preparedStatement.setString(index++, lek.getKontraindikacije());
				preparedStatement.setLong(index++, lek.getOblik().getId());
				preparedStatement.setFloat(index++, lek.getProsekOcena());
				preparedStatement.setString(index++, lek.getSlika());
				preparedStatement.setInt(index++, lek.getDostupnaKolicina());
				preparedStatement.setDouble(index++, lek.getCena());
				preparedStatement.setLong(index++, lek.getProizvodjac().getId());
				preparedStatement.setLong(index++, lek.getKategorijaLekova().getId());


				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Lek lek) {
		String sql = "UPDATE lek SET naziv = ?, sifra = ? , opis=?, kontraindikacije=?, oblik=?, prosekOcena=?, slika=?,"
				+ " dostupnaKolicina=?, cena=?, proizvodjac=?, kategorijaLeka=? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, lek.getNaziv() , lek.getSifra(), lek.getOpis(),lek.getKontraindikacije(),
				lek.getOblik().getId(), lek.getProsekOcena(),lek.getSlika(),lek.getDostupnaKolicina(),lek.getCena(),lek.getProizvodjac().getId(),
				lek.getKategorijaLekova().getId(), lek.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM lek WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
