package com.alerts;

/**
 * Alert object containing the patientId for which the alert was
 * triggered, the condition of the alert, and the timestamp at which it
 * was recorded
 */
public class Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    /**
     * Alert class constructor
     * @param patientId personal identifier of the patient
     * @param condition dangerous or abnormal condition recorded
     * @param timestamp time at which the abnormality was recorded
     */
    public Alert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    /**
     * getter for patientId
     * @return patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * getter for condition detail
     * @return condition that cause the alert
     */
    public String getCondition() {
        return condition;
    }

    /**
     * getter for timestamp of alert
     * @return time component of the triggered alert
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Overwritten toString method for testing, comparing it
     * to what's supposed to be
     * @return the string representation of teh alert
     */
    @Override
    public String toString() {
        return "Alert for Patient ID: " + patientId + " - " + condition + " at " + timestamp;
    }
}
