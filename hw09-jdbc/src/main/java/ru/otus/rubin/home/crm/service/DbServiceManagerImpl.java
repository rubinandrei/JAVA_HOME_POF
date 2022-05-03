package ru.otus.rubin.home.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.rubin.home.core.repository.DataTemplate;
import ru.otus.rubin.home.core.sessionmanager.TransactionManager;
import ru.otus.rubin.home.crm.model.Manager;

import java.util.List;
import java.util.Optional;

public class DbServiceManagerImpl implements DBServiceManager {
    private static final Logger log = LoggerFactory.getLogger(DbServiceManagerImpl.class);

    private final DataTemplate<Manager> managerDataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceManagerImpl(TransactionManager transactionManager, DataTemplate<Manager> managerDataTemplate) {
        this.transactionManager = transactionManager;
        this.managerDataTemplate = managerDataTemplate;
    }

    @Override
    public Manager saveManager(Manager manager) {
        return transactionManager.doInTransaction(connection -> {
            if (manager.getNo() == null) {
                var managerNo = managerDataTemplate.insert(connection, manager);
                var createdManager = new Manager(managerNo, manager.getLabel());
                log.info("created manager: {}", createdManager);
                return createdManager;
            }
            managerDataTemplate.update(connection, manager);
            log.info("updated manager: {}", manager);
            return manager;
        });
    }

    @Override
    public Optional<Manager> getManager(long no) {
        return transactionManager.doInTransaction(connection -> {
            var managerOptional = managerDataTemplate.findById(connection, no);
            log.info("test: {}", managerOptional);
            return managerOptional;
        });
    }

    @Override
    public List<Manager> findAll() {
        return transactionManager.doInTransaction(connection -> {
            var managerList = managerDataTemplate.findAll(connection);
            log.info("TestList:{}", managerList);
            return managerList;
        });
    }
}
