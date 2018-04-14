package crew;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.io.Serializable;

public abstract class Worker implements Serializable {

    private String name;
    private transient Channel channel;

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
}
