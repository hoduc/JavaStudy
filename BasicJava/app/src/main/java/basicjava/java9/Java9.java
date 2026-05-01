package basicjava.java9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Java9 {
    public static class EchoProcess {

        public Map.Entry<Process, Integer> echo(String... args) {
            String os = System.getProperty("os.name");
            System.out.println("os:" + os);
            
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            if (os.toLowerCase().contains("win")) {
                arguments.addFirst("echo");
                arguments.addFirst("/c");
                arguments.addFirst("cmd.exe");
            } // other os as soon as we got a hand of different machine

            System.out.println("arguments:" + arguments);
            ProcessBuilder pb = new ProcessBuilder(arguments);
            try {
                Process process = pb.start();            

                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                int exitCode = process.waitFor();
                return Map.entry(process, exitCode);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return Map.entry(null, -1);
            }
        }
    }

    public static void main(String[] args) {
        new EchoProcess().echo("hello", "world");
    }
}
