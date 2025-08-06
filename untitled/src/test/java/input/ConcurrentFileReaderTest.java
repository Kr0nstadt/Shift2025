package input;

import model.DataEntry;
import model.DataType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import valid.DataEntryParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ConcurrentFileReaderTest {
    @Mock
    private DataEntryParser parser;

    private ConcurrentFileReader fileReader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileReader = new ConcurrentFileReader(parser);
    }

    @Test
    void readFile_shouldReturnListOfEntries_whenFileContainsData() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, List.of("line1", "line2", "line3"));

        when(parser.parse("line1")).thenReturn(createDataEntry("line1"));
        when(parser.parse("line2")).thenReturn(createDataEntry("line2"));
        when(parser.parse("line3")).thenReturn(createDataEntry("line3"));

        List<DataEntry> result = fileReader.readFile(testFile.toString());

        assertEquals(3, result.size());
        assertEquals("line1", result.get(0).getValueAsString());
        assertEquals("line2", result.get(1).getValueAsString());
        assertEquals("line3", result.get(2).getValueAsString());
    }

    @Test
    void readFile_shouldSkipEmptyLines() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, List.of("", "data", "  ", "\t"));

        when(parser.parse("data")).thenReturn(createDataEntry("data"));

        List<DataEntry> result = fileReader.readFile(testFile.toString());

        assertEquals(1, result.size());
        assertEquals("data", result.get(0).getValueAsString());
    }

    @Test
    void readFile_shouldThrowException_whenFileDoesNotExist() {
        String nonExistentFile = tempDir.resolve("nonexistent.txt").toString();
        assertThrows(IOException.class, () -> fileReader.readFile(nonExistentFile));
    }

    @Test
    void readFile_shouldReturnEmptyList_whenFileIsEmpty() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.createFile(emptyFile);

        List<DataEntry> result = fileReader.readFile(emptyFile.toString());

        assertEquals(0, result.size());
    }

    @Test
    void readFile_shouldCorrectlyParseMixedContent() throws IOException {
        Path testFile = tempDir.resolve("mixed.txt");
        Files.write(testFile, List.of(
                "Lorem ipsum dolor sit amet",
                "45",
                "Пример",
                "3.1415",
                "consectetur adipiscing -0.001",
                "тестовое задание",
                "100500"
        ));

        when(parser.parse("Lorem ipsum dolor sit amet")).thenReturn(createDataEntry("Lorem ipsum dolor sit amet"));
        when(parser.parse("45")).thenReturn(createDataEntry("45"));
        when(parser.parse("Пример")).thenReturn(createDataEntry("Пример"));
        when(parser.parse("3.1415")).thenReturn(createDataEntry("3.1415"));
        when(parser.parse("consectetur adipiscing -0.001")).thenReturn(createDataEntry("consectetur adipiscing -0.001"));
        when(parser.parse("тестовое задание")).thenReturn(createDataEntry("тестовое задание"));
        when(parser.parse("100500")).thenReturn(createDataEntry("100500"));

        List<DataEntry> result = fileReader.readFile(testFile.toString());

        assertEquals(7, result.size());
        assertEquals("Lorem ipsum dolor sit amet", result.get(0).getValueAsString());
        assertEquals("45", result.get(1).getValueAsString());
        assertEquals("Пример", result.get(2).getValueAsString());
        assertEquals("3.1415", result.get(3).getValueAsString());
        assertEquals("consectetur adipiscing -0.001", result.get(4).getValueAsString());
        assertEquals("тестовое задание", result.get(5).getValueAsString());
        assertEquals("100500", result.get(6).getValueAsString());
    }

    @Test
    void readFile_shouldHandleEdgeCases() throws IOException {
        Path testFile = tempDir.resolve("edge_cases.txt");
        Files.write(testFile, List.of(
                "0",
                "-0",
                "0.0",
                "123.",
                ".456",
                "1e3",
                "0xFF"
        ));

        when(parser.parse("0")).thenReturn(createDataEntry("0"));
        when(parser.parse("-0")).thenReturn(createDataEntry("0"));
        when(parser.parse("0.0")).thenReturn(createDataEntry("0.0"));
        when(parser.parse("123.")).thenReturn(createDataEntry("123.0"));
        when(parser.parse(".456")).thenReturn(createDataEntry("0.456"));
        when(parser.parse("1e3")).thenReturn(createDataEntry("1000.0"));
        when(parser.parse("0xFF")).thenReturn(createDataEntry("0xFF"));

        List<DataEntry> result = fileReader.readFile(testFile.toString());

        assertEquals(7, result.size());
        assertEquals("0", result.get(0).getValueAsString());
        assertEquals("0", result.get(1).getValueAsString());
        assertEquals("0.0", result.get(2).getValueAsString());
        assertEquals("123.0", result.get(3).getValueAsString());
        assertEquals("0.456", result.get(4).getValueAsString());
        assertEquals("1000.0", result.get(5).getValueAsString());
        assertEquals("0xFF", result.get(6).getValueAsString());
    }

    private DataEntry createDataEntry(String value) {
        return new DataEntry() {
            @Override
            public DataType getType() {
                return null;
            }

            @Override
            public String getValueAsString() {
                return value;
            }
        };
    }
}