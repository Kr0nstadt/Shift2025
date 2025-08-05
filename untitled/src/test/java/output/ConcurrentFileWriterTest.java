package output;

import model.DataEntry;
import model.FloatData;
import model.IntegerData;
import model.StringData;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ConcurrentFileWriterTest {
    private static final String TEST_FILE = "test_output.txt";
    private OverwriteFileWriter writer;

    @BeforeEach
    void setUp() {
        writer = new OverwriteFileWriter(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    void shouldWriteIntegerData() throws IOException {
        List<DataEntry> data = List.of(new IntegerData(42));
        writer.writeData(data);

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, lines.size());
        assertEquals("42", lines.get(0));
    }

    @Test
    void shouldWriteFloatData() throws IOException {
        List<DataEntry> data = List.of(new FloatData(3.14));
        writer.writeData(data);

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, lines.size());
        assertEquals("3.14", lines.get(0));
    }

    @Test
    void shouldWriteStringData() throws IOException {
        List<DataEntry> data = List.of(new StringData("test"));
        writer.writeData(data);

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, lines.size());
        assertEquals("test", lines.get(0));
    }

    @Test
    void shouldNotWriteEmptyData() throws IOException {
        writer.writeData(Collections.emptyList());
        assertFalse(Files.exists(Paths.get(TEST_FILE)));
    }

    @Test
    void shouldOverwriteExistingFile() throws IOException {
        Files.write(Paths.get(TEST_FILE), List.of("old content"));

        List<DataEntry> data = List.of(new IntegerData(1));
        writer.writeData(data);

        List<String> lines = Files.readAllLines(Paths.get(TEST_FILE));
        assertEquals(1, lines.size());
        assertEquals("1", lines.get(0));
    }
}