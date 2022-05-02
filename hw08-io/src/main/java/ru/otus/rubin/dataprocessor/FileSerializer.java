package ru.otus.rubin.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream out = new FileOutputStream(fileName,false)) {
            out.write(mapper.writeValueAsString(data).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //формирует результирующий json и сохраняет его в файл
    }
}
