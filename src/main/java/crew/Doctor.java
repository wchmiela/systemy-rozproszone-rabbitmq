package crew;

import com.rabbitmq.client.*;
import messages.AdminMessage;
import messages.DoctorRequest;
import messages.SerializationWrapper;
import org.apache.commons.lang3.SerializationUtils;
import utilities.SkillFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Doctor extends Worker {

    private final transient BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void work() throws IOException {
        String exchangeName = EXCHANGE_NAME;
        getChannel().exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String queueName = getChannel().queueDeclare(getName(),
                false, false, false, null).getQueue();
        getChannel().queueBind(queueName, exchangeName, queueName);

        String adminQ = getChannel().queueDeclare(getName(),
                false, false, false, null).getQueue();
        getChannel().queueBind(adminQ, exchangeName, AdminMessage.makeRoutingKey());

        Consumer consumer = new DefaultConsumer(getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                SerializationWrapper reply = SerializationUtils.deserialize(body);
                System.out.println(String.format("Otrzymano>\t %s", reply));

                getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        };

        getChannel().basicConsume(queueName, false, consumer);
        getChannel().basicConsume(adminQ, false, consumer);

        while (send(exchangeName)) ;
    }

    private boolean send(String exchangeName) throws IOException {
        System.out.println("Podaj imie pacienta: ");
        String patientName = bufferedReader.readLine();

        if (patientName.equals("exit")) return false;

        System.out.println("Podaj typ badania: ");
        String type = bufferedReader.readLine();

        DoctorRequest request = new DoctorRequest(this, patientName, SkillFactory.getType(type));
        byte[] data = SerializationUtils.serialize(request);

        getChannel().basicPublish(exchangeName, request.makeRoutingKey(), null, data);

        System.out.println(String.format("Wyslano>\t %s", request));

        return true;
    }

    @Override
    public String toString() {
        return String.format("Doktor %s", super.getName());
    }
}
