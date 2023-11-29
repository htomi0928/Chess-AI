package Interface;

public class Clock {
    private final int startMinute;
    private final int startSecond;
    private int minute;
    private int second;

    public Clock(int minute, int second) {
        this.startMinute = minute;
        this.startSecond = second;
        this.minute = minute;
        this.second = second;
    }

    public void takeTime() {
        if (second == 0) {
            minute--;
            second = 59;
        } else second--;
    }

    public boolean isOutOfTime() {
        return second == 0 && minute == 0;
    }

    @Override
    public String toString() {
        return String.format("%02d : %02d", minute, second);
    }

    public void restart() {
        this.minute = startMinute;
        this.second = startSecond;
    }
}
