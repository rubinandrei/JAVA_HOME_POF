package ru.otus.rubin.dataprocessor;

import ru.otus.rubin.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {
    Map<String, Double> process(List<Measurement> data);
}
