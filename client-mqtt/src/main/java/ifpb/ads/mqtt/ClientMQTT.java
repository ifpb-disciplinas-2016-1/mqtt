package ifpb.ads.mqtt;
 
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 05/09/2016, 14:15:44
 */
public class ClientMQTT {

    public static void main(String[] args) {
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
//        String topic = "area/10/sensor/5000/#";
        String topic = "sensor/temperatura/#";
//        String topic = "mitsuruog";
        int qos = 2;
        String broker = "ws://test.mosquitto.org:8080/mqtt";
//        String broker = "tcp://192.168.99.100:1883";
        String clientId = "job";

        try {
            MqttClient client = new MqttClient(broker, clientId, dataStore);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
//            connOpts.setUserName("mosquitto");
//            connOpts.setPassword("".toCharArray());
            System.out.println("Connecting to broker: " + broker);
            client.setCallback(new ClienteCall());

            client.connect(connOpts);

            client.subscribe(topic, qos);

        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    static class ClienteCall implements MqttCallback {

        @Override
        public void connectionLost(Throwable thrwbl) {
            System.out.println("thrwbl = " + thrwbl);
        }

        @Override
        public void messageArrived(String string, MqttMessage mm) throws Exception {
            byte[] bytes = mm.getPayload();
            System.out.println("array: "+Arrays.toString(bytes));
            System.out.println("str: "+new String(bytes));
            //lendo o valor numérico
            int sampleValue = (int) ((bytes[0] << 8) | (bytes[1] & 0x00FF));
            System.out.println("msg: " + string + "-> " + sampleValue);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken imdt) {

        }
    }
}
