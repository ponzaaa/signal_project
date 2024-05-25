# Signal Project i6350810 - Software Engineering

The package contains:
- a Cardio Data Generator module that simulates constant information about a specifiable number of patients
- a Data Management system module that simulates the storing and retrieving of every patient's medical record
- an Alert Management system module that reads the generated data and checks for abnormal values, creating and showing the particular alert highlighting its specific details

## Code Structure + UML Diagrams

### Main Method

The software contains two main methods, selectable in the arguments line when running the program: you can run the data generator, or running the data storage and analysis. It is recommended to run the generator first, choosing as output a file directory, and then running the data storage class's main method to run the analysis and collect abnormal values.

### Cardio Generator Module

The main method here takes as input the number of patients for which we want to simulate and the output strategy; each output strategy implements the OutputStrategy Java interface, while the generated data comes from methods in the generators directory and they all implement the PatientDataGenerator interface. The UML Diagram for this module is found in the UML Diagrams Task 2 section of the project, with the caveat that the AlertGenerator has been deleted, redeveloped and moved in the Alert module.

### Data Management 

The main method registers the data that must have already been generated, stores it in a DataStorage object making use of the Patient class, containing a list PatientRecords objects (each one defining the timestamp, the data type and its value). The data is parsed and stored through the DataFileReader class, implementation of the DataReader interface. The UML Diagram for this module is found in the Task 4 section for UML Diagrams.

### Alerts

The main actor is the AlertGenerator class, which makes use of all the methods from a static toolbox class called Checkers.java in order to check each patient's data looking for abnormal and potentially dangerous values, indicating that a problem could be ongoing. This class needs a DataStorage object to read from and will generate Alert objects, recording them in a list of alerts and displaying each alert in the Console as it gets created. The UML Diagram is also found in the Task 4 section for UML Diagrams. 

## SE Weekly Task Updates

### Task 2 - UML Diagrams

Some UML Class diagrams are also included under the directory *uml_diagrams*. Here follows a brief explanation of the rationale behind them,, as well as a short summary of the classes' uses and relationships.

**HealthDataGenerator** class contains the main method and takes and parses input from the user. It then calls each of the Generators' classes, which all implement the **PatientDataGenerator** interface. Each of those classes contain an output method, which calls/uses some class that implements the **OutputStrategy** interface, pre-decided from the input. Those classes then are responsible for the outputting of the randomly generated health data for the indicated number of patients.

The **DataStorage** class operates as a database, with methods to get and set various types of health data associated with a **PatientData object**. The **DataRetriever** acts as a service to fetch this data, possibly for use by medical personnel. It uses a login system, implying access control to sensitive patient information.

The **Data Access System** is the class-structure used for handling incoming data via different protocols such as TCP, WebSocket, and file-based data streams. These data inputs are handled by respective listeners (TCPDataListener, WebSocketDataListener, FileDataListener) that all funnel into the **DataParser**. The parsed data is then stored through the **DataSourceAdapter**, which abstracts the data storage implementation.

The **Patient Identification System** revolves around the **PatientIdentifier**, which has a method to match patient IDs and retrieve their records. It's connected to **IdentityManager**, suggesting a system for handling discrepancies in patient identification. The **PatientRecord** is a data model that includes personal and medical details of a patient and can be updated and displayed as needed.

The **Alert Generation System** is made up of the **AlertGenerator**, which scans the received real-time data and personal thresholds to detect anomalies. Upon finding an issue, it generates and dispatches alerts via an **AlertManager**, which then routes the information to the appropriate hospital section.

### UML State Diagrams

I included a UML State Diagram that shows the life cycle of an alert event. The process begins with checking a patient's data and loops through this state until it finds data points exceeding predefined thresholds. It then checks against historical patient data in **DataStorage** to confirm if it's an aberration or part of a trend. If a trend is detected, the system creates an alert and sends it to the nursing staff. After sending the alert, it enters a holding pattern, waiting for the next data input or for the nurse to address the issue manually.

### UML Sequence Diagram

What follows is an explanation of the sequence diagram showing the life cycle of an alert event: it starts with the **DataListener** that sends patient data to **AlertGenerator**. The AlertGenerator may cross-reference this input with past data held in **DataStorage** to determine if the current data is part of a worrying trend. If so, it updates the records and instructs the **AlertManager** to send an alert to the nursing staff, represented by the final object in the sequence, a Nurse.

## Task 4 - UML Class Diagrams

The code is now structured in modules, among which the only modifications regard the alerts module, where the **AlertGenerator** class has been developed. It uses static methods/tools for organization purpose stored in a static class called **Checkers**, which creates **Alert** objects containing the information about that precise alert. For the data_management module we have the Main class instantiating a **DataStorage** and a **DataFileReader** implementation of the **DataReader** interface. This reader parses the output data generated previously and stores it in the DataStorage object, which contains a HashMap which connects each **Patient** to  to its **PatientRecord** objects, one for each type of value that was generated (ECG, Saturation...).
