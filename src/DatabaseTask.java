import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class DatabaseTask {

    public static void main(String[] args) {
        // Фетч даних (наприклад, температура)
        CompletableFuture<Integer> fetchTemperature = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2); // Імітація затримки отримання даних
            int temperature = new Random().nextInt(35); // Генерація випадкової температури 0-34
            System.out.println("Fetched temperature: " + temperature + "°C");
            return temperature;
        });

        // Обробка даних
        CompletableFuture<String> analyzeTemperature = fetchTemperature.thenCompose(temp -> CompletableFuture.supplyAsync(() -> {
            simulateDelay(1); // Імітація обробки
            return temp > 20 ? "Warm weather" : "Cool weather";
        }));

        // Вивід результату
        analyzeTemperature.thenAccept(result -> System.out.println("Analysis result: " + result));

        // Чекаємо завершення для демонстрації
        simulateDelay(5);
    }

    private static void simulateDelay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
