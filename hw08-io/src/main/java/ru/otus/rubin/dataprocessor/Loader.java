package ru.otus.rubin.dataprocessor;

import ru.otus.rubin.model.Measurement;

import java.io.IOException;
import java.util.List;

public interface Loader {
    List<Measurement> load() throws IOException;
}
