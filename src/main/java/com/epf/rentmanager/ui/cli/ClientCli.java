package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class ClientCli {

    @Autowired
    private ClientService clientService;

    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("Gestion des clients:");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Afficher tous les clients");
            System.out.println("3. Supprimer un client");
            System.out.println("4. Quitter");

            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listClients();
                    break;
                case 3:
                    deleteClient();
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

    private void createClient() {
        try {
            System.out.print("Entrez le nom: ");
            String nom = scanner.nextLine();

            System.out.print("Entrez le prénom: ");
            String prenom = scanner.nextLine();

            System.out.print("Entrez l'email: ");
            String email = scanner.nextLine();

            System.out.print("Entrez la date de naissance (YYYY-MM-DD): ");
            String dob = scanner.nextLine();
            LocalDate dateOfBirth = LocalDate.parse(dob);

            Client client = new Client(0, nom, prenom, email, dateOfBirth);
            clientService.create(client);

            System.out.println("Client ajouté avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de l'ajout du client: " + e.getMessage());
        }
    }

    private void listClients() {
        try {
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (DaoException e) {
            System.err.println("Erreur lors de la récupération des clients: " + e.getMessage());
        }
    }

    private void deleteClient() {
        try {
            System.out.print("Entrez l'ID du client à supprimer: ");
            long id = scanner.nextLong();
            clientService.delete(id);
            System.out.println("Client supprimé avec succès.");
        } catch (DaoException e) {
            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
        }
    }
}
