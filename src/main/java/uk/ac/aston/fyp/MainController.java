package uk.ac.aston.fyp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class MainController implements Initializable {
    @FXML
    private TextArea textAreaUI;
    @FXML
    public static TextArea staticTxtArea;
    @FXML
    private TextField targetIPAddress;
    private TextField txt_portNumber;

    @FXML
    private Button scanPressed;


    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticTxtArea = textAreaUI;
    }

    @FXML
    void scanPressed(ActionEvent event) throws ExecutionException, InterruptedException {
        /**
         * TODO Currently scanning port 80 but can be built to be scalable with other services.
         */

        String ip = targetIPAddress.getText();
        //int pn = txt_portNumber.getText();
        int pn = 65535;
        PortScanner ps = new PortScanner(ip, pn);
    }
}
