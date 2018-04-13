package crew;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.Serializable;

public abstract class Worker implements Serializable {

    private String name;
    private transient Channel channel;
    private transient Connection connection;

    public final static String EXCHANGE_NAME = "wojtek";

    public void setName(String newName) {
        this.name = newName;
    }

    String getName() {
        return name;
    }

    public void introduceYourself() {
        System.out.println(this);
    }

    public abstract void work() throws IOException;

    public void setChanel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
