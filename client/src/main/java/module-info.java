module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;
    requires io.netty.codec;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
}