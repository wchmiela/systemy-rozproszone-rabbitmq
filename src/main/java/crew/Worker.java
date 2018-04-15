package crew;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.io.Serializable;

public abstract class Worker implements Serializable {

    private String name;
    private transient Channel channel;

    final static String EXCHANGE_NAME = "wojtek";

    public abstract void work() throws IOException;

    public void introduceYourself() {
        System.out.println(this);
    }

    public void setName(String newName) {
        this.name = newName;
    }

    String getName() {
        return name;
    }

    public void setChanel(Channel channel) {
        this.channel = channel;
    }

    Channel getChannel() {
        return channel;
    }
}
