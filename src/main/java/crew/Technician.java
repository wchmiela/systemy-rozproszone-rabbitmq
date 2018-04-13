package crew;

import com.rabbitmq.client.*;
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
    public void work() throws IOException {
        String exchangeName = EXCHANGE_NAME;

        getChannel().exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);

        String firstSkillQueue = getChannel().queueDeclare(
                DoctorRequest.makeRoutingKey(firstSkill.operationName(), "*", "*"),
                false, false, false, null).getQueue();
        getChannel().queueBind(firstSkillQueue, exchangeName, firstSkillQueue);

        String secondSkillQueue = getChannel().queueDeclare(
                DoctorRequest.makeRoutingKey(secondSkill.operationName(), "*", "*"),
                false, false, false, null).getQueue();
        getChannel().queueBind(secondSkillQueue, exchangeName, secondSkillQueue);

        Consumer consumer = new DefaultConsumer(getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                DoctorRequest request = SerializationUtils.deserialize(body);
                System.out.println(String.format("Otrzymano> %s", request));

                TechnicianReply reply = new TechnicianReply(request, myself);
                byte[] serializedReply = SerializationUtils.serialize(reply);

                getChannel().basicPublish(exchangeName, reply.makeRoutingKey(), null, serializedReply);
                System.out.println(String.format("Wyslano> %s", reply));
            }
        };

        getChannel().basicConsume(firstSkillQueue, true, consumer);
        getChannel().basicConsume(secondSkillQueue, true, consumer);
    }

    @Override
    public String toString() {
        return String.format("Technik %s z umiejętnościami %s and %s ", getName(), firstSkill, secondSkill);
    }
}
