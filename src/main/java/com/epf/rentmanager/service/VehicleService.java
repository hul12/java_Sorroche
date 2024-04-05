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
		// Vérifier que le constructeur et le modèle ne sont pas vides
		if (vehicule.getConstructeur().trim().isEmpty() || vehicule.getModele().trim().isEmpty()) {
			throw new DaoException("Le constructeur et le modèle ne peuvent pas être vides.");
		}

		// Vérifier la longueur du constructeur et du modèle
		if (vehicule.getConstructeur().length() < 2 || vehicule.getModele().length() < 2) {
			throw new DaoException("Le constructeur et le modèle doivent contenir au moins 2 caractères.");
		}

		// Exemple de validation de format avec une expression régulière (regex) simple
		// Ceci est un exemple basique. Adaptez la regex selon vos besoins spécifiques de format.
		String pattern = "^[A-Za-z0-9\\s-]+$"; // Lettres, chiffres, espaces et tirets autorisés
		if (!Pattern.matches(pattern, vehicule.getConstructeur()) || !Pattern.matches(pattern, vehicule.getModele())) {
			throw new DaoException("Le constructeur ou le modèle contient des caractères invalides.");
		}

		// Vérifier le nombre de places
		if (vehicule.getNbPlaces() < 1 || vehicule.getNbPlaces() > 9) {
			throw new DaoException("Le nombre de places doit être entre 1 et 9.");
		}
	}
}
