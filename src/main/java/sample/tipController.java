package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class tipController {
    @FXML
    public Button okButton;
    @FXML
    public Label showAddrText;
    @FXML
    private void initialize() throws IOException {
        InputStream in = getClass().getClassLoader().getResource("ipAddr.sh").openStream();
        String s = myUtils.runBashResourceFromJar("ipAddr.sh",in);
        String[] s1 = s.split(" ");
        showAddrText.setText("ftp://"+s1[1]);
    }

    public void exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
    @Test
    public void test(){
        String shellPath = getClass().getClassLoader().getResource("ipAddr.sh").getPath();
        String s = myUtils.bashCommand("bash " + shellPath);
        String[] s1 = s.split(" ");
        System.out.println(s1[1]);
    }
}
