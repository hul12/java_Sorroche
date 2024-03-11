package com.epf.rentmanager.ui.cli;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;

public class ClientCli {
    private static ClientService clientService = ClientService.getInstance();
    private static Scanner scanner = new Scanner(System.in);


    public ClientCli(Scanner scanner) {
        this.scanner = scanner;
        this.clientService = ClientService.getInstance();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("Gestion des clients:");
            System.out.println("1. Créer un client");
            System.out.println("2. Lister tous les clients");
            System.out.println("3. Supprimer un client (Bonus)");
            System.out.println("4. Retour au menu principal");
            System.out.print("Choix : ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createClient();
                    break;
                case "2":
                    listClients();
                    break;
                case "3":
                    deleteClient();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        }
    }

    public static void createClient() {
        System.out.println("Création d'un nouveau client:");
        try {
            System.out.print("Nom : ");
            String nom = scanner.nextLine().trim();
            System.out.print("Prénom : ");
            String prenom = scanner.nextLine().trim();
            System.out.print("Email : ");
            String email = scanner.nextLine().trim();
            System.out.print("Date de naissance (yyyy-mm-dd) : ");
            LocalDate naissance = IOUtils.readLocalDate();

            Client client = new Client(nom, prenom, email, naissance);
            long id = clientService.create(client);
            System.out.println("Le client a été créé avec l'ID " + id);
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la création du client : " + e.getMessage());
        }
    }

    public static void listClients() {
        System.out.println("Liste de tous les clients:");
        try {
            List<Client> clients = clientService.findAll();
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (ServiceException e) {
            System.out.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }
    }

    public static void deleteClient() {
        System.out.println("Suppression d'un client :");
        System.out.print("Identifiant du client à supprimer : ");
        long id = IOUtils.readLong();
        clientService.delete(id);
        System.out.println("Client avec l'identifiant " + id + " supprimé avec succès.");
    }
}
