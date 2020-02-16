package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class tip extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("tip.fxml"));
        stage.setTitle("Configuration saved");
        stage.setScene(new Scene(root, 550, 220));
        stage.show();
    }
}
