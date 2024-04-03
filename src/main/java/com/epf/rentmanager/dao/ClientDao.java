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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.DaoException;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getDateNaissance()));

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La création du client a échoué, aucune ligne affectée.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("La création du client a échoué, aucun ID généré.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client.", e);
		}
	}

	public long delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {

			preparedStatement.setLong(1, client.getId());

			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client.", e);
		}
	}

	public Client findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {

			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String nom = resultSet.getString("nom");
					String prenom = resultSet.getString("prenom");
					String email = resultSet.getString("email");
					LocalDate naissance = resultSet.getTimestamp("naissance").toLocalDateTime().toLocalDate();

					return new Client(1, nom, prenom, email, naissance);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID.", e);
		}
	}


	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_CLIENTS_QUERY)) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				LocalDate naissance = resultSet.getDate("naissance").toLocalDate();

				clients.add(new Client(1, nom, prenom, email, naissance));
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de tous les clients.", e);
		}
		return clients;
	}


}
