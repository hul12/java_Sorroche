package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VehicleDao {

	private static final String FIND_ALL_VEHICLES_QUERY = "SELECT id, constructeur, modele, nbPlaces FROM Vehicule;";
	private static final String FIND_VEHICLE_BY_ID_QUERY = "SELECT id, constructeur, modele, nbPlaces FROM Vehicule WHERE id=?;";
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicule(constructeur, modele, nbPlaces) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicule WHERE id=?;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicule SET constructeur=?, modele=?, nbPlaces=? WHERE id=?;";

	public List<Vehicule> findAll() throws DaoException {
		List<Vehicule> vehicules = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_VEHICLES_QUERY);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				vehicules.add(mapToVehicule(rs));
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return vehicules;
	}

	public Vehicule findById(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_BY_ID_QUERY)) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapToVehicule(rs);
				}
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
		return null;
	}

	public int create(Vehicule vehicule) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, vehicule.getConstructeur());
			pstmt.setString(2, vehicule.getModele());
			pstmt.setInt(3, vehicule.getNbPlaces());
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException();
			}
			return affectedRows;
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int delete(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY)) {
			pstmt.setLong(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public int update(Vehicule vehicule) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(UPDATE_VEHICLE_QUERY)) {
			pstmt.setString(1, vehicule.getConstructeur());
			pstmt.setString(2, vehicule.getModele());
			pstmt.setInt(3, vehicule.getNbPlaces());
			pstmt.setLong(4, vehicule.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	private Vehicule mapToVehicule(ResultSet rs) throws SQLException {
		return new Vehicule(
				rs.getLong("id"),
				rs.getString("constructeur"),
				rs.getString("modele"),
				rs.getInt("nbPlaces")
		);
	}
}
