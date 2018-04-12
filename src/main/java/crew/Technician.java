package crew;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import operations.Operation;

import java.io.IOException;

public class Technician extends Worker {

    private Operation firstSkill;
    private Operation secondSkill;

    public Technician(Operation firstSkill, Operation secondSkill) {
        this.firstSkill = firstSkill;
        this.secondSkill = secondSkill;
    }

    @Override
    public void introduceYourself() {
        System.out.println(this);
    }

    @Override
    public void work() throws IOException {
        String QUEUE_NAME = "queue1";
        getChannel().queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
            }
        };

        getChannel().basicConsume(QUEUE_NAME, true, consumer);
    }

    @Override
    public String toString() {
        return String.format("Technik %s z umiejętnościami %s and %s ", getName(), firstSkill, secondSkill);
    }
}
