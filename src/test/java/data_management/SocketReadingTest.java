package data_management;

import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.RealTimeWebSocketClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SocketReadingTest {

    private WebSocketOutputStrategy socket;
    private DataStorage dataStorage;
    private RealTimeWebSocketClient client;

    @BeforeEach
    void setUp() throws IOException, URISyntaxException, InterruptedException {
        // Start server
        socket = new WebSocketOutputStrategy(8080);

        // Initialize data storage and client
        dataStorage = new DataStorage();
        URI uri = new URI("ws://localhost:8080");
        client = new RealTimeWebSocketClient(uri, dataStorage);
        client.connectToWebSocket("ws://localhost:8080");

        // Small delay to ensure the client is connected
        Thread.sleep(1000);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        // Close the client connection and stop the server
        if (client != null) {
            client.closeConnection();
        }
        if (socket != null) {
            socket.stopServer();
        }
    }

    @Test
    void testSocketReadingStoring() throws InterruptedException {
        // Send to the WebSocket a certain line
        socket.output(2, 17000000000050L, "ECG", "90");

        // Small delay to ensure the message is processed
        Thread.sleep(1000);

        // Check that the parsed line corresponds to the correct data
        assertEquals("Patient ID: 2, Record Type: ECG, Measurement Value: 90.0, Timestamp: 17000000000050",
                dataStorage.getRecords(2, 17000000000040L, 17000000000060L).get(0).toString());
    }

    @Test
    void testSocketLiveAlertSystem() throws InterruptedException {
        socket.output(2, 17000000000030L, "Saturation", "90");

        // Small delay to ensure the message is processed
        Thread.sleep(2000);

        // Check that alerts were generated
        assertNotNull(dataStorage.getAlertGenerator().getAlerts().get(0));
    }

    @Test
    void testSocketLiveAlert() throws InterruptedException {
        socket.output(2, 17000000000030L, "Saturation", "90");
        socket.output(5, 17000000000032L, "SystolicPressure", "190");
        socket.output(1, 17000000000031L, "DiastolicPressure", "30");
        socket.output(3, 17000000000041L, "HeartRate", "45");

        // Small delay to ensure the message is processed
        Thread.sleep(2000);

        // Check that alerts were generated
        assertEquals("Alert for Patient ID: 2 - Low Saturation Alert at 17000000000030", dataStorage
                .getAlertGenerator().getAlerts().get(0).toString());
        assertEquals("Alert for Patient ID: 5 - Critical Threshold Alert for Systolic Pressure " +
                "at 17000000000032", dataStorage.getAlertGenerator().getAlerts().get(1).toString());
        assertEquals("Alert for Patient ID: 1 - Critical Threshold Alert for Diastolic Pressure " +
                "at 17000000000031", dataStorage.getAlertGenerator().getAlerts().get(2).toString());
        assertEquals("Alert for Patient ID: 3 - Abnormal Heart Rate Alert at 17000000000041", dataStorage
                .getAlertGenerator().getAlerts().get(3).toString());
    }
}
