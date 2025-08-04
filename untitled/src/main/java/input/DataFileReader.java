package input;

import model.DataEntry;

import java.io.IOException;
import java.util.List;

public interface DataFileReader {
    List<DataEntry> readFile(String filePath) throws IOException;
}