package uk.ac.aston.fyp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;


public class MainController implements Initializable {

    @FXML
    private TextArea textAreaUI;

    @FXML
    public static TextArea staticTxtArea;

    @FXML
    private TextField targetIPAddress;

    @FXML
    private TextField dos_target_ip;

    @FXML
    private TextField txt_portNumberLower;

    @FXML
    private TextField txt_portNumberUpper;

    @FXML
    private TextField packetSizeCounter;

    @FXML
    private TextField threadCount;

    @FXML
    private Button scanPressed;

    @FXML
    private Button btn_start_attack;

    @FXML
    private Button btn_stop_attack;

    @FXML
    private CheckBox portDetails;

    @FXML
    private Slider slider_packets;

    DOS dos;

    public MainController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticTxtArea = textAreaUI;
    }

    @FXML
    void scanPressed(ActionEvent event) throws ExecutionException, InterruptedException, MalformedURLException {
        /* Default */
        int lower = 1;
        int upper = 65535;
        String ip = targetIPAddress.getText();
        lower = Integer.parseInt(txt_portNumberLower.getText());
        upper = Integer.parseInt(txt_portNumberUpper.getText());

        if(portDetails.isSelected()) {
            PortScanner.nmapScanner(ip, txt_portNumberLower.getText(), txt_portNumberUpper.getText());
        } else {
            /**
             * TODO Currently scanning port 80 but can be built to be scalable with other services.
             */
            System.out.println("PortScanner is scanning open ports on: " + ip);
            PortScanner ps = new PortScanner(ip, lower, upper);
        }
    }

    @FXML
    void startAttack(ActionEvent event) throws UnknownHostException {
        String ip = dos_target_ip.getText();
        String port = "80"; // todo default port use ports discovered on PortScanner
        String packets = String.valueOf( (int) slider_packets.getValue() );
        String threads = threadCount.getText();

        InetAddress address = InetAddress.getByName(ip);
        dos = new DOS(address.getHostAddress(), port, packets, threads);
    }

    @FXML
    void stopAttack(ActionEvent event) {
        dos.stopProcess();
    }

    @FXML
    void updateText(MouseEvent event) {
        packetSizeCounter.setText(String.valueOf(slider_packets.getValue()));
    }

    @FXML
    void updateSliderValue(ActionEvent event) {
        slider_packets.setValue(Double.parseDouble(packetSizeCounter.getText()));
    }
}
