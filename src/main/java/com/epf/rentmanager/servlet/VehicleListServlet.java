package com.epf.rentmanager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import static com.epf.rentmanager.ui.cli.ClientCli.clientService;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("users", clientService.findAll());
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);

    }
}
