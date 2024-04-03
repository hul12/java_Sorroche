package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao ;

	@Autowired
	public VehicleService() {
		this.vehicleDao = vehicleDao;
	}



	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Error counting vehicles", e);
		}
	}


	public long create(Vehicule vehicle) throws ServiceException {
		try {
			if (vehicle.getConstructeur().isEmpty() || vehicle.getNbPlaces() < 1) {
				throw new ServiceException("Le constructeur et le nombre de places du véhicule sont obligatoires");
			}
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du véhicule", e);
		}
	}

	public Vehicule findById(long id) throws ServiceException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du véhicule par ID", e);
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération de tous les véhicules", e);
		}
    }

	public void delete(long id) {

	}
}
