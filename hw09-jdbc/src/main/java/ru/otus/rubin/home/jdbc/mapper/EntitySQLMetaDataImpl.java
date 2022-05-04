package ru.otus.rubin.home.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.rubin.home.crm.annatation.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final Logger log = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    private final EntityClassMetaData entity;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        entity = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        log.info("SELECT * FROM " + entity.getName());
        return "SELECT * FROM " + entity.getName();
    }

    @Override
    public String getSelectByIdSql() {
        if (entity.getFieldsWithoutId().size() == 0) {
            throw new RuntimeException("there are any non-id fields in class");
        }
        List<Field> fields = entity.getAllFields();
        StringBuilder elemetsNcommas = new StringBuilder();
        for (Field field : fields) {
            elemetsNcommas.append(field.getName()).append(",");
        }
        elemetsNcommas.deleteCharAt(elemetsNcommas.length() - 1);
        log.info("select " + elemetsNcommas + " from " + entity.getName() + " where " + entity.getIdField().getName() + "  = ?");
        return "select " + elemetsNcommas + " from " + entity.getName() + " where " + entity.getIdField().getName() + "  = ?";
    }

    @Override
    public String getInsertSql() {
        if (entity.getFieldsWithoutId().size() == 0) {
            throw new RuntimeException("there are any non-id fields in class");
        }
        StringBuilder elemetsNcommas = new StringBuilder();
        StringBuilder valuesNcommas = new StringBuilder();
        boolean prKey = entity.getIdField().getAnnotation(Id.class).primaryKey();
        List<Field> fields = prKey? entity.getFieldsWithoutId():entity.getAllFields();
        for (Field field : fields) {
                elemetsNcommas.append(field.getName()).append(",");
                valuesNcommas.append("?").append(",");
        }
        elemetsNcommas.deleteCharAt(elemetsNcommas.length() - 1);
        valuesNcommas.deleteCharAt(valuesNcommas.length() - 1);
        log.info("insert into " + entity.getName() + "(" + elemetsNcommas + ") values (" + valuesNcommas + ")");
        return "insert into " + entity.getName() + "(" + elemetsNcommas + ") values (" + valuesNcommas + ")";
    }

    @Override
    public String getUpdateSql() {
        if (entity.getFieldsWithoutId().size() == 0) {
            throw new RuntimeException("there are any non-id fields in class");
        }
        StringBuilder mysteryElemetsNcommas = new StringBuilder();
        List<Field> fields = entity.getFieldsWithoutId();
        for (Field field : fields) {
            mysteryElemetsNcommas.append(field.getName()).append(" = ?").append(",");
        }
        mysteryElemetsNcommas.deleteCharAt(mysteryElemetsNcommas.length() - 1);
        log.info("update " + entity.getName() + " set " + mysteryElemetsNcommas + " where " + entity.getIdField() + " = ?");
        return "update " + entity.getName() + " set " + mysteryElemetsNcommas + " where " + entity.getIdField() + " = ?";
    }
}
