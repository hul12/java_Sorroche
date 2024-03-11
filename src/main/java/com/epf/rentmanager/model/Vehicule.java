package com.epf.rentmanager.model;

// Classe Vehicule représentant un véhicule dans la base de données
public class Vehicule {
    private long id;
    private String constructeur;
    private String modele;
    private int nbPlaces;

    // Constructeur
    public Vehicule(String constructeur, String modele, int nbPlaces) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nbPlaces = nbPlaces;
    }
    public long getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    // Méthode toString() pour afficher les informations du véhicule
    @Override
    public String toString() {
        return "Vehicule [id=" + id + ", constructeur=" + constructeur + ", modele=" + modele + ", nbPlaces=" + nbPlaces + "]";
    }
}

