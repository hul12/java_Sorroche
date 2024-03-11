package com.epf.rentmanager.main;

import com.epf.rentmanager.ui.cli.ClientCli;
import com.epf.rentmanager.ui.cli.VehiculeCli;
import com.epf.rentmanager.ui.cli.ReservationCli;

public class Main {

    public static void main(String[] args) {
        // Test de la gestion des clients
        System.out.println("Gestion des clients :");
        ClientCli.createClient();
        ClientCli.listClients();
        //ClientCli.deleteClient();

        // Test de la gestion des véhicules
        System.out.println("\nGestion des véhicules :");
        VehiculeCli.createVehicule();
        VehiculeCli.listAllVehicules();
        // VehiculeCli.deleteVehicule(); // Décommentez cette ligne pour tester la suppression de véhicule

        // Test de la gestion des réservations
        System.out.println("\nGestion des réservations :");
        ReservationCli.createReservation();
        ReservationCli.listAllReservations();
        // ReservationCli.deleteReservation(); // Décommentez cette ligne pour tester la suppression de réservation
    }
}
