package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationDao {

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(clientId, vehicleId, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_BY_ID_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_ALL_RESERVATIONS_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET clientId=?, vehicleId=?, debut=?, fin=? WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_ID_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation WHERE vehicleId=?;";

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_ALL_RESERVATIONS_QUERY);
			 ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				reservations.add(mapToReservation(rs));
			}
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when finding all reservations", e);
		}
		return reservations;
	}

	public Reservation findById(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATION_BY_ID_QUERY)) {
			pstmt.setLong(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return mapToReservation(rs);
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when finding reservation by ID", e);
		}
		return null;
	}

	public int create(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setLong(1, reservation.getClientId());
			pstmt.setLong(2, reservation.getVehicleId());
			pstmt.setDate(3, Date.valueOf(reservation.getDebut()));
			pstmt.setDate(4, Date.valueOf(reservation.getFin()));
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("Creating reservation failed, no rows affected.");
			}
			return affectedRows;
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when creating reservation", e);
		}
	}

	public int delete(long id) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY)) {
			pstmt.setLong(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when deleting reservation", e);
		}
	}

	public int update(Reservation reservation) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(UPDATE_RESERVATION_QUERY)) {
			pstmt.setLong(1, reservation.getClientId());
			pstmt.setLong(2, reservation.getVehicleId());
			pstmt.setDate(3, Date.valueOf(reservation.getDebut()));
			pstmt.setDate(4, Date.valueOf(reservation.getFin()));
			pstmt.setLong(5, reservation.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when updating reservation", e);
		}
	}

	public List<Reservation> findByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_ID_QUERY)) {
			pstmt.setLong(1, vehicleId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					reservations.add(mapToReservation(rs));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Problem occurred when finding reservations by vehicle ID", e);
		}
		return reservations;
	}

	private Reservation mapToReservation(ResultSet rs) throws SQLException {
		return new Reservation(
				rs.getLong("id"),
				rs.getLong("clientId"),
				rs.getLong("vehicleId"),
				rs.getDate("debut").toLocalDate(),
				rs.getDate("fin").toLocalDate()
		);
	}
}
