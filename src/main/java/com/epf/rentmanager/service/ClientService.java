package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {


	@Autowired
	private ClientDao clientDao;

	private void validateClient(Client client) throws DaoException, ServiceException {
		if (client.getEmail() == null || !client.getEmail().contains("@")) {
			throw new DaoException();
		}
		if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
			throw new DaoException();
		}
		if (client.getDateNaissance() == null || client.getDateNaissance().isAfter(java.time.LocalDate.now().minusYears(18)))
			throw new ServiceException("Date de naissance invalide");
		LocalDate today = LocalDate.now();
		Period period = Period.between(client.getDateNaissance(), today);
		if (period.getYears() < 18) {
			throw new DaoException();
		}

	}

	public Optional<Client> findById(long id) throws ServiceException {
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("erreur");
		}
	}

	public   List<Client> findAll() throws ServiceException {
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("erreur");
		}
	}

	public long create(Client client) throws DaoException, ServiceException {
		validateClient(client);
		return clientDao.create(client);
	}



	public long delete(long client) throws ServiceException {
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du client");
		}
	}





	public void update(Client client) throws Exception {
		validateClient(client);

		try {
			clientDao.update(client);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

}
