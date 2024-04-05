package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao; // Supposons que vous avez un DAO pour accéder aux données des véhicules.
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private VehicleDao vehicleDao;

    public Reservation findById(long id) throws DaoException {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() throws DaoException {
        return reservationDao.findAll();
    }

    public int create(Reservation reservation) throws DaoException {
        validateReservationDates(reservation);
        checkVehicleAvailability(reservation);
        return reservationDao.create(reservation);
    }

    public int update(Reservation reservation) throws DaoException {
        validateReservationDates(reservation);
        checkVehicleAvailabilityForUpdate(reservation);
        return reservationDao.update(reservation);
    }

    public int delete(long id) throws DaoException {
        return reservationDao.delete(id);
    }

    private void validateReservationDates(Reservation reservation) throws DaoException {
        if (reservation.getDebut().isAfter(reservation.getFin())) {
            throw new DaoException("La date de début doit être avant la date de fin.");
        }
    }

    private void checkVehicleAvailability(Reservation reservation) throws DaoException {
        List<Reservation> reservations = reservationDao.findByVehicleId(reservation.getVehicleId());
        for (Reservation existingReservation : reservations) {
            if (reservation.getDebut().isBefore(existingReservation.getFin()) &&
                    reservation.getFin().isAfter(existingReservation.getDebut())) {
                throw new DaoException("Le véhicule est déjà réservé pour les dates sélectionnées.");
            }
        }
    }

    private void checkVehicleAvailabilityForUpdate(Reservation reservation) throws DaoException {
        List<Reservation> reservations = reservationDao.findByVehicleId(reservation.getVehicleId());
        for (Reservation existingReservation : reservations) {
            if (existingReservation.getId() != reservation.getId() &&
                    reservation.getDebut().isBefore(existingReservation.getFin()) &&
                    reservation.getFin().isAfter(existingReservation.getDebut())) {
                throw new DaoException("Le véhicule est déjà réservé pour les dates sélectionnées.");
            }
        }
    }

}



