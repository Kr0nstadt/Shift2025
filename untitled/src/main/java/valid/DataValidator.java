package valid;

import model.DataEntry;

public interface DataValidator {
    boolean isValid(String value);
    DataEntry parse(String value);
}
