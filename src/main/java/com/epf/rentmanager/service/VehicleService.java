package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class VehicleService {

	@Autowired
	private VehicleDao vehicleDao;

	public Vehicule findById(long id) throws DaoException {
		return vehicleDao.findById(id);
	}

	public List<Vehicule> findAll() throws DaoException {
		return vehicleDao.findAll();
	}

	public int create(Vehicule vehicule) throws DaoException {
		validateVehicle(vehicule);
		return vehicleDao.create(vehicule);
	}

	public int update(Vehicule vehicule) throws DaoException {
		validateVehicle(vehicule);
		return vehicleDao.update(vehicule);
	}

	public int delete(long id) throws DaoException {
		return vehicleDao.delete(id);
	}

	private void validateVehicle(Vehicule vehicule) throws DaoException {

		if (vehicule.getConstructeur().trim().isEmpty() || vehicule.getModele().trim().isEmpty()) {
			throw new DaoException();
		}


		if (vehicule.getConstructeur().length() < 2 || vehicule.getModele().length() < 2) {
			throw new DaoException();
		}


		String pattern = "^[A-Za-z0-9\\s-]+$"; // Lettres, chiffres, espaces et tirets autorisés
		if (!Pattern.matches(pattern, vehicule.getConstructeur()) || !Pattern.matches(pattern, vehicule.getModele())) {
			throw new DaoException();
		}


		if (vehicule.getNbPlaces() < 1 || vehicule.getNbPlaces() > 9) {
			throw new DaoException();
		}
	}
}
