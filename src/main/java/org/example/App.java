package org.example;
import java.util.Scanner;
import java.util.Arrays;

public class App {


    private static int[] prices = new int[24];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            String menu = """
                    Elpriser
                    ========
                    1. Inmatning
                    2. Min, Max och Medel
                    3. Sortera
                    4. Bästa Laddningstid (4h)
                    e. Avsluta
                    """;

            System.out.print(menu);
            choice = scanner.nextLine().trim();

            switch (choice.toLowerCase()) {
                case "1":
                    inputPrices(scanner);
                    break;
                case "2":
                    calculateMinMaxAverage();
                    break;
                case "3":
                    sortAndDisplayPrices();
                    break;
                case "4":
                    findCheapestChargingTime();
                    break;
                case "e":
                    System.out.println("Avslutar programmet.");
                    break;
                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }
        } while (!choice.equalsIgnoreCase("e"));

        scanner.close();
    }

    private static void inputPrices(Scanner scanner) {
        System.out.println("Ange elpriser för varje timme (0-23):");
        for (int i = 0; i < 24; i++) {
            System.out.print(i + "-"+ (i + 1) + ": ");
            prices[i] = Integer.parseInt(scanner.nextLine());
        }
    }

    private static void calculateMinMaxAverage() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int minHour = -1;
        int maxHour = -1;
        int total = 0;

        for (int i = 0; i < 24; i++) {
            if (prices[i] < min) {
                min = prices[i];
                minHour = i;
            }
            if (prices[i] > max) {
                max = prices[i];
                maxHour = i;
            }
            total += prices[i];
        }

        double average = total / 24.0;

        System.out.printf("Lägsta pris: %02d-%02d, %d öre/kWh\n", minHour, minHour + 1, min);
        System.out.printf("Högsta pris: %02d-%02d, %d öre/kWh\n", maxHour, maxHour + 1, max);
        System.out.printf("Medelpris: %.2f öre/kWh\n", average);
    }

    private static void sortAndDisplayPrices() {
        Integer[] sortedIndices = new Integer[24];
        for (int i = 0; i < 24; i++) {
            sortedIndices[i] = i;
        }

        Arrays.sort(sortedIndices, (a, b) -> Integer.compare(prices[b], prices[a]));

        for (int index : sortedIndices) {
            System.out.printf("%02d-%02d %d öre\n", index, index + 1, prices[index]);
        }
    }

    private static void findCheapestChargingTime() {
        int bestStart = 0;
        double bestAverage = Double.MAX_VALUE;

        for (int i = 0; i <= 20; i++) {
            double sum = prices[i] + prices[i + 1] + prices[i + 2] + prices[i + 3];
            double average = sum / 4.0;

            if (average < bestAverage) {
                bestAverage = average;
                bestStart = i;
            }
        }

        System.out.printf("Påbörja laddning klockan %d\n", bestStart);
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", bestAverage);
    }

}
