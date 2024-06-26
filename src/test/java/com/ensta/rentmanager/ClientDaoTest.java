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
import java.util.Optional;

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
        Optional<Client> foundClientOpt = clientDao.findById(client.getId());
        assertTrue(foundClientOpt.isPresent(), "Le client devrait être trouvé par son ID.");
        foundClientOpt.ifPresent(foundClient -> {
            assertEquals(client.getEmail(), foundClient.getEmail(), "Les emails devraient correspondre.");
        });
    }

    @Test
    void testUpdateClient() throws Exception {
        client.setNom("Updated Name");
        clientDao.update(client);
        Optional<Client> updatedClientOpt = clientDao.findById(client.getId());
        assertTrue(updatedClientOpt.isPresent(), "Le client mis à jour devrait être trouvé par son ID.");
        updatedClientOpt.ifPresent(updatedClient -> {
            assertEquals("Updated Name", updatedClient.getNom(), "Le nom du client devrait être mis à jour.");
        });
    }

    @Test
    void testDeleteClient() throws Exception {
        clientDao.delete(client.getId());
        assertNull(clientDao.findById(client.getId()), "Le client devrait être supprimé.");
    }
}
