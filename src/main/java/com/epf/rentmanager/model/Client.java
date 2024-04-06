package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {

    private long id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;


    public Client() {
    }


    public Client(long id, String nom, String prenom, String email, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public Client(String nom, String prenom, String email, LocalDate dateNaissance) {
        this(0, nom, prenom, email, dateNaissance);
    }

    public Client(long id) {
        this(id, "", "", "", LocalDate.now());
    }
    public Client(String nom, String prenom, String email) {
        this((long) 0, nom, prenom, email, LocalDate.now());
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }
}
