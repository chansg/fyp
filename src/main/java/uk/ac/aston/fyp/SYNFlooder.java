package uk.ac.aston.fyp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SYNFlooder {

    ProcessBuilder builder;
    Process process;

    public SYNFlooder(String ip, String port, String packetsToSend, String threads, String loop) {
        try {
            builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\..\\scripts\\tcp_flooder.py", ip, "80", packetsToSend, threads, loop);
            process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String lines = null;
            while((lines=reader.readLine())!=null) {
                System.out.println("[*] " + lines);
            }

            while((lines=readers.readLine())!=null) {
                System.out.println("[!] " + lines);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopProcess() {
        process.destroy();
    }
}
