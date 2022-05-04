package ru.otus.rubin.home;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.rubin.home.core.repository.executor.DbExecutorImpl;
import ru.otus.rubin.home.core.sessionmanager.TransactionManagerJdbc;
import ru.otus.rubin.home.crm.datasource.DriverManagerDataSource;
import ru.otus.rubin.home.crm.model.Client;
import ru.otus.rubin.home.crm.model.Manager;
import ru.otus.rubin.home.crm.service.DbServiceClientImpl;
import ru.otus.rubin.home.crm.service.DbServiceManagerImpl;
import ru.otus.rubin.home.jdbc.mapper.DataTemplateJdbc;
import ru.otus.rubin.home.jdbc.mapper.EntityClassMetaData;
import ru.otus.rubin.home.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.rubin.home.jdbc.mapper.EntitySQLMetaData;
import ru.otus.rubin.home.jdbc.mapper.EntitySQLMetaDataImpl;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5432/pgdata";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) throws NoSuchMethodException {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);

        flywayMigrations(dataSource);
        var transactionManager = new TransactionManagerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient);

        // Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionManager, dataTemplateClient);
        dbServiceClient.saveClient(new Client("ClientFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("ClientSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);


        System.out.println("------------------------------------------------------");
// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)
        EntityClassMetaData entityClassMetaDataManager = new EntityClassMetaDataImpl(Manager.class);
        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor, entitySQLMetaDataManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionManager, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, no:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);

        dbServiceManager.findAll().forEach( m-> {
            log.info(m.toString());
        });
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
