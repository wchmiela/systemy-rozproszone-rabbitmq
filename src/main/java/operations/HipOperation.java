package operations;

import java.io.Serializable;

public class HipOperation implements Operation, Serializable {

    @Override
    public String toString() {
        return "operacja biodra";
    }

    @Override
    public String operationName() {
        return "hip";
    }
}
