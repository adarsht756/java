import java.util.Observable;
import java.util.Observer;

/**
 * CurrentConditionsDisplay
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {
    private float humidity;
    private float temperature;
    Observable observable;
    // private Subject weatherData;

    public CurrentConditionsDisplay(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    public void update (Observable obs, Object arg) {
        if (obs instanceof WeatherData) {
            WeatherData weatherData = (WeatherData)obs;
            this.temperature = weatherData.getTemperature();
            this.humidity = weatherData.getHumidity();
            display();
        }
    }

    public void display() {
            System.out.println("Current conditions: " + temperature
            + "F degrees and " + humidity + "% humidity");
    }

    // public void update (float temperature, float humidity, float pressure) {
        // public CurrentConditionsDisplay(Subject weatherData) {
        // this.weatherData = weatherData;
        // weatherData.registerObserver(this);
        //  }

    //     this.temperature = temperature;
    //     this.humidity = humidity;
    //     display();
    // }
}