package coordinador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Coordinador implements ExceptionListener {

    public static String[] getSubsLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public void processCordinador(String ip, String file) {
        try {
            // Crear conexion con activeMQ
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + ip + ":61616");

            // Crear la conexion
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Crear la sesion
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Crear el destino (topico o cola)
            Destination destination = session.createQueue("GridJobs");

            // Crear un MessageProducer de la sesion para el topico o la cola
            MessageProducer producer = session.createProducer(destination);

            // Partir el archivo de configuracion en lineas
            String fileLines[] = getSubsLines(file);

            int i = 0;
            while (i < fileLines.length) {
                // Crear los mensajes
                TextMessage message = session.createTextMessage(fileLines[i]);

                // Imprimir en pantalla el mensaje que se va a enviar
                System.out.println("Mensaje enviado: " + fileLines[i]);
                producer.send(message);
                i++;
            }

            // Cerrar conexiones
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured. Apagando el cliente.");
    }

    /**
     * Inicio del programa Coordinador, el cual envia los datos a ser procesados
     * @param args se debe pasar dos argumentos: el primero debe de ser la ip de la maquina donde se corre este receptor, el segundo el path del archivo de configuracion
     */
    public static void main(String[] args) {
        Coordinador coo = new Coordinador();
        try {
            if(args.length == 2){
                String ip = args[0];
                String file = args[1];
                System.out.println("Coordinador encendido.");
                coo.processCordinador(ip, file);
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Error Modo de uso: arg0 => ip, arg1 => configFile");
        }
    }
}
