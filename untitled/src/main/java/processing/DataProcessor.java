package processing;


import config.Config;
import input.ConcurrentFileReader;
import input.DataFileReader;
import model.DataEntry;
import output.FileWriterData;
import output.FileWriterFactory;
import statistic.StatisticsCollector;
import valid.DataEntryParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DataProcessor {
    private final Config config;
    private final DataEntryParser parser;
    private final DataFileReader fileReader;
    private final FileWriterData fileWriter;
    private final StatisticsCollector statisticsCollector;

    public DataProcessor(Config config) {
        this.config = config;
        this.parser = new DataEntryParser();
        this.fileReader = new ConcurrentFileReader(parser);
        this.fileWriter = FileWriterFactory.getFileWriter(config.isAppendMode());
        this.statisticsCollector = new StatisticsCollector(config.isShortStatistics(), config.isFullStatistics());
    }

    public void process() {
        List<DataEntry> allEntries = readAllFiles();
        if (allEntries.isEmpty()) {
            System.out.println("No data to process");
            return;
        }

        fileWriter.writeData(allEntries, config);
        statisticsCollector.printStatistics(allEntries);
    }

    private List<DataEntry> readAllFiles() {
        List<DataEntry> allEntries = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<List<DataEntry>>> futures = new ArrayList<>();

        for (String filePath : config.getInputFiles()) {
            futures.add(executor.submit(() -> {
                try {
                    return fileReader.readFile(filePath);
                } catch (IOException e) {
                    System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                    return Collections.<DataEntry>emptyList();
                }
            }));
        }

        for (Future<List<DataEntry>> future : futures) {
            try {
                allEntries.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error processing file: " + e.getMessage());
            }
        }

        executor.shutdown();
        return allEntries;
    }
}
