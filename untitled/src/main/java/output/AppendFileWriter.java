package output;


import config.Config;
import model.DataEntry;
import model.DataType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppendFileWriter implements FileWriterData {
    @Override
    public void writeData(List<DataEntry> data, Config config) {
        if (data == null || data.isEmpty()) return;

        Map<DataType, BufferedWriter> writers = new HashMap<>();

        try {
            for (DataEntry entry : data) {
                DataType type = entry.getType();
                if (!writers.containsKey(type)) {
                    String fileName = buildFileName(config, type);
                    File file = new File(fileName);
                    file.getParentFile().mkdirs();
                    writers.put(type, new BufferedWriter(new FileWriter(file, true)));
                }
            }

            for (DataEntry entry : data) {
                BufferedWriter writer = writers.get(entry.getType());
                writer.write(entry.getValueAsString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            for (BufferedWriter writer : writers.values()) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String buildFileName(Config config, DataType type) {
        String path = config.getOutputPath() != null ? config.getOutputPath() : "";
        String prefix = config.getFilePrefix() != null ? config.getFilePrefix() : "";
        String defaultName = type.toString().toLowerCase() + ".txt";

        return path + File.separator + prefix + defaultName;
    }
}