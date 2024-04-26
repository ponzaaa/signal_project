# Cardio Data Simulator

The Cardio Data Simulator is a Java-based application designed to simulate real-time cardiovascular data for multiple patients. This tool is particularly useful for educational purposes, enabling students to interact with real-time data streams of ECG, blood pressure, blood saturation, and other cardiovascular signals.

## Features

- Simulate real-time ECG, blood pressure, blood saturation, and blood levels data.
- Supports multiple output strategies:
  - Console output for direct observation.
  - File output for data persistence.
  - WebSocket and TCP output for networked data streaming.
- Configurable patient count and data generation rate.
- Randomized patient ID assignment for simulated data diversity.

## Getting Started

### Prerequisites

- Java JDK 11 or newer.
- Maven for managing dependencies and compiling the application.

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/tpepels/signal_project.git
   ```

2. Navigate to the project directory:

   ```sh
   cd signal_project
   ```

3. Compile and package the application using Maven:
   ```sh
   mvn clean package
   ```
   This step compiles the source code and packages the application into an executable JAR file located in the `target/` directory.

### Running the Simulator

After packaging, you can run the simulator directly from the executable JAR:

```sh
java -jar target/target/cardio_generator-1.0-SNAPSHOT.jar
```

To run with specific options (e.g., to set the patient count and choose an output strategy):

```sh
java -jar target/target/cardio_generator-1.0-SNAPSHOT.jar --patient-count 100 --output file:./output
```

### Supported Output Options

- `console`: Directly prints the simulated data to the console.
- `file:<directory>`: Saves the simulated data to files within the specified directory.
- `websocket:<port>`: Streams the simulated data to WebSocket clients connected to the specified port.
- `tcp:<port>`: Streams the simulated data to TCP clients connected to the specified port.

## UML Class Diagrams

Some UML Class diagrams are also included under the directory *uml_diagrams*. Here follows a brief explanation of the rationale behind them,, as well as a short summary of the classes' uses and relationships.

**HealthyDataGenerator** class contains the main method and takes and parses input from the user. It then calls each of the Generators' classes, which all implement the **PatientDataGenerator** interface. Each of those classes contain an output method, which calls/uses some class that implements the **OutputStrategy** interface, pre-decided from the input. Those classes then are responsible for the outputting of the randomly generated health data for the indicated number of patients.

The **DataStorage** class operates as a database, with methods to get and set various types of health data associated with a **PatientData object**. The **DataRetriever** acts as a service to fetch this data, possibly for use by medical personnel. It uses a login system, implying access control to sensitive patient information.

The **Data Access System** is the class-structure used for handling incoming data via different protocols such as TCP, WebSocket, and file-based data streams. These data inputs are handled by respective listeners (TCPDataListener, WebSocketDataListener, FileDataListener) that all funnel into the **DataParser**. The parsed data is then stored through the **DataSourceAdapter**, which abstracts the data storage implementation.

The **Patient Identification System ** revolves around the **PatientIdentifier**, which has a method to match patient IDs and retrieve their records. It's connected to **IdentityManager**, suggesting a system for handling discrepancies in patient identification. The **PatientRecord** is a data model that includes personal and medical details of a patient and can be updated and displayed as needed.

The **Alert Generation System** is made up of the **AlertGenerator**, which scans the received real-time data and personal thresholds to detect anomalies. Upon finding an issue, it generates and dispatches alerts via an **AlertManager**, which then routes the information to the appropriate hospital section.

## UML State Diagrams

I included a UML State Diagram that shows the life cycle of an alert event. The process begins with checking a patient's data and loops through this state until it finds data points exceeding predefined thresholds. It then checks against historical patient data in **DataStorage** to confirm if it's an aberration or part of a trend. If a trend is detected, the system creates an alert and sends it to the nursing staff. After sending the alert, it enters a holding pattern, waiting for the next data input or for the nurse to address the issue manually.

## UML Sequence Diagram

What follows is an explanation of the sequence diagram showing the life cycle of an alert event: it starts with the **DataListener** that sends patient data to **AlertGenerator**. The AlertGenerator may cross-reference this input with past data held in **DataStorage** to determine if the current data is part of a worrying trend. If so, it updates the records and instructs the **AlertManager** to send an alert to the nursing staff, represented by the final object in the sequence, a Nurse.




## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Project Members

- Student ID: i6350810
