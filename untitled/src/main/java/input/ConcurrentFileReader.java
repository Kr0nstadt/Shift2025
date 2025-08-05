package input;

import model.DataEntry;
import valid.DataEntryParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConcurrentFileReader implements DataFileReader {
    private final DataEntryParser parser;

    public ConcurrentFileReader(DataEntryParser parser) {
        this.parser = parser;
    }

    public List<DataEntry> readFile(String filePath) throws IOException {
        List<DataEntry> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    DataEntry entry = parser.parse(line);
                    entries.add(entry);
                }
            }
        }

        return entries;
    }
}