package uk.ac.aston.fyp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UDPFlooder {

    ProcessBuilder builder;
    Process process;

    public UDPFlooder(String ip, String port, String duration, String bytes) {
        try {
            builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\..\\scripts\\udp_flooder.py", ip, port, duration, bytes);
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
}
