package operations;

import java.io.Serializable;

public class HipOperation implements Operation, Serializable {
    @Override
    public String operationName() {
        return "hip";
    }

    @Override
    public String toString() {
        return "operacja biodra";
    }
}
