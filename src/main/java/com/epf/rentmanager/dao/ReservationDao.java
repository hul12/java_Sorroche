package com.epf.rentmanager.dao;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ReservationDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
			rs.getLong("id"),
			rs.getLong("clientId"),
			rs.getLong("vehicleId"),
			rs.getDate("debut").toLocalDate(),
			rs.getDate("fin").toLocalDate()
	);

	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(clientId, vehicleId, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_BY_ID_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_ALL_RESERVATIONS_QUERY = "SELECT id, clientId, vehicleId, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET clientId=?, vehicleId=?, debut=?, fin=? WHERE id=?;";

	public List<Reservation> findAll() throws DaoException {
		return jdbcTemplate.query(FIND_ALL_RESERVATIONS_QUERY, rowMapper);
	}

	public Reservation findById(long id) throws DaoException {
		return jdbcTemplate.queryForObject(FIND_RESERVATION_BY_ID_QUERY, new Object[]{id}, rowMapper);
	}

	public int create(Reservation reservation) throws DaoException {
		return jdbcTemplate.update(CREATE_RESERVATION_QUERY,
				reservation.getClientId(),
				reservation.getVehicleId(),
				Date.valueOf(reservation.getDebut()),
				Date.valueOf(reservation.getFin()));
	}

	public int delete(long id) throws DaoException {
		return jdbcTemplate.update(DELETE_RESERVATION_QUERY, id);
	}

	public int update(Reservation reservation) throws DaoException {
		return jdbcTemplate.update(UPDATE_RESERVATION_QUERY,
				reservation.getClientId(),
				reservation.getVehicleId(),
				Date.valueOf(reservation.getDebut()),
				Date.valueOf(reservation.getFin()),
				reservation.getId());
	}
	public List<Reservation> findByVehicleId(long vehicleId) {
		String sql = "SELECT * FROM Reservation WHERE vehicleId = ?";
		return jdbcTemplate.query(sql, new Object[]{vehicleId}, rowMapper);
	}
}
