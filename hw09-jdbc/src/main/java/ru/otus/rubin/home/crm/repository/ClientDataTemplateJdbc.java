package ru.otus.rubin.home.crm.repository;

import ru.otus.rubin.home.core.repository.DataTemplate;
import ru.otus.rubin.home.core.repository.DataTemplateException;
import ru.otus.rubin.home.core.repository.executor.DbExecutor;
import ru.otus.rubin.home.crm.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientDataTemplateJdbc implements DataTemplate<Client> {
    private final DbExecutor dbExecutor;

    public ClientDataTemplateJdbc(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Optional<Client> findById(Connection connection, long id) {
        System.out.println("was sel\t" + "select id, name from client where id  = ?");
        return dbExecutor.executeSelect(connection, "select id, name from client where id  = ?", List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return new Client(rs.getLong("id"), rs.getString("name"));
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<Client> findAll(Connection connection) {
        System.out.println("was all\t" + "select * from client");
        return dbExecutor.executeSelect(connection, "select * from client", Collections.emptyList(), rs -> {
            var clientList = new ArrayList<Client>();
            try {
                while (rs.next()) {
                    clientList.add(new Client(rs.getLong("id"), rs.getString("name")));
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, Client client) {
        System.out.println("was ins\t" + "insert into client(name) values (?)");
        try {
            return dbExecutor.executeStatement(connection, "insert into client(name) values (?)",
                    Collections.singletonList(client.getName()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, Client client) {
        System.out.println("was upd\t" + "update client set name = ? where id = ?");
        try {
            dbExecutor.executeStatement(connection, "update client set name = ? where id = ?",
                    List.of(client.getName(), client.getId()));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
