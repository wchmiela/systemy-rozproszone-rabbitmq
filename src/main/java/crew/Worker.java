package crew;

import com.rabbitmq.client.Channel;

import java.io.IOException;

public abstract class Worker {

    private String name;
    private Channel channel;

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

    public void setChanel(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}