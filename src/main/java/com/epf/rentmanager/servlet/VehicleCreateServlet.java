package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;

    public VehicleService getVehicleService() {
        return vehicleService;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Affichage du formulaire de création de véhicule
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        int seats = Integer.parseInt(request.getParameter("seats"));

        // Création de l'objet Vehicule avec les données du formulaire
        Vehicule vehicule = new Vehicule(manufacturer, model, seats);

        // Appel à la méthode de sauvegarde dans la base de données
        try {
            long id = vehicleService.create(vehicule);
            response.sendRedirect(request.getContextPath() + "/vehicles"); // Redirection vers la liste des véhicules après création
        } catch (ServiceException e) {
            // Gestion des erreurs
            request.setAttribute("errorMessage", "Erreur lors de la création du véhicule : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
        }
    }
}

