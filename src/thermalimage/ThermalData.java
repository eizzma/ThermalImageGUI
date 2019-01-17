package thermalimage;

public class ThermalData {

    private String date;

    private double temp;

    private double seconds;

    public ThermalData() {

        this.date = "";
        this.temp = 0.0;
        this.seconds = 0.0;

    }

    public ThermalData(String date, double temp, double seconds) {

        this.date = date;
        this.temp = temp;
        this.seconds = seconds;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
