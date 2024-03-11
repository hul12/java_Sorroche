package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            VehicleService vehicleService = new VehicleService();
            int vehicleCount = vehicleService.count();
            request.setAttribute("vehicleCount", vehicleCount);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

        }
	}
