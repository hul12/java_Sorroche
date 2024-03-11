package com.epf.rentmanager.model;

import java.time.LocalDate;



// Classe Reservation représentant une réservation dans la base de données
public class Reservation {
    private long id;
    private long clientId;
    private long vehiculeId;
    private LocalDate debut;
    private LocalDate fin;

    // Constructeur
    public Reservation(long id, long clientId, long vehiculeId, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.clientId = clientId;
        this.vehiculeId = vehiculeId;
        this.debut = debut;
        this.fin = fin;
    }
    public long getId() {
        return id;
    }

    public long getClientId() {
        return clientId;
    }

    public long getVehiculeId() {
        return vehiculeId;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public LocalDate getFin() {
        return fin;
    }


    // Méthode toString() pour afficher les informations de la réservation
    @Override
    public String toString() {
        return "Reservation [id=" + id + ", clientId=" + clientId + ", vehiculeId=" + vehiculeId + ", debut=" + debut + ", fin=" + fin + "]";
    }



}

