package crew;

import java.io.IOException;

public class Doctor extends Worker {

    @Override
    public void work() throws IOException {
        String QUEUE_NAME = "queue1";
        getChannel().queueDeclare(QUEUE_NAME, false, false, false, null);

        String message = "Hello world!";

        getChannel().basicPublish("", QUEUE_NAME, null, message.getBytes());
    }

    @Override
    public String toString() {
        return String.format("Doctor %s", getName());
    }
}
