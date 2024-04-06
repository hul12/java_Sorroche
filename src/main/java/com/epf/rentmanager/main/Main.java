package com.epf.rentmanager.main;

import com.epf.rentmanager.confi.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);

        try {
            // Ajout et affichage de clients
            System.out.println("Création et affichage des clients :");
            Client client1 = new Client("Doe", "John", "john.doe@example.com", LocalDate.of(1980, 1, 1));
            clientService.create(client1);
            clientService.findAll().forEach(System.out::println);

            // Ajout et affichage de véhicules
            System.out.println("\nCréation et affichage des véhicules :");
            Vehicule vehicule1 = new Vehicule("Renault", "Clio", 5);
            vehicleService.create(vehicule1);
            vehicleService.findAll().forEach(System.out::println);

            // Ajout et affichage de réservations
            System.out.println("\nCréation et affichage des réservations :");
            Reservation reservation1 = new Reservation(client1.getId(), vehicule1.getId(), LocalDate.now(), LocalDate.now().plusDays(5));
            reservationService.create(reservation1);
            reservationService.findAll().forEach(System.out::println);

            // Suppression d'un client
            System.out.println("\nSuppression d'un client :");
            clientService.delete(client1.getId());
            System.out.println("Client supprimé avec succès.");

            // Suppression d'un véhicule
            System.out.println("\nSuppression d'un véhicule :");
            vehicleService.delete(vehicule1.getId());
            System.out.println("Véhicule supprimé avec succès.");

            // Suppression d'une réservation
            System.out.println("\nSuppression d'une réservation :");
            reservationService.delete(reservation1.getId());
            System.out.println("Réservation supprimée avec succès.");

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

