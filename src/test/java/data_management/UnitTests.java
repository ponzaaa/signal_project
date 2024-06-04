package data_management;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.*;
import com.alerts.objects.decorators.AlertSorter;
import com.alerts.objects.decorators.PriorityAlertDecorator;
import com.alerts.objects.decorators.PriorityAlertSorter;
import com.cardio_generator.HealthDataSimulator;
import com.cardio_generator.outputs.ConsoleOutputStrategy;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;
import com.data_management.DataStorage;
import com.data_management.RealTimeWebSocketClient;
import com.data_management.records.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTests {

    @BeforeEach
    void setUp() throws InterruptedException {
        DataStorage.resetInstance();
        HealthDataSimulator.resetInstance();
        Thread.sleep(2000);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        DataStorage.resetInstance();
        HealthDataSimulator.resetInstance();
        Thread.sleep(2000);
    }

    @Test
    public void testDataStorageSingleton(){
        DataStorage dataStorage = DataStorage.getDataStorage();
        assertNotNull(dataStorage);
        DataStorage dataStorage2 = DataStorage.getDataStorage();
        assertEquals(dataStorage, dataStorage2);
    }

    @Test
    public void testRecords(){
        // Initialize some values
        Patient patient14 = new Patient(14);
        Patient patient25 = new Patient(25);
        patient14.addRecord(60, "ECG", 100201);
        patient25.addRecord(99, "Saturation", 1202);
        patient14.addRecord(100, "Diastolic Pressure", 18930);
        patient14.addRecord(110, "Diastolic Pressure", 18931);

        // Test correct parsing and creation
        assertNotNull(patient14); assertNotNull(patient25);
        assertNotNull(patient14.getPatientRecords());
        assertNotNull(patient25.getPatientRecords());
        assertNotNull(patient14.getRecordAtTime("ECG", 100201));
        assertNotNull(patient25.getRecordAtTime("Saturation", 1202));
        assertNotNull(patient14.getRecordAtTime("Diastolic Pressure", 18930));
        assertNull(patient25.getRecordAtTime("Diastolic Pressure", 18930));
        assertEquals("Patient ID: 14, ECG: 60.0, Timestamp: 100201",
                patient14.getRecordAtTime("ECG", 100201).toString());
        assertEquals("Patient ID: 25, Saturation: 99.0, Timestamp: 1202",
                patient25.getRecordAtTime("Saturation", 1202).toString());
        assertEquals("Patient ID: 14, Diastolic Pressure: 100.0, Timestamp: 18930",
                patient14.getRecordAtTime("Diastolic Pressure", 18930).toString());
    }

    @Test
    public void testAddStorage() {
        DataStorage dataStorage = DataStorage.getDataStorage();
        dataStorage.addPatientData(20, 70, "ECG", 10000);
        dataStorage.addPatientData(20, 95, "Saturation", 10000);
        dataStorage.addPatientData(30, 100, "Systolic Pressure", 10000);
        dataStorage.addPatientData(30, 100, "Diastolic Pressure", 10000);
        dataStorage.addPatientData(10, 150, "Cholesterol", 10000);
        dataStorage.addPatientData(10, 150, "Red Blood Cells", 10000);

        // Check that the variables are not null
        assertNotNull(dataStorage);
        assertNotNull(dataStorage.getPatientMap());
        assertNotNull(dataStorage.getPatient(20));
        assertNotNull(dataStorage.getPatient(20).getPatientRecords());

        // Check that the registered values are correct
        assertEquals("Patient ID: 20, ECG: 70.0, Timestamp: 10000", dataStorage.getPatient(20)
                .getRecord("ECG").get(0).toString());
        assertEquals("Patient ID: 20, Saturation: 95.0, Timestamp: 10000", dataStorage.getPatient(20)
                .getRecord("Saturation").get(0).toString());
        assertEquals("Patient ID: 30, Systolic Pressure: 100.0, Timestamp: 10000",
                dataStorage.getPatient(30).getRecord("Systolic Pressure").get(0).toString());
        assertEquals("Patient ID: 30, Diastolic Pressure: 100.0, Timestamp: 10000", dataStorage.getPatient(30)
                .getRecord("Diastolic Pressure").get(0).toString());
        assertEquals("Patient ID: 10, Cholesterol: 150.0, Timestamp: 10000", dataStorage.getPatient(10)
                .getRecord("Cholesterol").get(0).toString());
        assertEquals("Patient ID: 10, Red Blood Cells: 150.0, Timestamp: 10000", dataStorage.getPatient(10)
                .getRecord("Red Blood Cells").get(0).toString());
    }

    @Test
    public void testHealthGeneratorSingleton(){
        // Check that there is indeed only one Singleton object
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(400);
        OutputStrategy console = new ConsoleOutputStrategy();
        HealthDataSimulator healthDataSimulator = HealthDataSimulator.getHealthDataSimulator(100,
                scheduler, console);
        assertNotNull(healthDataSimulator);
        HealthDataSimulator healthDataSimulator2 = HealthDataSimulator.getHealthDataSimulator(200,
                scheduler, console);
        assertEquals(healthDataSimulator, healthDataSimulator2);

    }

    @Test
    public void testWebSocketClient() throws URISyntaxException, InterruptedException {
        // We start the server
        DataStorage dataStorage = DataStorage.getDataStorage();
        WebSocketOutputStrategy webSocketOutputStrategy = new WebSocketOutputStrategy(8080);
        URI uri = new URI("ws://localhost:8080");
        RealTimeWebSocketClient client = new RealTimeWebSocketClient(uri, dataStorage);
        assertNotNull(client);

        // We connect to the server
        client.connectToWebSocket("ws://localhost:8080");

        Thread.sleep(1500);

        // We do a some test outputs
        webSocketOutputStrategy.output(3, 1000, "Saturation", "93.0");
        webSocketOutputStrategy.output(45, 4000, "Systolic Pressure", "110.0");
        assertNotNull(webSocketOutputStrategy);

        Thread.sleep(1500);

        assertNotNull(dataStorage.getPatientMap());
        assertNotNull(dataStorage.getPatient(3));
        assertNotNull(dataStorage.getPatient(3).getRecord("Saturation"));
        assertEquals("Patient ID: 3, Saturation: 93.0, Timestamp: 1000",
                dataStorage.getPatient(3).getRecordAtTime("Saturation", 1000).toString());
        assertEquals("Patient ID: 45, Systolic Pressure: 110.0, Timestamp: 4000",
                dataStorage.getPatient(45).getRecordAtTime("Systolic Pressure", 4000)
                        .toString());
        client.close();
        webSocketOutputStrategy.stopServer();
    }

    @Test
    public void testAlertFactory(){
        DataStorage dataStorage = DataStorage.getDataStorage();
        Map<Integer, Alert> alerts = dataStorage.getAlerts();

        // Add alerts
        AlertFactory alertFactory = new AlertFactory();
        Alert alert1 = alertFactory.creatAlert(21, "Heart Rate above 100 bpm", 1213);
        alerts.put(1234, alert1);
        Alert alert2 = alertFactory.creatAlert(12, "Blood Saturation below 92%", 3212);
        alerts.put(123, alert2);
        Alert alert3 = alertFactory.creatAlert(24, "Blood Saturation below 92% and Systolic " +
                "Pressure under 90 mmHg", 1000);
        alerts.put(234, alert3);

        // Check they're there and correct
        assertNotNull(alerts.get(1234));
        assertNotNull(alerts.get(123));
        assertNotNull(alerts.get(234));
        assertEquals(alert1, alerts.get(1234));
        assertEquals(alert2, alerts.get(123));
        assertEquals(alert3, alerts.get(234));

        // Check they are of teh correct type, meaning the factory does the right thing
        assertInstanceOf(ECGAlert.class, alerts.get(1234));
        assertInstanceOf(SaturationAlert.class, alerts.get(123));
        assertInstanceOf(HypotensiveHypoxemiaAlert.class, alerts.get(234));
    }

    @Test
    public void testLiveAlerts() throws URISyntaxException, InterruptedException {
        // Start server
        DataStorage dataStorage = DataStorage.getDataStorage();
        WebSocketOutputStrategy webSocketOutputStrategy = new WebSocketOutputStrategy(8080);
        URI uri = new URI("ws://localhost:8080");
        RealTimeWebSocketClient client = new RealTimeWebSocketClient(uri, dataStorage);

        // Connect to server
        client.connectToWebSocket("ws://localhost:8080");
        Thread.sleep(1500);

        // Outputting some dangerous data
        webSocketOutputStrategy.output(70, 100, "Saturation", "91.0");
        webSocketOutputStrategy.output(70, 100, "Systolic Pressure", "50.0");
        webSocketOutputStrategy.output(34, 400, "Systolic Pressure", "100.0"); // Shouldn't trigger alert
        webSocketOutputStrategy.output(20, 300, "Diastolic Pressure", "200.0");
        webSocketOutputStrategy.output(70, 100, "Systolic Pressure", "200.0");
        webSocketOutputStrategy.output(1, 200, "ECG", "101.0");
        webSocketOutputStrategy.output(1, 200, "ECG", "71.0"); // Shouldn't trigger alert

        Thread.sleep(1500);

        // Check that alerts have been instantly created
        assertNotNull(dataStorage.getAlerts());
        assertEquals(5, dataStorage.getAlerts().size());
        client.close();
        webSocketOutputStrategy.stopServer();
    }

    @Test
    public void testSortingAlerts(){
        PriorityAlertDecorator a1 = new PriorityAlertDecorator(new AlertFactory().creatAlert(1, "Heart Rate " +
                "above 100 bpm", 1213), 2);
        PriorityAlertDecorator a2 = new PriorityAlertDecorator(new AlertFactory().creatAlert(1, "Heart Rate " +
                "above 100 bpm", 1213), 1);
        PriorityAlertDecorator a3 = new PriorityAlertDecorator(new AlertFactory().creatAlert(1, "Heart Rate " +
                "above 100 bpm", 1213), 3);
        PriorityAlertDecorator a4 = new PriorityAlertDecorator(new AlertFactory().creatAlert(1, "Heart Rate " +
                "above 100 bpm", 1213), 0);
        List<PriorityAlertDecorator> listAlerts = new ArrayList<>();
        listAlerts.add(a1); listAlerts.add(a2); listAlerts.add(a3); listAlerts.add(a4);
        PriorityAlertSorter alertSorter = new PriorityAlertSorter();
        alertSorter.sort(listAlerts);
        assertEquals(a4, listAlerts.get(0));
        assertEquals(a2, listAlerts.get(1));
        assertEquals(a1, listAlerts.get(2));
        assertEquals(a3, listAlerts.get(3));
    }

}
