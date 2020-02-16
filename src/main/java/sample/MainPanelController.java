package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

public class MainPanelController {
    @FXML
    public ChoiceBox anon_enable_ChooseBox;
    @FXML
    public ChoiceBox local_enable_Choosebox;
    @FXML
    public ChoiceBox write_enable_Choosebox;
    @FXML
    public ChoiceBox anon_upload_enable_Choosebox;
    @FXML
    public ChoiceBox anon_mkdir_write_enable_Choosebox;
    @FXML
    public Button refreshButton;
    @FXML
    public Button cancelButton;
    @FXML
    public ChoiceBox no_anon_passwd_Choosebox;
    @FXML
    public Button anon_changedirButton;
    @FXML
    public Button local_changeDirButton;
    @FXML
    public Label anon_rootText;
    @FXML
    public Label local_rootText;
    @FXML
    public TextField ftp_usernameTextField;
    private String passwd;
    private HashMap<String, String> configuration;

    @FXML
    private void initialize() {
       /*
       对控制器中注册了的控件进行初始化
       */
        configuration = myUtils.readConfig();
        anon_enable_ChooseBox.setItems(FXCollections.observableArrayList("YES", "NO"));
        local_enable_Choosebox.setItems(FXCollections.observableArrayList("YES", "NO"));
        write_enable_Choosebox.setItems(FXCollections.observableArrayList("YES", "NO"));
        anon_mkdir_write_enable_Choosebox.setItems(FXCollections.observableArrayList("YES", "NO"));
        anon_upload_enable_Choosebox.setItems(FXCollections.observableArrayList("YES", "NO"));
        no_anon_passwd_Choosebox.setItems(FXCollections.observableArrayList("YES", "NO"));

        if (configuration.containsKey("anonymous_enable")) {
            anon_enable_ChooseBox.setValue(configuration.get("anonymous_enable"));
        } else {
            configuration.put("anonymous_enable", "NO");
            anon_enable_ChooseBox.setValue("NO");
        }

        if (configuration.containsKey("no_anon_password")) {
            no_anon_passwd_Choosebox.setValue(configuration.get("no_anon_password"));
        } else {
            configuration.put("no_anon_password", "NO");
            no_anon_passwd_Choosebox.setValue("NO");
        }
        if(configuration.containsKey("ftp_username")){
            ftp_usernameTextField.setText(configuration.get("ftp_username"));
        }else{
            configuration.put("ftp_username","ftp");
            ftp_usernameTextField.setText("ftp");
        }
        if (configuration.containsKey("anon_root")) {
            anon_rootText.setText(configuration.get("anon_root"));
        } else {
            configuration.put("anon_root", "NO");
            anon_rootText.setText("/srv/ftp");
        }
        if (configuration.containsKey("local_root")) {
            local_rootText.setText(configuration.get("local_root"));
        } else {
            configuration.put("local_root", "NO");
            local_rootText.setText("/srv/ftp");
        }
        if (configuration.containsKey("local_enable")) {
            local_enable_Choosebox.setValue(configuration.get("local_enable"));
        } else {
            configuration.put("local_enable", "NO");
            local_enable_Choosebox.setValue("NO");
        }
        if (configuration.containsKey("write_enable")) {
            write_enable_Choosebox.setValue(configuration.get("write_enable"));
        } else {
            configuration.put("write_enable", "NO");
            write_enable_Choosebox.setValue("NO");
        }
        if (configuration.containsKey("anon_upload_enable")) {
            anon_upload_enable_Choosebox.setValue(configuration.get("anon_upload_enable"));
        } else {
            configuration.put("anon_upload_enable", "NO");
            anon_upload_enable_Choosebox.setValue("NO");
        }
        if (configuration.containsKey("anon_mkdir_write_enable")) {
            anon_mkdir_write_enable_Choosebox.setValue(configuration.get("anon_mkdir_write_enable"));
        } else {
            configuration.put("anon_mkdir_write_enable", "NO");
            anon_mkdir_write_enable_Choosebox.setValue("NO");
        }

    }

    public void setPasswd(String password) {
        passwd = password;
    }

    public void local_changeDir(MouseEvent mouseEvent) {
        DirectoryChooser file=new DirectoryChooser();
        file.setTitle("Choose the local dirctionary for FTP");
        file.setInitialDirectory(new File(local_rootText.getText()));
        Stage stage = (Stage) local_changeDirButton.getScene().getWindow();
        File newFolder = file.showDialog(stage);
        local_rootText.setText(newFolder.getPath());
    }

    public void anon_changeDir(MouseEvent mouseEvent) {
        DirectoryChooser file=new DirectoryChooser();
        file.setTitle("Choose the anonymous dirctionary for FTP");
        file.setInitialDirectory(new File(anon_rootText.getText()));
        Stage stage = (Stage) anon_changedirButton.getScene().getWindow();
        File newFolder = file.showDialog(stage);
        anon_rootText.setText(newFolder.getPath());
    }

    public void Exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void refreshConfig(MouseEvent mouseEvent) throws Exception {
        try {
            refreshAndCopy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = getClass().getClassLoader().getResource("openVsftpd.sh").openStream();
        myUtils.runBashResourceFromJar("openVsftpd.sh",in,passwd);
        tip ti=new tip();
        ti.start(new Stage());
        Stage stage = (Stage) refreshButton.getScene().getWindow();
        stage.close();
    }

    public void refreshAndCopy() throws IOException {
        configuration.replace("anonymous_enable", (String) anon_enable_ChooseBox.getValue());
        configuration.replace("no_anon_password", (String) no_anon_passwd_Choosebox.getValue());
        configuration.replace("anon_root",anon_rootText.getText());
        configuration.replace("local_root",local_rootText.getText());
        configuration.replace("local_enable", (String) local_enable_Choosebox.getValue());
        configuration.replace("write_enable", (String) write_enable_Choosebox.getValue());
        configuration.replace("anon_upload_enable", (String) anon_upload_enable_Choosebox.getValue());
        configuration.replace("anon_mkdir_write_enable", (String) anon_mkdir_write_enable_Choosebox.getValue());
        configuration.replace("ftp_username",ftp_usernameTextField.getText());
        Vector<String> strings = myUtils.bashCommandVectorWithComment("cat /etc/vsftpd.conf");
        File confFile = new File("./vsftpd.conf");
        if (confFile.exists()) {
            confFile.delete();
        }
        confFile.createNewFile();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(confFile, false), "UTF-8"));
        for (String line : strings) {
            if (line.length()==0||line.charAt(0) == '#') {
                out.write(line + "\n");
            } else {
                String[] split = line.split("=");
                out.write(split[0]+"="+configuration.get(split[0])+"\n");
            }
        }
        out.close();
    }
}
