package com.epf.rentmanager.model;

public class Vehicule {

    private long id;
    private String constructeur;
    private String modele;
    private int nbPlaces;


    public Vehicule() {
    }


    public Vehicule(long id, String constructeur, String modele, int nbPlaces) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nbPlaces = nbPlaces;
    }

    // Constructeur sans ID (si déjà existant, ne changez rien)
    public Vehicule(String constructeur, String modele, int nbPlaces) {
        this.constructeur = constructeur;
        this.modele = modele;
        this.nbPlaces = nbPlaces;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }


    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nbPlaces=" + nbPlaces +
                '}';
    }
}
