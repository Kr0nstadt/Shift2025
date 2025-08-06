package valid;

import model.DataEntry;
import model.FloatData;

public class FloatValidator implements DataValidator {
    private static final String FLOAT_REGEX =
            "^[-+]?\\d+\\.\\d*([eE][-+]?\\d+)?$|^[-+]?\\.\\d+([eE][-+]?\\d+)?$|^[-+]?\\d+[eE][-+]?\\d+$";

    @Override
    public boolean isValid(String value) {
        return value.matches(FLOAT_REGEX);
    }

    @Override
    public DataEntry parse(String value) {
        try {
            return new FloatData(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid float format: " + value);
        }
    }
}