package be.vdab.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import be.vdab.entities.Brouwer;
import be.vdab.valueobjects.Adres;

@Repository
class BrouwerDAOImpl implements BrouwerDAO {
	private final JdbcTemplate jdbcTemplate;

	private final BrouwerRowMapper rowMapper = new BrouwerRowMapper();
	private final SimpleJdbcInsert simpleJdbcInsert;
	
	private static final String BEGIN_SQL = "select id, naam, omzet, straat, huisnr, postcode, gemeente from brouwers ";
	private static final String SQL_FIND_ALL = BEGIN_SQL + "order by naam";
	private static final String SQL_FIND_BY_NAAM = BEGIN_SQL + "where naam like ?";

	@Autowired
	BrouwerDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("brouwers");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
	}

	@Override
	public void create(Brouwer brouwer) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("naam", brouwer.getNaam());
		parameters.put("postcode", brouwer.getAdres().getPostcode());
		parameters.put("gemeente", brouwer.getAdres().getGemeente());
		parameters.put("straat", brouwer.getAdres().getStraat());
		parameters.put("huisnr", brouwer.getAdres().getHuisNr());
		parameters.put("omzet", brouwer.getOmzet());
		brouwer.setId(simpleJdbcInsert.executeAndReturnKey(parameters).longValue());
	}

	@Override
	public List<Brouwer> findAll() {
		return jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
	}

	@Override
	public List<Brouwer> findByNaam(String beginNaam) {
		return jdbcTemplate.query(SQL_FIND_BY_NAAM, rowMapper, beginNaam + '%');
	}

	// private rowmapper class
	private static class BrouwerRowMapper implements RowMapper<Brouwer> {
		@Override
		public Brouwer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return new Brouwer(resultSet.getLong("id"), resultSet.getString("naam"), resultSet.getInt("omzet"),
					new Adres(resultSet.getString("straat"), resultSet.getString("huisnr"),
							resultSet.getInt("postcode"), resultSet.getString("gemeente")));
		}
	}

}
