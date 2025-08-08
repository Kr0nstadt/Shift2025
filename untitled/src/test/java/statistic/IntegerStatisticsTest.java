package statistic;

import static org.junit.jupiter.api.Assertions.*;

import model.IntegerData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegerStatisticsTest {

    @Test
    void testShortStatistics() {
        IntegerStatistics stats = new IntegerStatistics(false);

        stats.collect(new IntegerData(10));
        stats.collect(new IntegerData(20));
        stats.collect(new IntegerData(30));

        assertEquals(3, stats.count);
    }

    @Test
    void testFullStatistics() {
        IntegerStatistics stats = new IntegerStatistics(true);

        stats.collect(new IntegerData(10));
        stats.collect(new IntegerData(20));
        stats.collect(new IntegerData(30));

        assertEquals(3, stats.count);
        assertEquals(10, stats.min);
        assertEquals(30, stats.max);
        assertEquals(60, stats.sum);
    }

    @Test
    void testNegativeNumbers() {
        IntegerStatistics stats = new IntegerStatistics(true);

        stats.collect(new IntegerData(-5));
        stats.collect(new IntegerData(0));
        stats.collect(new IntegerData(5));

        assertEquals(-5, stats.min);
        assertEquals(5, stats.max);
        assertEquals(0, stats.sum);
    }

    @Test
    void testSingleValue() {
        IntegerStatistics stats = new IntegerStatistics(true);
        stats.collect(new IntegerData(42));

        assertEquals(42, stats.min);
        assertEquals(42, stats.max);
        assertEquals(42, stats.sum);
    }
}