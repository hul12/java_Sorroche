package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;
import org.springframework.stereotype.Repository;

@Repository

public class ReservationDao {

	private ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY,
					 Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setLong(1, reservation.getClientId());
			preparedStatement.setLong(2, reservation.getVehiculeId());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La création de la réservation a échoué, aucune ligne affectée.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1); // Retourne l'identifiant de la nouvelle réservation créée
				} else {
					throw new DaoException("La création de la réservation a échoué, aucun ID généré.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation.", e);
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {

			preparedStatement.setLong(1, reservation.getId());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation.", e);
		}
	}
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)) {

			preparedStatement.setLong(1, clientId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong("id");
					long vehicleId = resultSet.getLong("vehicle_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();

					reservations.add(new Reservation(id, clientId, vehicleId, debut, fin));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des réservations par client.", e);
		}
		return reservations;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, vehicleId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long id = resultSet.getLong("id");
					long clientId = resultSet.getLong("client_id");
					LocalDate debut = resultSet.getDate("debut").toLocalDate();
					LocalDate fin = resultSet.getDate("fin").toLocalDate();

					reservations.add(new Reservation(id, clientId, vehicleId, debut, fin));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des réservations par véhicule.", e);
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_RESERVATIONS_QUERY)) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				long clientId = resultSet.getLong("client_id");
				long vehicleId = resultSet.getLong("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();

				reservations.add(new Reservation(id, clientId, vehicleId, debut, fin));
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de toutes les réservations.", e);
		}
		return reservations;
	}
}
