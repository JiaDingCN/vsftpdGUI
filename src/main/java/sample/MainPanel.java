package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPanel extends Application {
    private String passwd;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("mainPanel.fxml"));
        Parent root = fxmlLoader.load();
        MainPanelController mc=fxmlLoader.getController();
        mc.setPasswd(passwd);
        primaryStage.setTitle("MainPanel");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    public MainPanel(String Passwd){
        passwd=Passwd;
    }
}
