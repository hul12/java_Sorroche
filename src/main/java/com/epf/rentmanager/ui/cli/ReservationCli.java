package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class ReservationCli {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\nGestion des réservations:");
            System.out.println("1. Ajouter une réservation");
            System.out.println("2. Afficher toutes les réservations");
            System.out.println("3. Supprimer une réservation");
            System.out.println("4. Quitter");

            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addReservation();
                    break;
                case 2:
                    listReservations();
                    break;
                case 3:
                    deleteReservation();
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

    private void addReservation() {
        try {
            System.out.print("Entrez l'ID du client: ");
            long clientId = scanner.nextLong();


            System.out.print("Entrez l'ID du véhicule: ");
            long vehicleId = scanner.nextLong();


            System.out.print("Entrez la date de début (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.next());

            System.out.print("Entrez la date de fin (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.next());

            Reservation reservation = new Reservation(0, clientId, vehicleId, startDate, endDate);
            reservationService.create(reservation);

            System.out.println("Réservation ajoutée avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de l'ajout de la réservation: " + e.getMessage());
        }
    }

    private void listReservations() {
        try {
            List<Reservation> reservations = reservationService.findAll();
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } catch (DaoException e) {
            System.err.println("Erreur lors de la récupération des réservations: " + e.getMessage());
        }
    }

    private void deleteReservation() {
        try {
            System.out.print("Entrez l'ID de la réservation à supprimer: ");
            long reservationId = scanner.nextLong();
            reservationService.delete(reservationId);

            System.out.println("Réservation supprimée avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }
}
