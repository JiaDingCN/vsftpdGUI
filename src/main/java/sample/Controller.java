package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Controller {
    public String passwd = null;
    @FXML
    public Button okButton;
    @FXML
    public PasswordField passwordTextField;
    @FXML
    public Button submitButton;
    @FXML
    public Text tipText;

    public void exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    public void submitClicked(MouseEvent mouseEvent) throws Exception {
        passwd = passwordTextField.getText();
        tipText.setVisible(true);
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
        MainPanel mainPanel = new MainPanel(passwd);
        mainPanel.start(new Stage());
    }

}
