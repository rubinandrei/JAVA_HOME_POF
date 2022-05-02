package ru.otus.rubin.dataprocessor;

import com.google.gson.Gson;
import ru.otus.rubin.model.Measurement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        String read = new String(Files.readAllBytes(Paths.get(fileName)));
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(read, Measurement[].class));
    }
}
