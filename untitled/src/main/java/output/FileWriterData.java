package output;

import config.Config;
import model.DataEntry;

import java.util.List;

public interface FileWriterData {
    void writeData(List<DataEntry> dataEntries, Config config);
}
