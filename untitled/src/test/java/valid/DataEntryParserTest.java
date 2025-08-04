package valid;

import model.FloatData;
import model.IntegerData;
import model.StringData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataEntryParserTest {
    private final DataEntryParser parser = new DataEntryParser();

    @Test
    void standardInteger() {
        assertInstanceOf(IntegerData.class, parser.parse("42"));
        assertInstanceOf(IntegerData.class, parser.parse("-123"));
        assertInstanceOf(IntegerData.class, parser.parse("+999"));
        assertInstanceOf(IntegerData.class, parser.parse("0"));
    }

    @Test
    void integerEdgeCases() {
        assertInstanceOf(IntegerData.class, parser.parse(String.valueOf(Long.MAX_VALUE)));
        assertInstanceOf(IntegerData.class, parser.parse(String.valueOf(Long.MIN_VALUE)));

        assertInstanceOf(IntegerData.class, parser.parse("007"));
        assertInstanceOf(IntegerData.class, parser.parse("-000123"));

        assertInstanceOf(IntegerData.class, parser.parse("1234567890123456789"));
    }

    @Test
    void standardFloat() {
        assertInstanceOf(FloatData.class, parser.parse("3.14"));
        assertInstanceOf(FloatData.class, parser.parse("-0.001"));
        assertInstanceOf(FloatData.class, parser.parse("1.23e-10"));
        assertInstanceOf(FloatData.class, parser.parse("6.022E23"));
    }

    @Test
    void floatEdgeCases() {
        assertInstanceOf(FloatData.class, parser.parse(".5"));
        assertInstanceOf(FloatData.class, parser.parse("42."));

        assertInstanceOf(FloatData.class, parser.parse("0.0"));
        assertInstanceOf(FloatData.class, parser.parse("-0.0"));
        assertInstanceOf(FloatData.class, parser.parse(".0"));
        assertInstanceOf(FloatData.class, parser.parse("0."));

        assertInstanceOf(FloatData.class, parser.parse("1e100"));
        assertInstanceOf(FloatData.class, parser.parse("1e-100"));
        assertInstanceOf(FloatData.class, parser.parse("123.456e-78"));

        assertInstanceOf(FloatData.class, parser.parse(String.valueOf(Double.MAX_VALUE)));
        assertInstanceOf(FloatData.class, parser.parse(String.valueOf(Double.MIN_VALUE)));
    }

    @Test
    void ambiguousCases() {
        assertInstanceOf(FloatData.class, parser.parse("123.0"));
        assertInstanceOf(FloatData.class, parser.parse("123.000"));
        assertInstanceOf(FloatData.class, parser.parse("123.0001"));
        assertInstanceOf(FloatData.class, parser.parse("123e0"));
    }

    @Test
    void invalidNumbers() {
        assertInstanceOf(StringData.class, parser.parse("34.56л"));
        assertInstanceOf(StringData.class, parser.parse("12a34"));
        assertInstanceOf(StringData.class, parser.parse("1.2.3"));
        assertInstanceOf(StringData.class, parser.parse("1e2e3"));
        assertInstanceOf(StringData.class, parser.parse("e10"));
        assertInstanceOf(StringData.class, parser.parse("+-12"));
    }

    @Test
    void specialStringCases() {
        assertInstanceOf(StringData.class, parser.parse("."));
        assertInstanceOf(StringData.class, parser.parse("-.e"));
        assertInstanceOf(StringData.class, parser.parse("Infinity"));
        assertInstanceOf(StringData.class, parser.parse("NaN"));
        assertInstanceOf(StringData.class, parser.parse("1,234"));
        assertInstanceOf(StringData.class, parser.parse("$123"));
    }

    @Test
    void emptyAndNullCases() {
        assertInstanceOf(StringData.class, parser.parse(""));
        assertInstanceOf(StringData.class, parser.parse("   "));
        assertInstanceOf(StringData.class, parser.parse(null));
    }

    @Test
    void whitespaceHandling() {
        assertInstanceOf(IntegerData.class, parser.parse(" 42 "));
        assertInstanceOf(FloatData.class, parser.parse(" 3.14 \t"));
        assertInstanceOf(StringData.class, parser.parse(" hello "));
    }

    @Test
    void unicodeCases() {
        assertInstanceOf(StringData.class, parser.parse("٣٤٥"));
        assertInstanceOf(StringData.class, parser.parse("１２３"));
        assertInstanceOf(StringData.class, parser.parse("⓵⓶⓷"));
    }

    @Test
    void numberLikeStrings() {
        assertInstanceOf(StringData.class, parser.parse("123abc"));
        assertInstanceOf(StringData.class, parser.parse("0x10"));
        assertInstanceOf(StringData.class, parser.parse("0b1010"));
        assertInstanceOf(StringData.class, parser.parse("1.2f"));
        assertInstanceOf(StringData.class, parser.parse("1L"));
    }

    @Test
    void scientificNotationEdgeCases() {
        assertInstanceOf(FloatData.class, parser.parse("1e+1"));
        assertInstanceOf(FloatData.class, parser.parse("1e-1"));
        assertInstanceOf(StringData.class, parser.parse("1e"));
        assertInstanceOf(StringData.class, parser.parse("1e+"));
        assertInstanceOf(StringData.class, parser.parse("1e-"));
    }
}