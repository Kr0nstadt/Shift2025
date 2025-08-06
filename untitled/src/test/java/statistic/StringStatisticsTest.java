package statistic;

import static org.junit.jupiter.api.Assertions.*;

import model.StringData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringStatisticsTest {

    @Test
    void testShortStatistics() {
        StringStatistics stats = new StringStatistics(false);

        stats.collect(new StringData("a"));
        stats.collect(new StringData("bb"));

        assertEquals(2, stats.count);
    }

    @Test
    void testFullStatistics() {
        StringStatistics stats = new StringStatistics(true);

        stats.collect(new StringData("a"));
        stats.collect(new StringData("bb"));
        stats.collect(new StringData("ccc"));

        assertEquals(3, stats.count);
        assertEquals(1, stats.minLength);
        assertEquals(3, stats.maxLength);
        assertEquals(6, stats.totalLength);
    }

    @Test
    void testEmptyString() {
        StringStatistics stats = new StringStatistics(true);
        stats.collect(new StringData(""));

        assertEquals(0, stats.minLength);
        assertEquals(0, stats.maxLength);
    }

    @Test
    void testUnicodeStrings() {
        StringStatistics stats = new StringStatistics(true);
        stats.collect(new StringData("Привет"));
        stats.collect(new StringData("世界"));

        assertEquals(2, stats.minLength);
        assertEquals(6, stats.maxLength);
    }
}