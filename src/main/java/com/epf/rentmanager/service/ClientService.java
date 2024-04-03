package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;



import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.epf.rentmanager.model.Vehicule;

@Service

public class ClientService {
	private ClientDao clientDao;

	@Autowired
	public static ClientService instance;


	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}

		return instance;
	}


	public long create(Client client) throws ServiceException {
		if (client.getNom() == null || client.getNom().isEmpty() || client.getPrenom() == null || client.getPrenom().isEmpty()) {
			throw new ServiceException("Le nom et le prénom du client sont obligatoires");
		}
		client.setNom(client.getNom().toUpperCase()); // Nom de famille en MAJUSCULES
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création du client", e);
		}
	}



	public List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération de tous les clients", e);
		}
	}

	public void delete(long id) {
	}
}
