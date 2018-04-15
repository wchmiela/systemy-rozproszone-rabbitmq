package operations;

import java.io.Serializable;

public class UnknownOperation implements Operation, Serializable {
    @Override
    public String operationName() {
        return "unknown";
    }

    @Override
    public String toString() {
        return "nieznana operacja";
    }
}
