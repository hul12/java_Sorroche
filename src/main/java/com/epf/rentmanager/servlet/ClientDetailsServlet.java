package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/clients");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            Client client = clientService.findById(id);
            request.setAttribute("users", client);
            request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (DaoException | NumberFormatException e) {
            request.setAttribute("errorMessage", "Erreur lors de la récupération des détails du client: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        }
    }
}
