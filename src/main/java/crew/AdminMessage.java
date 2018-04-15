package crew;

import java.io.Serializable;

class AdminMessage extends SerializationWrapper implements Serializable {
    private final String message;
    private final Worker admin;

    AdminMessage(String message, Worker admin) {
        this.message = message;
        this.admin = admin;
    }

    static String makeRoutingKey() {
        return "Info";
    }

    @Override
    public String toString() {
        return String.format("Wiadomosc: %s. Nadawca: %s", message, admin);
    }
}
