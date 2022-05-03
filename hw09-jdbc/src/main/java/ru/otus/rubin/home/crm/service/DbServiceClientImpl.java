package ru.otus.rubin.home.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.otus.rubin.home.core.repository.DataTemplate;
import ru.otus.rubin.home.core.sessionmanager.TransactionManager;
import ru.otus.rubin.home.crm.model.Client;

import java.util.List;
import java.util.Optional;


public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(connection -> {
            if (client.getId() == null) {
                //long clientId = 0;
//                try {
                long clientId = clientDataTemplate.insert(connection, client);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
