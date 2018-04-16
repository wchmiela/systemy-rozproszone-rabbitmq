package messages;

import crew.Worker;

import java.io.Serializable;

public class AdminMessage extends SerializationWrapper implements Serializable {
    private final String message;
    private final Worker admin;

    public AdminMessage(String message, Worker admin) {
        this.message = message;
        this.admin = admin;
    }

    public static String makeRoutingKey() {
        return "Info";
    }

    @Override
    public String toString() {
        return String.format("Wiadomosc: %s. Nadawca: %s", message, admin);
    }
}
