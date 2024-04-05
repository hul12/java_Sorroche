package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

@Repository
public class ClientDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	private RowMapper<Client> rowMapper = (rs, rowNum) -> new Client(
			rs.getLong("id"),
			rs.getString("nom"),
			rs.getString("prenom"),
			rs.getString("email"),
			rs.getDate("naissance").toLocalDate()
	);

	public long create(Client client) throws DaoException {
		jdbcTemplate.update(CREATE_CLIENT_QUERY,
				client.getNom(),
				client.getPrenom(),
				client.getEmail(),
				Date.valueOf(client.getDateNaissance())
		);
		// Assuming auto-generated ID retrieval is handled elsewhere or using JDBC features.
		return 0; // Placeholder for actual ID retrieval logic
	}

	// Dans ClientDao.java
	public int delete(long id) {
		String sql = "DELETE FROM clients WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}


	public Client findById(long id) throws DaoException {
		return jdbcTemplate.queryForObject(FIND_CLIENT_QUERY, new Object[]{id}, rowMapper);
	}

	public List<Client> findAll() throws DaoException {
		return jdbcTemplate.query(FIND_CLIENTS_QUERY, rowMapper);
	}
	public int update(Client client) {
		String sql = "UPDATE clients SET nom = ?, prenom = ?, email = ?, dateNaissance = ? WHERE id = ?";
		return jdbcTemplate.update(sql,
				client.getNom(),
				client.getPrenom(),
				client.getEmail(),
				Date.valueOf(client.getDateNaissance()),
				client.getId());
	}
}
