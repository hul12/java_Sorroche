package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("constructeur");
        String modele = request.getParameter("modele");
        int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));

        Vehicule vehicule = new Vehicule();
        vehicule.setConstructeur(constructeur);
        vehicule.setModele(modele);
        vehicule.setNbPlaces(nbPlaces);

        try {
            vehicleService.create(vehicule);
            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (DaoException e) {
            request.setAttribute("errorMessage", "Erreur lors de la création du véhicule: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
        }
    }
}
