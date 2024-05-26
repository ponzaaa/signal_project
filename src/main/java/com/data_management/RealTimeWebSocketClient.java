package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class RealTimeWebSocketClient extends WebSocketClient implements DataReader {

    DataStorage dataStorage;

    public RealTimeWebSocketClient(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message: " + message);
        parseAndStore(message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("An error occurred:" + ex);
    }

    public void connectToWebSocket(String uri) throws URISyntaxException {
        URI serverUri = new URI(uri);
        this.uri = serverUri;
        this.connect();
    }

    @Override
    public void readData(DataStorage dataStorage) {
        // It'll remain empty, we'll use parseAndStore()
    }

    private void parseAndStore(String message) {
        // Use the same parsing logic as DataFileReader
        String[] parts = message.split(", ");
        int patientId = Integer.parseInt(parts[0].split(": ")[1]);
        long timestamp = Long.parseLong(parts[1].split(": ")[1]);
        String label = parts[2].split(": ")[1];
        String dataStr = parts[3].split(": ")[1];

        // Remove percentage sign if present
        if (dataStr.endsWith("%")) {
            dataStr = dataStr.substring(0, dataStr.length() - 1);
        }

        double data = Double.parseDouble(dataStr);

        // Add the data to the storage
        dataStorage.addPatientData(patientId, data, label, timestamp);
    }

    public static void main(String[] args) {
        try {
            DataStorage dataStorage = new DataStorage();
            URI uri = new URI("ws://localhost:8080");
            RealTimeWebSocketClient client = new RealTimeWebSocketClient(uri, dataStorage);
            client.connectToWebSocket("ws://localhost:8080");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

