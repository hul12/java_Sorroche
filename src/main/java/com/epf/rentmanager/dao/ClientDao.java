package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ClientDao {

	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_BY_ID_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_ALL_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	public long create(Client client) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, client.getNom());
			pstmt.setString(2, client.getPrenom());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getDateNaissance()));
			pstmt.executeUpdate();
			ResultSet resultSet = pstmt.getGeneratedKeys();
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (resultSet.next()) {
					return resultSet.getInt(1);
				} else {
					throw new DaoException();
				}
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Client findById(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_BY_ID_QUERY)) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getDate("naissance").toLocalDate());
				}
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_CLIENTS_QUERY);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				clients.add(new Client(rs.getLong("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getDate("naissance").toLocalDate()));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return clients;
	}

	public long delete(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY)) {
			pstmt.setLong(1, id);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				return affectedRows;
			} else {
				throw new DaoException();
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int update(Client client) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(UPDATE_CLIENT_QUERY)) {
			pstmt.setString(1, client.getNom());
			pstmt.setString(2, client.getPrenom());
			pstmt.setString(3, client.getEmail());
			pstmt.setDate(4, Date.valueOf(client.getDateNaissance()));
			pstmt.setLong(5, client.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
}
