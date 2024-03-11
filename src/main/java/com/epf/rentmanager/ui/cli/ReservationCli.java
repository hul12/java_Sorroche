package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;


import java.time.LocalDate;
import java.util.List;

public class ReservationCli {

    private static ReservationService reservationService = ReservationService.getInstance();

    public static void createReservation() {
        try {
            System.out.println("Création d'une nouvelle réservation :");
            System.out.print("Identifiant du client : ");
            long clientId = IOUtils.readLong();
            System.out.print("Identifiant du véhicule : ");
            long vehicleId = IOUtils.readLong();
            System.out.print("Date de début (YYYY-MM-DD) : ");
            LocalDate debut = IOUtils.readLocalDate();
            System.out.print("Date de fin (YYYY-MM-DD) : ");
            LocalDate fin = IOUtils.readLocalDate();

            Reservation reservation = new Reservation(0, clientId, vehicleId, debut, fin);
            long id = reservationService.create(reservation);
            System.out.println("Réservation créée avec l'identifiant : " + id);
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    public static void listAllReservations() {
        try {
            System.out.println("Liste de toutes les réservations :");
            List<Reservation> reservations = reservationService.findAll();
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.err.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("Gestion des réservations :");
        System.out.println("a. Créer une réservation");
        System.out.println("b. Lister toutes les réservations");

        System.out.print("Choix : ");
        String choice = IOUtils.readString();

        switch (choice) {
            case "a":
                createReservation();
                break;
            case "b":
                listAllReservations();
                break;
            default:
                System.err.println("Choix invalide.");
        }
    }
}

