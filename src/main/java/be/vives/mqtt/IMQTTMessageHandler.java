package be.vives.mqtt;

/**
 *
 * @author BioBoost
 */
public interface IMQTTMessageHandler {
    public void messageArrived(String topic, String message);
}