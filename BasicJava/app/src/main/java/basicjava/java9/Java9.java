package basicjava.java9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.awt.*;
import java.awt.image.BaseMultiResolutionImage;
import java.awt.image.BufferedImage;

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

    public static class MultiResolutionImage {
        private final BaseMultiResolutionImage multiResImage;


        public MultiResolutionImage(BufferedImage lowRes, BufferedImage highRes) {
            createGraphics2D(lowRes, Color.RED);
            createGraphics2D(highRes, Color.BLUE);
            multiResImage = new BaseMultiResolutionImage(lowRes, highRes);
        }

        // why do we need this?
        private void createGraphics2D(BufferedImage image, Color color) {
            Graphics2D g1 = image.createGraphics();
            g1.setColor(color);
            g1.fillRect(0, 0, image.getWidth(), image.getHeight());
            g1.dispose();
        }

        public Image getVariant(double width, double height) {
            return multiResImage.getResolutionVariant(width, height);
        }

        public List<Image> getVariants() {
            return multiResImage.getResolutionVariants();
        }
    }

    public static void main(String[] args) {
        // for quick sysout testing
    }
}
