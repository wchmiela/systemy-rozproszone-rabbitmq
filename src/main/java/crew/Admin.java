package crew;

public class Admin extends Worker {

    @Override
    public String toString() {
        return String.format("Admin %s", getName());
    }

    @Override
    public void work() {

    }
}
