package Modelo;

import Controlador.Simulador;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class GridModel implements ExceptionListener {

    private String ip;
    private Simulador simulador;

    public void submitResults(String text) {
        try {
            // Crear conexion con activeMQ
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + this.ip + ":61616");
            
            // Crear la conexion
            Connection connection = connectionFactory.createConnection();
            connection.start();
            
            // Crear la sesion
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Crear el destino (topico o cola)
            Destination destination = session.createQueue("GridResults");
            
            // Crear un MessageProducer de la sesion para el topico o la cola
            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage(text);

            System.out.println("mensaje enviado: " + text);
            producer.send(message);

            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void jobConsumer() {
        try {
            final GridModel gridModel = this;
            // Crear conexion con activeMQ
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + this.ip + ":61616");
            
            // Crear la conexion
            Connection connection = connectionFactory.createConnection();  
            connection.setExceptionListener(this);
            
            // Crear la sesion
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Crear el destino (topico o cola)
            Destination destination = session.createQueue("GridJobs");
            
            // Crear un MessageConsumer de la sesion para el topico o la cola
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
                        System.out.println("Llego una peticion para procesar los siguientes datos: " + text + "\n");
                        
                        // Separar el mensaje en tokens
                        MessageSplit p = new MessageSplit();
                        String[] params = p.parse(text);
                        
                        // Se simula el modelo con los parametros recibidos
                        simulador = new Simulador();
                        simulador.simular(gridModel, params[0], params[1], params[2], params[3], params[4], params[5]);

                        System.out.println("===========Fin del mensaje===========\n\n\nEsperando por mas datos para ser procesados\n");
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
        ex.printStackTrace();
    }

    public GridModel(String ip) {
        this.ip = ip;
    }

    /**
     * Inicio del programa Modelo, el cual procesa los datos enviados por el coordinador
     * @param args se debe pasar un argumento que debe de ser la ip de la maquina donde se corre este receptor
     */
    public static void main(String args[]) {
        try{
            if(args.length == 1){
                String ip = args[0];
                GridModel gm = new GridModel(ip);
                System.out.println("Modelo encendido, esperando datos para procesar.");
                gm.jobConsumer();
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            System.out.println("Error Modo de uso: arg0 => ip");
        }
    }
}



