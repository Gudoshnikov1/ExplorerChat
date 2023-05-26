package com.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private Network network;
    public TextArea txArea;
    public TextField txMsgField;
    public TextField txNck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new Network((args) ->{
            txArea.appendText((String)args[0]);
        });
    }

    public void sendMessage(ActionEvent actionEvent) {
        network.sendMessage(txMsgField.getText());
        txMsgField.clear();
        txMsgField.requestFocus();
    }
}