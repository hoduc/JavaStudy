package basicjava.java9;

import org.junit.jupiter.api.Test;

import basicjava.java9.Java9.EchoProcess;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Java9Test {
    @Test
    void processInfo() {
        EchoProcess echoProcess = new EchoProcess();
        Map.Entry<Process, Integer> processExit = echoProcess.echo("hello", "world");
        Process process = processExit.getKey();
        if (process != null) {
            ProcessHandle.Info processInfo = process.toHandle().info();
            assertTrue(processInfo.totalCpuDuration().isPresent());
        }

    }
}
