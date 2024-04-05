package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Vehicule> rowMapper = (rs, rowNum) -> new Vehicule(
			rs.getLong("id"),
			rs.getString("constructeur"),
			rs.getString("modele"),
			rs.getInt("nbPlaces")
	);

	private static final String FIND_ALL_VEHICLES_QUERY = "SELECT id, constructeur, modele, nbPlaces FROM Vehicule;";
	private static final String FIND_VEHICLE_BY_ID_QUERY = "SELECT id, constructeur, modele, nbPlaces FROM Vehicule WHERE id=?;";
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicule(constructeur, modele, nbPlaces) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicule WHERE id=?;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicule SET constructeur=?, modele=?, nbPlaces=? WHERE id=?;";

	public List<Vehicule> findAll() throws DaoException {
		return jdbcTemplate.query(FIND_ALL_VEHICLES_QUERY, rowMapper);
	}

	public Vehicule findById(long id) throws DaoException {
		return jdbcTemplate.queryForObject(FIND_VEHICLE_BY_ID_QUERY, new Object[]{id}, rowMapper);
	}

	public int create(Vehicule vehicule) throws DaoException {
		return jdbcTemplate.update(CREATE_VEHICLE_QUERY,
				vehicule.getConstructeur(),
				vehicule.getModele(),
				vehicule.getNbPlaces());
	}

	public int delete(long id) throws DaoException {
		return jdbcTemplate.update(DELETE_VEHICLE_QUERY, id);
	}

	public int update(Vehicule vehicule) throws DaoException {
		return jdbcTemplate.update(UPDATE_VEHICLE_QUERY,
				vehicule.getConstructeur(),
				vehicule.getModele(),
				vehicule.getNbPlaces(),
				vehicule.getId());
	}
}
