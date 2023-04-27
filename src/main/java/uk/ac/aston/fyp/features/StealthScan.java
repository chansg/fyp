package uk.ac.aston.fyp.features;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StealthScan
{
    ProcessBuilder builder;
    Process process;
    /* Advanced Features */

    public StealthScan(String ip) {
        try {
            builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\..\\scripts\\stealth_scan.py", ip);
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

