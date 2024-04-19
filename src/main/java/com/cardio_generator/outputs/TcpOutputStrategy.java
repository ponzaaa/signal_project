package com.cardio_generator.outputs;
//USED CHATGPT FOR THE JAVADOC HERE, I WASN'T ABLE TO DO IT WITHOUT
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * A TCP/IP implementation of the {@link OutputStrategy} for outputting patient health data.
 * Once a connection is established, health data is sent to the connected client in real-time.
 */
public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

    /**
     * Constructs a {@code TcpOutputStrategy} that initializes a TCP server on the specified port.
     * The constructor handles client connections in a separate thread to avoid blocking the main application.
     *
     * @param port The port number on which the server will listen for connections.
     */
    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Outputs health data over a TCP connection. If a client is connected, it formats the data as
     * a comma-separated string and sends it across the connection. The data format is:
     * "patientId,timestamp,label,data".
     *
     * @param patientId The unique identifier for the patient whose data is to be outputted.
     * @param timestamp The timestamp at which the data was created.
     * @param label     A label describing the type of data being output.
     * @param data      The actual data to be output, formatted as a String.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
