package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    boolean isVsftpdExists=false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        checkVsftpd();
        if(!isVsftpdExists){
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("warn_NoVsftpd.fxml"));
            primaryStage.setTitle("Please install vsftpd first");
            primaryStage.setScene(new Scene(root, 500, 300));
        }
        else{
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("getPassword.fxml"));
            primaryStage.setTitle("Please input your password");
            primaryStage.setScene(new Scene(root, 600, 280));
        }
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
    public void checkVsftpd(){
        String whereis_vsftpd = myUtils.bashCommand("whereis vsftpd");
        isVsftpdExists= !myUtils.isNullPath(whereis_vsftpd);
    }
}
