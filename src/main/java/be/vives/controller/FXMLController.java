package be.vives.controller;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.ProgressBar;


import be.vives.mqtt.IMQTTMessageHandler;
import be.vives.mqtt.SimpleMQTTClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable, IMQTTMessageHandler {
    
    @FXML
    private Label label;
    
    private SimpleMQTTClient client = new SimpleMQTTClient();
    @FXML  
    private ProgressBar healtprogress;
    @FXML
    private ProgressBar bulletprogress;


    @FXML
    private void uphandler(MouseEvent event) {
        System.out.println("you clicked up");
        client.publish("up","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void righthandler(MouseEvent event) {
        System.out.println("you clicked right");
        client.publish("right","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void downhandler(MouseEvent event) {
        System.out.println("you clicked down");
        client.publish("down","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void lefthandler(MouseEvent event) {
        System.out.println("you clicked left");
        client.publish("left","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void ahandler(MouseEvent event) {
        System.out.println("you clicked a");
        client.publish("a","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void bhandler(MouseEvent event) {
        System.out.println("you clicked b");
        client.publish("b","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void yhandler(MouseEvent event) {
        System.out.println("you clicked y");
        client.publish("y","test/oop2/bug/RobinDeleucontrols");
    }
    @FXML
    private void xhandler(MouseEvent event) {
        System.out.println("you clicked x");
        client.publish("x","test/oop2/bug/RobinDeleucontrols");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    client.subscribe("test/oop2/bug/health", this);
    client.subscribe("test/oop2/bug/bullets", this);
    //double value = Double.parseDouble(message);
    }

    @Override
    public void messageArrived(String topic, String message) {
    System.out.println("topic= " +topic+ " message = " +message);
    if (topic.equals("test/oop2/bug/health")){
    try{
    double value = Double.parseDouble(message);
    healtprogress.setProgress(value/100);
    } catch (NumberFormatException nfe){

    }}
    if (topic.equals("test/oop2/bug/bullets")){
        try{
        double value = Double.parseDouble(message);
        bulletprogress.setProgress(value/1000);
        } catch (NumberFormatException nfe){
    
        }}
}
}
