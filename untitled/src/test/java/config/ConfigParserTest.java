package config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ConfigParserTest {

    @Test
    void fullConfigParsing() {
        String[] args = {"-o", "/output", "-p", "result_", "-a", "-s", "input1.txt", "input2.txt"};
        Config config = new ConfigParser(args).parse();

        assertAll(
                () -> assertEquals("/output", config.getOutputPath()),
                () -> assertEquals("result_", config.getFilePrefix()),
                () -> assertTrue(config.isAppendMode()),
                () -> assertTrue(config.isShortStatistics()),
                () -> assertFalse(config.isFullStatistics()),
                () -> assertEquals(List.of("input1.txt", "input2.txt"), config.getInputFiles())
        );
    }

    @Test
    void minimalConfig() {
        Config config = new ConfigParser(new String[]{"file.txt"}).parse();

        assertAll(
                () -> assertEquals("", config.getOutputPath()),
                () -> assertEquals("", config.getFilePrefix()),
                () -> assertFalse(config.isAppendMode()),
                () -> assertFalse(config.isShortStatistics()),
                () -> assertFalse(config.isFullStatistics()),
                () -> assertEquals(List.of("file.txt"), config.getInputFiles())
        );
    }

    @Test
    void conflictingStatisticsFlags() {
        String[] args = {"-s", "-f", "file.txt"};
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> new ConfigParser(args).parse());

        assertEquals("You can't use -s and -f at the same time.", e.getMessage());
    }

    @Test
    void missingOutputPathValue() {
        String[] args = {"-o", "-a", "file.txt"};
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> new ConfigParser(args).parse());

        assertEquals("No path specified for -o", e.getMessage());
    }

    @Test
    void filesWithSpecialCharacters() {
        String[] args = {"file.txt", "input.txt", "normal.txt"};
        Config config = new ConfigParser(args).parse();

        assertEquals(List.of("file.txt", "input.txt", "normal.txt"),
                config.getInputFiles());
    }

    @Test
    void duplicateOutputPath() {
        Config config = new ConfigParser(new String[]{"-o", "/old", "-o", "/new", "file.txt"}).parse();
        assertEquals("/new", config.getOutputPath());
    }

    @Test
    void mixedOrderArguments() {
        Config config = new ConfigParser(new String[]{"file.txt", "-a", "-p", "pre_"}).parse();

        assertAll(
                () -> assertTrue(config.isAppendMode()),
                () -> assertEquals("pre_", config.getFilePrefix()),
                () -> assertEquals(List.of("file.txt"), config.getInputFiles())
        );
    }
    @Test
    void shouldAcceptDoubleDashFiles() {
        String[] args = {"special-file.txt", "normal.txt"};
        Config config = new ConfigParser(args).parse();

        assertEquals(List.of("special-file.txt", "normal.txt"),
                config.getInputFiles());
    }

    @Test
    void shouldHandleMultipleSpacesInPaths() {
        String[] args = {"-o", "path/with  spaces", "file with  spaces.txt"};
        Config config = new ConfigParser(args).parse();

        assertAll(
                () -> assertEquals("path/with  spaces", config.getOutputPath()),
                () -> assertEquals(List.of("file with  spaces.txt"), config.getInputFiles())
        );
    }

    @Test
    void shouldIgnoreArgumentOrder() {
        String[] args = {"file1.txt", "-a", "file2.txt", "-o", "/out"};
        Config config = new ConfigParser(args).parse();

        assertAll(
                () -> assertTrue(config.isAppendMode()),
                () -> assertEquals("/out", config.getOutputPath()),
                () -> assertEquals(List.of("file1.txt", "file2.txt"), config.getInputFiles())
        );
    }

    @Test
    void shouldHandleEmptyArguments() {
        String[] args = {};
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> new ConfigParser(args).parse());

        assertEquals("No input files specified", e.getMessage());
    }

    @Test
    void shouldHandleMixedFileTypes() {
        String[] args = {"-p", "pre_", "file1.txt", "file2.txt", "input.txt"};
        Config config = new ConfigParser(args).parse();

        assertAll(
                () -> assertEquals("pre_", config.getFilePrefix()),
                () -> assertEquals(List.of("file1.txt", "file2.txt", "input.txt"),
                        config.getInputFiles())
        );
    }

    @Test
    void shouldRejectInvalidFlags() {
        String[] args = {"-unknown", "file.txt"};
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> new ConfigParser(args).parse());

        assertEquals("Unknown option: -unknown", e.getMessage());
    }

    @Test
    void shouldHandleMaxArguments() {
        String[] args = new String[100];
        Arrays.fill(args, "file.txt");
        args[0] = "-a";
        args[1] = "-o";
        args[2] = "/out";

        Config config = new ConfigParser(args).parse();
        assertEquals(97, config.getInputFiles().size());
    }

    @Test
    void shouldHandleVeryLongFilenames() {
        String longName = "a".repeat(500) + ".txt";
        String[] args = {longName};

        Config config = new ConfigParser(args).parse();
        assertEquals(List.of(longName), config.getInputFiles());
    }
}