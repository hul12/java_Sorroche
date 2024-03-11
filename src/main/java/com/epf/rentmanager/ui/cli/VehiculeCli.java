package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.utils.IOUtils;
import java.util.List;

public class VehiculeCli {

    private static VehicleService vehiculeService = VehicleService.getInstance();

    public static void createVehicule() {
        try {
            System.out.println("Création d'un nouveau véhicule :");
            System.out.print("Constructeur : ");
            String constructeur = IOUtils.readString();
            System.out.print("Modèle : ");
            String modele = IOUtils.readString();
            System.out.print("Nombre de places : ");
            int nbPlaces = IOUtils.readInt();

            Vehicule vehicule = new Vehicule(constructeur, modele, nbPlaces);
            long id = vehiculeService.create(vehicule);
            System.out.println("Véhicule créé avec l'identifiant : " + id);
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    public static void listAllVehicules() {
        try {
            System.out.println("Liste de tous les véhicules :");
            List<Vehicule> vehicules = vehiculeService.findAll();
            for (Vehicule vehicule : vehicules) {
                System.out.println(vehicule);
            }
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la récupération des véhicules : " + e.getMessage());
        }
    }

    public static void deleteVehicule() {
        System.out.println("Suppression d'un véhicule :");
        System.out.print("Identifiant du véhicule à supprimer : ");
        long id = IOUtils.readLong();
        vehiculeService.delete(id);
        System.out.println("Véhicule avec l'identifiant " + id + " supprimé avec succès.");
    }

    public static void main(String[] args) {
        System.out.println("Gestion des véhicules :");
        System.out.println("a. Créer un véhicule");
        System.out.println("b. Lister tous les véhicules");
        System.out.println("e. Supprimer un véhicule");

        System.out.print("Choix : ");
        String choice = IOUtils.readString();

        switch (choice) {
            case "a":
                createVehicule();
                break;
            case "b":
                listAllVehicules();
                break;
            case "e":
                deleteVehicule();
                break;
            default:
                System.err.println("Choix invalide.");
        }
    }
}
