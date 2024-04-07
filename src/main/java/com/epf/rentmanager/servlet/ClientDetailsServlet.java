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
import java.util.Optional;

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
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            Optional<Optional<Client>> optionalClient = Optional.ofNullable(clientService.findById(id));

            if (optionalClient.isPresent()) {
                Optional<Client> client = optionalClient.get();
                request.setAttribute("user", client);
                // Ajouter d'autres attributs ici comme avant
                request.getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
            } else {
                IOUtils.print("Client not found");
            }
        } catch (Exception e) {
            IOUtils.print(e.getMessage());


            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
}
    }
}
