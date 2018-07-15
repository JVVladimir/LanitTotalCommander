package tools;

import java.nio.file.attribute.FileTime;

public class TimeKeeper {

    private String date;
    private String time;

    public TimeKeeper(FileTime fileTime) {
        String input = fileTime.toString();
        try {
            date = input.substring(0, input.indexOf("T"));
            time = input.substring(input.indexOf("T") + 1, input.indexOf("."));
        }catch (Exception ex) {
            date = "";
            time = "";
        }
    }


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("%s  %s", date, time);
    }
}
