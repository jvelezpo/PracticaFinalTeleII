package Escuchar;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Escuchar implements ExceptionListener {

    void processReceptor(String brokerIp) {
        try {
            // Crear conexion con activeMQ
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + brokerIp + ":61616");

            // Crear la conexion
            Connection connection = connectionFactory.createConnection();
            connection.setExceptionListener(this);

            // Crear la sesion
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear el destino (topico o cola)
            Destination destination = session.createQueue("GridResults");

            // Crear un MessageProducer de la sesion para el topico o la cola
            MessageConsumer consumer = session.createConsumer(destination);

            MessageListener listener = new MessageListener() {
                public void onMessage(Message msg) {
                    if (msg instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) msg;
                        String text = null;
                        try {
                            text = textMessage.getText();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Recibido: " + text);
                    } else {
                        System.out.println("Recibido: " + msg);
                    }
                }
            };
            consumer.setMessageListener(listener);
            connection.start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured. Apagando el cliente.");
    }

    /**
     * Inicio del programa receptor
     * @param args, se debe pasar un argumento que debe de ser la ip de la maquina donde se corre este receptor
     */
    public static void main(String[] args) {
        Escuchar re = new Escuchar();
        try{
            if (args.length == 1) {
                String ip = args[0];
                System.out.println("Receptor encendido.");
                re.processReceptor(ip);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            System.out.println("Error Modo de uso: arg0 => ip");
        }

    }
}
