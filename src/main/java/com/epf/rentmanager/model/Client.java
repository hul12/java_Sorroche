package com.epf.rentmanager.model;
import java.time.LocalDate;

// Classe Client représentant un client dans la base de données
public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;

    // structur
    public Client(int i, String nom, String prenom, String email, LocalDate naissance) {
        this.id = this.id;
        this.nom = this.nom;
        this.prenom = this.prenom;
        this.email = this.email;
        this.dateNaissance = dateNaissance;
    }

    public Client() {

    }

    public long getId() {
        return id;
    }

    public String getNom() { return nom;}

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    //  afficher les informations du client
    @Override
    public String toString() {
        return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", dateNaissance=" + dateNaissance + "]";
    }



}

