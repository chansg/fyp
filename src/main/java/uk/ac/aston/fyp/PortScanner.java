package uk.ac.aston.fyp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class PortScanner {

    private String ip;
    private int lower;
    private int upper;

    public PortScanner(String ip, int lower, int upper) throws InterruptedException, ExecutionException
    {
        this.ip = ip;
        this.lower = lower;
        this.upper = upper;
        final ExecutorService es = Executors.newCachedThreadPool();
        final int timeout = 200;
        final List<Future<ScanResult>> futures = new ArrayList<>();
        // 65535 total ports to scan
        for (int port = lower; port <= upper; port++) {
            futures.add(portIsOpen(es, ip, port, timeout));
        }
        es.awaitTermination(200L, TimeUnit.MILLISECONDS);
        int openPorts = 0;
        for (final Future<ScanResult> f : futures) {
            if (f.get().isOpen()) {
                openPorts++;
                System.out.println(f.get().getPort());
            }
        }
        System.out.println("There are " + openPorts + " open ports on host " + ip + " (probed with a timeout of "
                + timeout + "ms)");
        es.shutdown();
    }



    public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
                                                final int timeout)
    {
        return es.submit(new Callable<ScanResult>() {
            @Override
            public ScanResult call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return new ScanResult(port, true);
                } catch (Exception ex) {
                    return new ScanResult(port, false);
                }
            }
        });
    }

    public static class ScanResult {
        private int port;

        private boolean isOpen;

        public ScanResult(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

    }

    public static void nmapScanner(String ip, String lp, String up) {
        try {
            ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\..\\scripts\\nmap_scanner.py", ip, lp, up);
            Process process = builder.start();

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