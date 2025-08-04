package input;

import model.DataEntry;
import model.DataType;
import valid.DataEntryParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConcurrentFileReader implements DataFileReader {
    public List<DataEntry> readFile(String filePath) throws IOException {
        DataEntryParser parser = new DataEntryParser();
        List<DataEntry> entries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                DataEntry entry = parser.parse(line.trim());
                entries.add(entry);
            }
        }

        return entries;
    }
}