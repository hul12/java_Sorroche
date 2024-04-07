package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/update")
public class VehicleUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cars");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            Vehicule vehicle = vehicleService.findById(id);
            if (vehicle != null) {
                request.setAttribute("vehicle", vehicle);

            } else {
                response.sendRedirect(request.getContextPath() + "/cars");
            }
        } catch (Exception e) {
            IOUtils.print(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String constructeur = request.getParameter("constructeur");
            String modele = request.getParameter("modele");
            int nbPlaces = Integer.parseInt(request.getParameter("nbPlaces"));

            Vehicule vehicle = new Vehicule(id, constructeur, modele, nbPlaces);
            vehicleService.update(vehicle);

        } catch (Exception e) {
            IOUtils.print(e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}
