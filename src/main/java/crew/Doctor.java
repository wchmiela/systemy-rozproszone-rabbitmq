package crew;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.commons.lang3.SerializationUtils;
import utilities.SkillFactory;

import java.io.*;

public class Doctor extends Worker {

    private transient BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void work() throws IOException {
        String QUEUE_NAME = "queue1";
        getChannel().queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                TechnicianReply reply = SerializationUtils.deserialize(body);
                System.out.println(String.format("Otrzymano: %s", reply));
            }
        };

        getChannel().basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            System.out.print("Podaj imie pacienta: ");
            String patientName = bufferedReader.readLine();
            System.out.print("Podaj typ badania: ");
            String type = bufferedReader.readLine();

            DoctorRequest request = new DoctorRequest(this, patientName, SkillFactory.getType(type));

            byte[] data = SerializationUtils.serialize(request);

            getChannel().basicPublish("", QUEUE_NAME, null, data);

            String msg = String.format("Wyslano: %s", request);
            System.out.println(msg);
        }
    }

    @Override
    public String toString() {
        return String.format("Doctor %s", getName());
    }
}
