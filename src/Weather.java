public class Weather {
    private final String city;
    private final int temperature;
    private final int humidity;
    private final int windSpeed;

    public Weather(String city, int temperature, int humidity, int windSpeed) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    @Override
    public String toString() {
        return String.format("Weather in %s: Temp=%d°C, Humidity=%d%%, WindSpeed=%dkm/h", city, temperature, humidity, windSpeed);
    }
}
