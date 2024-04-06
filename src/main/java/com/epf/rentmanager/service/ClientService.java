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

@Service
public class ClientService {


	@Autowired
	private ClientDao clientDao;



	public Client findById(long id) throws DaoException {
		return clientDao.findById(id);
	}

	public List<Client> findAll() throws DaoException {
		return clientDao.findAll();
	}

	public long create(Client client) throws DaoException {
		validateClient(client);
		return clientDao.create(client);
	}

	public int update(Client client) throws DaoException {
		return clientDao.update(client);
	}


	public long delete(long client) throws ServiceException {
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur a eu lieu lors de la suppression du client");
		}
	}


	private void validateClient(Client client) throws DaoException {
		if (client.getEmail() == null || !client.getEmail().contains("@")) {
			throw new DaoException();
		}
		if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
			throw new DaoException();
		}
		LocalDate today = LocalDate.now();
		Period period = Period.between(client.getDateNaissance(), today);
		if (period.getYears() < 18) {
			throw new DaoException();
		}
	}

}
