import java.util.Observable;
// import java.util.Observer;
// import java.util.ArrayList;

public class WeatherData extends Observable {
    // private ArrayList<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        // observers = new ArrayList();
    }

    public void measurementsChanged() {
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    // public void registerObserver(Observer observer) {
    // observers.add(observer);
    // }

    // public void removeObserver(Observer observer) {
    // int i = observers.indexOf(observer);
    // if (i >= 0) observers.remove(i);
    // }

    // public void notifyObservers() {
    // for (int i = 0; i < observers.size(); ++i) {
    // Observer observer = (Observer)observers.get(i);
    // observer.update(temperature, humidity, pressure);
    // }
    // }
}