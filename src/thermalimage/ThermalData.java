package thermalimage;

public class ThermalData {

    private String name;

    private double temp;

    private double seconds;

    public ThermalData() {

        this.name = "";
        this.temp = 0.0;
        this.seconds = 0.0;

    }

    public ThermalData(String name, double temp, double seconds) {

        this.name = name;
        this.temp = temp;
        this.seconds = seconds;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }
}
