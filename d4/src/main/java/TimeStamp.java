import java.util.Arrays;

public class TimeStamp {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String command;

    TimeStamp(String timeStamp) {
        String[] temp;
        timeStamp = timeStamp.replace("[", "");
        temp = timeStamp.split("] ");
        command = temp[1];
        temp = temp[0].split("[- :]");
        year = Integer.valueOf(temp[0]);
        month = Integer.valueOf(temp[1]);
        day = Integer.valueOf(temp[2]);
        hour = Integer.valueOf(temp[3]);
        minute = Integer.valueOf(temp[4]);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {

        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getCommand() {
        return command;
    }

    public String getFirstWordOfCommand() {
        return command.substring(0, 5);
    }

    public int getGuardId() {
        return Integer.valueOf(command.replaceAll("[^0-9]",""));
    }
}
