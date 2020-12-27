package be.vives.mqtt;

import java.util.HashMap;
import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SimpleMQTTClient implements MqttCallback {
    private MqttClient client;
    private String broker = "tcp://mqtt.labict.be:1883";
    private String clientId;
    private int qos = 2;            // Exactly once
    private MemoryPersistence persistence;
    private MqttConnectOptions connectionOptions;
    private HashMap<String, IMQTTMessageHandler> subscriptions;

    public SimpleMQTTClient(String clientId) {
      subscriptions = new HashMap<>();
      Random random = new Random();
      this.clientId = clientId + random.nextInt();
      setupMqtt();
    }

    public SimpleMQTTClient() {
      this("guest");
    }

    private void setupMqtt() {
      try {
        persistence = new MemoryPersistence();
        client = new MqttClient(broker, clientId, persistence);
        connectionOptions = new MqttConnectOptions();
        connectionOptions.setCleanSession(true);
        client.connect(connectionOptions);
        client.setCallback(this);
      } catch(MqttException me) {
        System.out.println("Failed to connect to broker");
        System.out.println("reason " + me.getReasonCode());
        System.out.println("msg " + me.getMessage());
        System.out.println("loc " + me.getLocalizedMessage());
        System.out.println("cause " + me.getCause());
        System.out.println("excep " + me);
        me.printStackTrace();
      }
    }

    public void publish(String message, String topic) {
      try {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        client.publish(topic, mqttMessage);
      } catch (MqttException me) {
        System.out.println("Failed to publish message");
        System.out.println(me);
      }
    }

    public void subscribe(String topic, IMQTTMessageHandler handler) {
      try {
        client.subscribe(topic);
        subscriptions.put(topic, handler);
      } catch(MqttException me) {
        System.out.println("Failed to connect to broker");
        System.out.println("reason " + me.getReasonCode());
        System.out.println("msg " + me.getMessage());
        System.out.println("loc " + me.getLocalizedMessage());
        System.out.println("cause " + me.getCause());
        System.out.println("excep " + me);
        me.printStackTrace();
      }
    }

    public void disconnect() {
      try {
        client.disconnect();
        client.close();
      } catch (MqttException me) {
        System.out.println("Failed to disconnect");
        System.out.println(me);
      }
    }

    @Override
    public void connectionLost(Throwable thrwbl) {
      System.out.println("Lost connection with broker");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
      IMQTTMessageHandler handler = subscriptions.get(topic);
      if (handler != null) {
        handler.messageArrived(topic, mm.toString());
      } else {
        System.out.println("No handler for topic " + topic);
      }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }
}