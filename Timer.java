import java.util.Date;

public class Timer {

    private Date startDate;
    private Long time;

    Timer() {

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getTime() {
        return time;
    }

    public double getTimeInSeconds() {
        return time / 1000;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void start() {
        this.startDate = new Date();
    }

    public Long end() {
        this.time = startDate.getTime() - new Date().getTime();
        return this.time;
    }

}
