package crew;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Admin extends Worker {

    private transient BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String toString() {
        return String.format("Admin %s", getName());
    }

    @Override
    public void work() throws IOException {
        String exchangeName = EXCHANGE_NAME;
        getChannel().exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String queueName = getChannel().queueDeclare().getQueue();
        getChannel().queueBind(queueName, exchangeName, "#");

        Consumer consumer = new DefaultConsumer(getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                SerializationWrapper received = SerializationUtils.deserialize(body);

                System.out.println(String.format("Otrzymano>\t %s", received));
            }
        };

        getChannel().basicConsume(queueName, true, consumer);

        while (true) {
            send(exchangeName);
        }
    }

    private void send(String exchangeName) throws IOException {
        System.out.println("Podaj wiadomosc: ");
        String adminMessage = bufferedReader.readLine();

        AdminMessage request = new AdminMessage(adminMessage, this);
        byte[] data = SerializationUtils.serialize(request);

        getChannel().basicPublish(exchangeName, AdminMessage.makeRoutingKey(), null, data);
    }
}
