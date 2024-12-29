import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class WeatherComparison {

    public static void main(String[] args) {
        CompletableFuture<Weather> city1Weather = fetchWeatherData("City1");
        CompletableFuture<Weather> city2Weather = fetchWeatherData("City2");
        CompletableFuture<Weather> city3Weather = fetchWeatherData("City3");

        CompletableFuture<Void> allWeatherFetched = CompletableFuture.allOf(city1Weather, city2Weather, city3Weather);

        allWeatherFetched.thenRun(() -> {
            try {
                Weather weather1 = city1Weather.get();
                Weather weather2 = city2Weather.get();
                Weather weather3 = city3Weather.get();

                compareWeather(weather1, weather2, weather3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.anyOf(city1Weather, city2Weather, city3Weather)
                .thenAccept(firstWeather -> System.out.println("First weather data received: " + firstWeather));

        // Чекаємо завершення для демонстрації
        simulateDelay(5);
    }

    private static CompletableFuture<Weather> fetchWeatherData(String city) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay(1 + ThreadLocalRandom.current().nextInt(3));
            return new Weather(city, ThreadLocalRandom.current().nextInt(15, 35),
                    ThreadLocalRandom.current().nextInt(50, 90),
                    ThreadLocalRandom.current().nextInt(0, 20));
        });
    }

    private static void compareWeather(Weather... weathers) {
        for (Weather weather : weathers) {
            System.out.println(weather);
            if (weather.getTemperature() > 25 && weather.getHumidity() < 70 && weather.getWindSpeed() < 10) {
                System.out.println(weather.getCity() + " is suitable for a beach trip.");
            } else {
                System.out.println(weather.getCity() + " is better for a warm outfit.");
            }
        }
    }

    private static void simulateDelay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
