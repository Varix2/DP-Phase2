package pt.isec.pd.spring_boot.exemplo3.models;


public class Event{
    private String name;
    private String location;
    private String date;
    private String startTime;
    private String endTime;

    public Event(String name, String location, String date, String startTime, String endTime) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
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
    @Override
    public String toString() {
        return String.format(
                "%s | %s | %s | %s | %s\n " +
                "------------------------------------------------------------- ",
                name, location, date, startTime, endTime
        );
    }
}
