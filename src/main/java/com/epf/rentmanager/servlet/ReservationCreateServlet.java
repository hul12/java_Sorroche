package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.ClientService;
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
import java.time.LocalDate;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("users", clientService.findAll());
            request.setAttribute("vehicles", vehicleService.findAll());
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (DaoException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du chargement des données");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long clientId = Long.parseLong(request.getParameter("clientId"));
            long vehicleId = Long.parseLong(request.getParameter("vehicleId"));
            LocalDate debut = LocalDate.parse(request.getParameter("debut"));
            LocalDate fin = LocalDate.parse(request.getParameter("fin"));

            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
            reservationService.create(reservation);

            response.sendRedirect(request.getContextPath() + "/rents");
        } catch (DaoException e) {
            request.setAttribute("errorMessage", "Erreur lors de la création de la réservation: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Format de l'ID client ou véhicule invalide.");
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        }
    }
}

