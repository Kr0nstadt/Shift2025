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

//    @Test
//    void testOverwriteFileWriterCreatesFiles(@TempDir Path tempDir) throws IOException, InterruptedException {
//        Config config = new Config(tempDir.toString(), "", false, false, false, null);
//        OverwriteFileWriter writer = new OverwriteFileWriter();
//
//        List<DataEntry> testData = Arrays.asList(
//                new IntegerData(123),
//                new FloatData(45.67f),
//                new StringData("test")
//        );
//
//        writer.writeData(testData, config);
//
//        Thread.sleep(100);
//
//        assertTrue(Files.exists(tempDir.resolve("integers.txt")));
//        assertTrue(Files.exists(tempDir.resolve("floats.txt")));
//        assertTrue(Files.exists(tempDir.resolve("strings.txt")));
//
//        assertEquals("123", Files.readAllLines(tempDir.resolve("integers.txt")).get(0));
//        assertEquals("45.67", Files.readAllLines(tempDir.resolve("floats.txt")).get(0));
//        assertEquals("test", Files.readAllLines(tempDir.resolve("strings.txt")).get(0));
//    }

//    @Test
//    void testAppendFileWriterAppendsData(@TempDir Path tempDir) throws Exception {
//        Config config = new Config(tempDir.toString(), "", true, false, false, null);
//        AppendFileWriter writer = new AppendFileWriter();
//
//        Files.write(tempDir.resolve("integers.txt"), "100".getBytes());
//
//        List<DataEntry> testData = List.of(new IntegerData(200));
//
//        writer.writeData(testData, config);
//
//        Thread.sleep(100);
//
//        List<String> lines = Files.readAllLines(tempDir.resolve("integers.txt"));
//        assertEquals(2, lines.size());
//        assertEquals("100", lines.get(0));
//        assertEquals("200", lines.get(1));
//    }

    @Test
    void testFileWriterFactory() {
        assertInstanceOf(AppendFileWriter.class, FileWriterFactory.getFileWriter(true));
        assertInstanceOf(OverwriteFileWriter.class, FileWriterFactory.getFileWriter(false));
    }

//    @Test
//    void testCustomPrefix(@TempDir Path tempDir) throws Exception {
//        Config config = new Config(tempDir.toString(), "custom_", false, false, false, null);
//        OverwriteFileWriter writer = new OverwriteFileWriter();
//
//        writer.writeData(List.of(new IntegerData(123)), config);
//
//        Thread.sleep(100);
//        assertTrue(Files.exists(tempDir.resolve("custom_integers.txt")));
//    }

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