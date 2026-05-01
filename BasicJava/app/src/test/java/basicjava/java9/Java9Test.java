package basicjava.java9;

import org.junit.jupiter.api.Test;

import basicjava.java9.Java9.EchoProcess;
import basicjava.java9.Java9.MultiResolutionImage;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class Java9Test {
    @Test
    void processInfo() {
        EchoProcess echoProcess = new EchoProcess();
        Map.Entry<Process, Integer> processExit = echoProcess.echo("hello", "world");
        Process process = processExit.getKey();
        if (process != null) {
            ProcessHandle processHandle = process.toHandle();
            ProcessHandle.Info processInfo = processHandle.info();
            assertFalse(processHandle.isAlive());
            assertTrue(processInfo.totalCpuDuration().isPresent());
            assertTrue(processInfo.user().isPresent());
        }
    }

    @Test
    void unicode7() {
        // 🎔 on unicode 7
        assertTrue(Character.isDefined(0x1F394));
    }

    @Test
    void multiResolutionImage() {
        MultiResolutionImage multiResImage = new MultiResolutionImage(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB), new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB));

        // pick a higher resolution if it is greater than the lower resolution one
        Image variant = multiResImage.getVariant(150, 150);

        assertEquals(200, variant.getWidth(null));
        assertEquals(200, variant.getHeight(null));
        
        // 5. Get all available variants
        List<Image> variants = multiResImage.getVariants();
        assertEquals(2, variants.size());
    }
}
