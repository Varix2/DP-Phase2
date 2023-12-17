package pt.isec.pd.spring_boot.exemplo3.models;

import java.time.LocalDate;

public class Attendance{
    private String userName;
    private String email;
    private String eventName;
    private String location;
    private String date;
    private String startTime;
    private String endTime;

    // Constructor
    public Attendance(String userName,String email, String eventName, String location, String date, String startTime, String endTime) {
        this.userName = userName;
        this.email = email;
        this.eventName = eventName;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor
    public Attendance(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    // Getters
    public String getUserName() {
        return userName;
    }


    public String getEmail() {
        return email;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String toString() {
        return String.format("%-10s%-15s\n",userName, email) +
                "---------------------------------------------------------------------------------";
    }
}
