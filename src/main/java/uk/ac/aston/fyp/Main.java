package uk.ac.aston.fyp;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class Main extends javafx.application.Application {

    private final PipedInputStream pipeIn = new PipedInputStream();
    private final PipedInputStream pipeIn2 = new PipedInputStream();
    Thread errorThrower;
    private Thread reader;
    private Thread reader2;
    boolean quit;
    private TextArea txtArea;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 440);
        stage.setTitle("180129708-FYP");
        stage.setScene(scene);
        stage.show();

        txtArea = MainController.staticTxtArea;

        // Thread execution for reading output stream
        executeReaderThreads();

        // Thread closing on stage close event
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                closeThread();
                Platform.exit();
                System.exit(0);
            }
        });
    }

    synchronized void closeThread() {
        System.out.println("System message: Stage is closed.");
        this.quit = true;
        notifyAll();
        try { this.reader.join(1000L); this.pipeIn.close(); } catch(Exception e) {
        } try {this.reader2.join(1000L); this.pipeIn2.close(); } catch(Exception e) {
        } System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }

    public void executeReaderThreads() {
        try {
            PipedOutputStream pout = new PipedOutputStream(this.pipeIn);
            System.setOut(new PrintStream(pout, true));
        } catch(IOException io) {

        } catch(SecurityException se) {

        }
        try {
            PipedOutputStream pout2 = new PipedOutputStream(this.pipeIn2);
            System.setErr(new PrintStream(pout2, true));
        } catch(IOException io) {

        } catch(SecurityException se) {

        }

        ReaderThread obj = new ReaderThread(pipeIn, pipeIn2, errorThrower, reader, reader2, quit, txtArea);
    }
}