package com.epf.rentmanager.main;


import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import com.epf.rentmanager.ui.cli.ClientCli;
import com.epf.rentmanager.ui.cli.VehiculeCli;
import com.epf.rentmanager.ui.cli.ReservationCli;
import com.epf.rentmanager.confi.AppConfiguration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
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
