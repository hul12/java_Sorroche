package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        try {
            long id = Long.parseLong(request.getParameter("id"));
            Optional<Client> client = clientService.findById(id);
            if (client.isPresent()) {
                request.setAttribute("user", client.get());
            } else {
                IOUtils.print("Client not found");
            }
        } catch (ServiceException e) {
            IOUtils.print(e.getMessage());
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les données du formulaire
        long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        LocalDate dateNaissance = LocalDate.parse(request.getParameter("dateNaissance"));

        Client client = new Client(id, nom, prenom, email, dateNaissance);

        try {
            clientService.update(client);
            response.sendRedirect(request.getContextPath() + "/users");
        } catch (Exception e) {
            IOUtils.print(e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
}
