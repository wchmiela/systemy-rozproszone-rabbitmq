package crew;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import operations.Operation;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class Technician extends Worker {

    private Operation firstSkill;
    private Operation secondSkill;
    private Technician myself;

    public Technician(Operation firstSkill, Operation secondSkill) {
        this.firstSkill = firstSkill;
        this.secondSkill = secondSkill;
        this.myself = this;
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
                DoctorRequest request = SerializationUtils.deserialize(body);
                System.out.println(String.format("Otrzymano: %s", request));

                TechnicianReply reply = new TechnicianReply(request, myself);
                byte[] data = SerializationUtils.serialize(reply);

                getChannel().basicPublish("", QUEUE_NAME, null, data);
                System.out.println(String.format("Wyslano: %s", reply));
            }
        };

        getChannel().basicConsume(QUEUE_NAME, true, consumer);
    }

    @Override
    public String toString() {
        return String.format("Technik %s z umiejętnościami %s and %s ", getName(), firstSkill, secondSkill);
    }
}
