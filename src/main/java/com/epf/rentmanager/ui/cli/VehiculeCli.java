package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class VehiculeCli {

    @Autowired
    private VehicleService vehicleService;

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\nGestion des véhicules:");
            System.out.println("1. Ajouter un véhicule");
            System.out.println("2. Afficher tous les véhicules");
            System.out.println("3. Supprimer un véhicule");
            System.out.println("4. Quitter");

            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addVehicule();
                    break;
                case 2:
                    listVehicules();
                    break;
                case 3:
                    deleteVehicule();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Option non reconnue.");
                    break;
            }
        }
    }

    private void addVehicule() {
        try {
            System.out.print("Entrez le constructeur: ");
            String constructeur = scanner.nextLine();

            System.out.print("Entrez le modèle: ");
            String modele = scanner.nextLine();

            System.out.print("Entrez le nombre de places: ");
            int nbPlaces = scanner.nextInt();

            Vehicule vehicule = new Vehicule();
            vehicule.setConstructeur(constructeur);
            vehicule.setModele(modele);
            vehicule.setNbPlaces(nbPlaces);

            vehicleService.create(vehicule);

            System.out.println("Véhicule ajouté avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de l'ajout du véhicule: " + e.getMessage());
        }
    }

    private void listVehicules() {
        try {
            List<Vehicule> vehicules = vehicleService.findAll();
            for (Vehicule vehicule : vehicules) {
                System.out.println(vehicule);
            }
        } catch (DaoException e) {
            System.err.println("Erreur lors de la récupération des véhicules: " + e.getMessage());
        }
    }

    private void deleteVehicule() {
        try {
            System.out.print("Entrez l'ID du véhicule à supprimer: ");
            long id = scanner.nextLong();
            vehicleService.delete(id);

            System.out.println("Véhicule supprimé avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de la suppression du véhicule: " + e.getMessage());
        }
    }
}
