package com.epf.rentmanager.model;
import org.springframework.aop.ClassFilter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.Temporal;

// Classe Client représentant un client dans la base de données
public class Client {
    public int id;
    public String nom;
    public String prenom;
    public String email;
    public LocalDate dateNaissance;


    public Client(int i, String nom, String prenom, String email, LocalDate naissance) {

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


    public CharSequence nom() {
        return null;
    }

    public CharSequence prenom() {
        return null;
    }

    public ClassFilter email() {
        return null;
    }

    public Instant naissance() {
        return null;
    }
}

