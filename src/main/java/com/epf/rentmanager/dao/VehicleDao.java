package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String FIND_ALL_VEHICULES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String FIND_VEHICULE_BY_ID_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";

	public int count() throws DaoException {
		int count = 0;
		String query = "SELECT COUNT(*) AS total FROM Vehicle";
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				count = resultSet.getInt("total");
			}
		} catch (SQLException e) {
			throw new DaoException("Error counting vehicles", e);
		}
		return count;
	}


	public long create(Vehicule vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNbPlaces());

			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DaoException("La création du véhicule a échoué, aucune ligne affectée.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("La création du véhicule a échoué, aucun ID généré.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule.", e);
		}
	}

	// Méthode pour supprimer un véhicule
	public long delete(Vehicule vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {

			preparedStatement.setLong(1, vehicle.getId());

			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule.", e);
		}
	}

	// Méthode pour trouver un véhicule par son ID
	public Vehicule findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICULE_BY_ID_QUERY)) {

			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String constructeur = resultSet.getString("constructeur");
					String modele = resultSet.getString("modele");
					int nbPlaces = resultSet.getInt("nb_places");

					return new Vehicule(constructeur, modele, nbPlaces);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule par ID.", e);
		}
	}

	// Méthode pour trouver tous les véhicules
	public List<Vehicule> findAll() throws DaoException {
		List<Vehicule> vehicles = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 Statement statement = connection.createStatement();
			 ResultSet resultSet = statement.executeQuery(FIND_ALL_VEHICULES_QUERY)) {

			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nbPlaces = resultSet.getInt("nb_places");

				vehicles.add(new Vehicule(constructeur, modele, nbPlaces));
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de tous les véhicules.", e);
		}
		return vehicles;
	}
}
