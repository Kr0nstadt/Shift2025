package output;

import config.Config;
import model.DataEntry;
import model.DataType;
import model.IntegerData;
import model.FloatData;
import model.StringData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {
    @Test
    void testFileWriterFactory() {
        assertInstanceOf(AppendFileWriter.class, FileWriterFactory.getFileWriter(true));
        assertInstanceOf(OverwriteFileWriter.class, FileWriterFactory.getFileWriter(false));
    }

    @Test
    void testEmptyDataDoesNotCreateFiles(@TempDir Path tempDir) {
        Config config = new Config(tempDir.toString(), "", false, false, false, null);
        OverwriteFileWriter writer = new OverwriteFileWriter();

        writer.writeData(List.of(), config);

        assertFalse(Files.exists(tempDir.resolve("integers.txt")));
        assertFalse(Files.exists(tempDir.resolve("floats.txt")));
        assertFalse(Files.exists(tempDir.resolve("strings.txt")));
    }
}