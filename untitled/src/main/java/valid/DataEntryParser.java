package valid;

import model.DataEntry;
import model.StringData;

import java.util.List;

public class DataEntryParser {
    private final List<DataValidator> validators;

    public DataEntryParser() {
        this.validators = List.of(
                new IntegerValidator(),
                new FloatValidator(),
                new StringValidator()
        );
    }

    public DataEntry parse(String value) {
        if (value == null) {
            return new StringData("");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return new StringData("");
        }

        for (DataValidator validator : validators) {
            if (validator.isValid(trimmed)) {
                return validator.parse(trimmed);
            }
        }

        return new StringData(trimmed);
    }
}