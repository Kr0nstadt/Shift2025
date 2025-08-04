package valid;

import model.DataEntry;
import model.IntegerData;

public class IntegerValidator implements DataValidator {
    private static final String INTEGER_REGEX = "^[-+]?\\d+$";

    @Override
    public boolean isValid(String value) {
        if (value == null) return false;
        return value.matches(INTEGER_REGEX);
    }

    @Override
    public DataEntry parse(String value) {
        try {
            return new IntegerData(Long.parseLong(value));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer format: " + value);
        }
    }
}
