import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import crew.Worker;
import utilities.WorkerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class Runner {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Konfiguracja START");
        System.out.println("Typ pracownika (doc|tech|admin)");
        System.out.println("Typ umiejetnosci technika (hip|elbow|knee)");

        String type = "";
        try {
            System.out.print("Podaj typ pracownika: ");
            type = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String skill1 = "";
        String skill2 = "";

        if (type.equals("tech")) {
            try {
                System.out.print("Podaj 1 typ obslugiwanych operacji: ");
                skill1 = bufferedReader.readLine();
                System.out.print("Podaj 2 typ obslugiwanych operacji: ");
                skill2 = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String name = "";
        try {
            System.out.print("Podaj imie: ");
            name = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        Worker worker = WorkerFactory.getType(type, skill1, skill2);
        worker.setName(name);
        worker.introduceYourself();
        worker.setChanel(channel);
        System.out.println("Konfiguracja STOP");
        try {
            worker.work();
        } catch (IOException e) {
            System.out.println("Wystapil blad w pracy " + worker);
        }
    }
}
