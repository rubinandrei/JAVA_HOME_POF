package ru.otus.rubin.home.jdbc.mapper;

import ru.otus.rubin.home.core.repository.DataTemplate;
import ru.otus.rubin.home.core.repository.DataTemplateException;
import ru.otus.rubin.home.core.repository.executor.DbExecutor;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            Field entityField = null;
            try {
                if (rs.next()) {
                    entityField = EntitySQLMetaDataImpl.class.getDeclaredField("entity");
                    entityField.setAccessible(true);
                    EntityClassMetaData entityClassMetaData = (EntityClassMetaData) entityField.get(entitySQLMetaData);
                    var newObject = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields = entityClassMetaData.getAllFields();
                    for (Field field :fields) {
                        var value = rs.getObject(field.getName());
                        System.out.println("adding field " + field);
                        field.setAccessible(true);
                        field.set(newObject, value);
                        field.setAccessible(false);
                    }
                    return (T) newObject;
                }
                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }finally {
                if(entityField!=null){
                    entityField.setAccessible(false);
                }
            }

        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var objectsList = new ArrayList<T>();
            Field entityField = null;
            try {
                while (rs.next()) {
                    entityField = EntitySQLMetaDataImpl.class.getDeclaredField("entity");
                    entityField.setAccessible(true);
                    EntityClassMetaData entityClassMetaData = (EntityClassMetaData) entityField.get(entitySQLMetaData);
                    var newObject = entityClassMetaData.getConstructor().newInstance();
                    List<Field> fields  = entityClassMetaData.getAllFields();
                    for (Field field : fields) {
                        var value = rs.getObject(field.getName());
                        field.setAccessible(true);
                        field.set(newObject, value);
                        field.setAccessible(false);
                    }
                    objectsList.add((T) newObject);
                    entityField.setAccessible(false);
                }
                return objectsList;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            } finally {
                if (entityField != null) {
                    entityField.setAccessible(false);
                }
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T value) {
        long out = -1;
        Field entityField = null;
        try {
            entityField = EntitySQLMetaDataImpl.class.getDeclaredField("entity");
            entityField.setAccessible(true);
            EntityClassMetaData entityClassMetaData = (EntityClassMetaData) entityField.get(entitySQLMetaData);
            List<Field> fields = entityClassMetaData.getFieldsWithoutId();
            var values = new ArrayList<>(fields.size());
            for (var field : fields) {
                field.setAccessible(true);
                values.add(field.get(value));
                field.setAccessible(false);
            }
            out = dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
        } catch (Exception e) {
            System.out.println("DataTemplateJdbc.insert\t" + e);
        } finally {
            if(entityField != null) {
                entityField.setAccessible(false);
            }
        }
        return out;
    }

    @Override
    public void update(Connection connection, T value) {
        Field entityUpdateField = null;
        try {
            entityUpdateField = EntitySQLMetaDataImpl.class.getDeclaredField("entity");
            entityUpdateField.setAccessible(true);
            EntityClassMetaData metaData = (EntityClassMetaData) entityUpdateField.get(entitySQLMetaData);
            List<Field> fields = metaData.getFieldsWithoutId();
            var values = new ArrayList<>(fields.size());
            for (var field : fields) {
                field.setAccessible(true);
                values.add(field.get(value));
                field.setAccessible(false);
            }
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
        } catch (Exception e) {
            System.out.println("DataTemplateJdbc.update\t" + e);
        } finally {
            if(entityUpdateField != null) {
                entityUpdateField.setAccessible(false);
            }
        }
    }

}
