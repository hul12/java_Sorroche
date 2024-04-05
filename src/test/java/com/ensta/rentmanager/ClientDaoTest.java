package com.ensta.rentmanager;


import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClientDaoTest {

    @Autowired
    private ClientDao clientDao;

    private Client client;

    @BeforeEach
    void setUp() {

        this.client = new Client("Test", "User", "test@example.com", LocalDate.of(1990, 1, 1));

    }

    @Test
    void testCreateClient() {
        assertNotNull(client.getId(), "Le client devrait avoir un ID non nul après l'insertion.");
    }

    @Test
    void testFindClientById() throws Exception {
        Client foundClient = clientDao.findById(client.getId());
        assertNotNull(foundClient, "Le client devrait être trouvé par son ID.");
        assertEquals(client.getEmail(), foundClient.getEmail(), "Les emails devraient correspondre.");
    }

    @Test
    void testUpdateClient() throws Exception {
        client.setNom("Updated Name");
        clientDao.update(client);
        Client updatedClient = clientDao.findById(client.getId());
        assertEquals("Updated Name", updatedClient.getNom(), "Le nom du client devrait être mis à jour.");
    }

    @Test
    void testDeleteClient() throws Exception {
        clientDao.delete(client.getId());
        assertNull(clientDao.findById(client.getId()), "Le client devrait être supprimé.");
    }
}
