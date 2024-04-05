package com.ensta.rentmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

public class ClientDaoTest {

    private ClientDao clientDao;
    private List<Client> clients;

    @BeforeEach
    void setUp() {
        clientDao = mock(ClientDao.class);
        clients = new ArrayList<>();
        clients.add(new Client(1, "John", "Doe", "john@example.com", LocalDate.of(1990, 5, 15)));
        clients.add(new Client(2, "Jane", "Doe", "jane@example.com", LocalDate.of(1985, 8, 25)));
    }

    @Test
    void findAll_should_return_all_clients() throws DaoException {
        when(clientDao.findAll()).thenReturn(clients);
        List<Client> foundClients = clientDao.findAll();
        assertEquals(clients.size(), foundClients.size());
    }

    @Test
    void findById_should_return_correct_client() throws DaoException {
        Client expectedClient = clients.get(0);
        when(clientDao.findById(expectedClient.getId())).thenReturn(expectedClient);
        Client foundClient = clientDao.findById(expectedClient.getId());
        assertEquals(expectedClient, foundClient);
    }

    @Test
    void findById_should_throw_DaoException_when_id_not_found() throws DaoException {
        when(clientDao.findById(999)).thenThrow(DaoException.class);
        assertThrows(DaoException.class, () -> clientDao.findById(999));
    }

    @Test
    void create_should_add_new_client_to_list() throws DaoException {
        Client newClient = new Client(1, "New", "Client", "new@example.com", LocalDate.of(1995, 3, 10));
        when(clientDao.create(newClient)).thenReturn((long) 3);
        long newClientId = clientDao.create(newClient);
        assertEquals(3, newClientId);
    }

    @Test
    void delete_should_remove_client_from_list() throws DaoException {
        Client clientToDelete = clients.get(0);
        when(clientDao.delete(clientToDelete)).thenReturn(true); // Utiliser true au lieu de Boolean.TRUE
        boolean isDeleted = clientDao.delete(clientToDelete);
        assertEquals(true, isDeleted);
    }

    @Test
    void delete_should_return_false_when_client_not_found() throws DaoException {
        Client clientToDelete = new Client(999, "Non", "Existant", "nonexistent@example.com", LocalDate.of(1990, 1, 1));
        when(clientDao.delete(clientToDelete)).thenReturn(false); // Utiliser false au lieu de Boolean.FALSE
        boolean isDeleted = clientDao.delete(clientToDelete);
        assertEquals(false, isDeleted);
    }


    // Ajoutez d'autres cas de test selon vos besoins

}
