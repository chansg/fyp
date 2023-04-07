package uk.ac.aston.fyp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TextArea textAreaUI;
    @FXML
    public static TextArea staticTxtArea;
    @FXML
    private Label welcomeText;

    @FXML
    private Button btn_scan;

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticTxtArea = textAreaUI;
    }
}
