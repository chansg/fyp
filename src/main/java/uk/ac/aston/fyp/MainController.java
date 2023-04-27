package uk.ac.aston.fyp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import uk.ac.aston.fyp.features.AggressiveScan;
import uk.ac.aston.fyp.features.CustomScript;
import uk.ac.aston.fyp.features.FastScan;
import uk.ac.aston.fyp.features.StealthScan;

import java.io.File;
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
    private TextField txt_duration;

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
    private TextField txt_loop;

    @FXML
    private TextField dos_port;

    @FXML
    private TextField txt_selected_file;

    @FXML
    private Button btn_file_chooser;

    @FXML
    private Button btn_clear_file_select;

    @FXML
    private Button scanPressed;

    @FXML
    private Button btn_start_attack;

    @FXML
    private Button btn_stop_attack;

    @FXML
    private CheckBox portDetails;

    @FXML
    private CheckBox spoofIP;

    @FXML
    private CheckBox randomise_packet;

    @FXML
    private Slider slider_packets;

    @FXML
    private RadioButton none;

    @FXML
    private RadioButton silentScan;

    @FXML
    private RadioButton aggressiveScan;

    @FXML
    private RadioButton fastScan;

    @FXML
    private RadioButton udp_protocol;

    @FXML
    private RadioButton tcp_protocol;


    /*** Local Variables ***/
    SYNFlooder synFlooder;
    UDPFlooder udpFlooder;
    String path;

    public MainController() {}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticTxtArea = textAreaUI;
    }

    @FXML
    void scanPressed(ActionEvent event) throws ExecutionException, InterruptedException, MalformedURLException {
        /* Default Values*/
        int lower = 1;
        int upper = 65535;
        String ip = targetIPAddress.getText();
        lower = Integer.parseInt(txt_portNumberLower.getText());
        upper = Integer.parseInt(txt_portNumberUpper.getText());
        
        if(!(path == null)) {
            CustomScript cs = new CustomScript(path);
        }

        if(portDetails.isSelected()) {
            PortScanner.nmapScanner(ip, txt_portNumberLower.getText(), txt_portNumberUpper.getText());
        } else {
            /**
             * TODO Currently scanning port 80 but can be built to be scalable with other services.
             */
            System.out.println("PortScanner is scanning open ports on: " + ip);
            PortScanner ps = new PortScanner(ip, lower, upper);
        }

        /* Advanced Features */
        if(silentScan.isSelected()) {
            StealthScan ss = new StealthScan(ip);
        }
        if(aggressiveScan.isSelected()) {
            AggressiveScan aggressiveScan = new AggressiveScan(ip);
        }
        if(fastScan.isSelected()) {
            FastScan fastScan = new FastScan(ip);
        }
    }

    @FXML
    void startAttack(ActionEvent event) throws UnknownHostException {
        String ip = dos_target_ip.getText();
        String port = dos_port.getText();
        String packets = String.valueOf( (int) slider_packets.getValue() );
        String threads = threadCount.getText();
        String loop = txt_loop.getText();
        String duration = txt_duration.getText();

        InetAddress address = InetAddress.getByName(ip);

        if(tcp_protocol.isSelected()) {
            synFlooder = new SYNFlooder(address.getHostAddress(), port, packets, threads, loop); // packet size does not work tcp=64 bytes
        } else {
            System.err.println("Unable to start attack.");
        }

        if(udp_protocol.isSelected()){
            udpFlooder = new UDPFlooder(address.getHostAddress(), port, duration, packets);
        } else {
            System.err.println("Unable to start attack.");
        }
    }

    @FXML
    void stopAttack(ActionEvent event) {
        synFlooder.process.destroy();
        udpFlooder.process.destroy();
        System.out.println("Successfully killed all processes.");
    }

    @FXML
    void updateText(MouseEvent event) {
        packetSizeCounter.setText(String.valueOf(slider_packets.getValue()));
    }

    @FXML
    void updateSliderValue(ActionEvent event) {
        slider_packets.setValue(Double.parseDouble(packetSizeCounter.getText()));
    }

    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Python Scripts", "*.py"));
        File f = fc.showOpenDialog(null);

        if( f != null) {
            txt_selected_file.setText(""+f.getName());
        }

        path = f.getAbsolutePath();
    }

    @FXML
    void runCustomScript(ActionEvent event) {
        CustomScript cs = new CustomScript(path);
    }

    @FXML
    void clearFileSelect(ActionEvent event) {
        txt_selected_file.setText("");
        path = null;
    }
}
