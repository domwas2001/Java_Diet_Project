package projekt2;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TDietProcess {
    //zapisujemy plik do ścieżki
    private static final String FILE_PATH = "C:\\Users\\domin\\OneDrive\\Desktop\\studia\\java\\plan.txt";


    //lista śniadań
    private static final List<TMeal> BREAKFASTS = new ArrayList<>(Arrays.asList(
        new TMeal("Owsianka z bananem", 450, 12, 65, 10),
        new TMeal("Jajecznica z 3 jaj i pieczywem", 600, 22, 15, 30),
        new TMeal("Jogurt z musli i owocami", 450, 15, 60, 12),
        new TMeal("Omlet z warzywami i serem", 480, 20, 8, 28),
        new TMeal("Płatki kukurydziane z mlekiem i orzechami", 500, 12, 55, 18),
        new TMeal("Smoothie z bananem, mlekiem i masłem orzechowym", 400, 10, 40, 15)
    ));

    //lista obiadów
    private static final List<TMeal> LUNCHES = new ArrayList<>(Arrays.asList(

        new TMeal("Kurczak z ryżem i warzywami", 750, 45, 60, 20),
        new TMeal("Sałatka z tuńczykiem i ziemniakami", 700, 35, 40, 22),
        new TMeal("Makaron pełnoziarnisty z warzywami i serem", 700, 20, 65, 24),
        new TMeal("Łosoś z ziemniakami i surówką", 800, 40, 50, 30),
        new TMeal("Pieczony indyk z kaszą i buraczkami", 750, 38, 45, 25),
        new TMeal("Chili sin carne z ryżem", 680, 25, 70, 18)
    ));

    //lista kolacji
    private static final List<TMeal> DINNERS = new ArrayList<>(Arrays.asList(

        new TMeal("Twaróg z warzywami i pieczywem", 500, 30, 20, 18),
        new TMeal("Sałatka grecka z oliwą i pieczywem", 450, 15, 25, 28),
        new TMeal("Kanapka z hummusem i warzywami", 480, 12, 40, 22),
        new TMeal("Placki z cukinii i dip jogurtowy", 500, 15, 30, 24),
        new TMeal("Warzywa z tofu i kaszą", 600, 25, 45, 22),
        new TMeal("Zupa krem z brokułów + grzanki", 450, 10, 35, 18)
    ));

    //metoda generuje plan na 3 dni, dobierając losowo kombinacje posiłków, których kaloryczność mieści się w przedziale plus minus 50 kcal od podanego celu.
    public String generatePlan(int targetCalories) {
        StringBuilder plan = new StringBuilder();
        Random rand = new Random();

        for (int day = 1; day <= 3; day++) {
            TMeal breakfast, lunch, dinner;
            int totalCalories;
            int attempts = 0;

            //poszukiwanie dobrej kombinancji randomowo
            do {
                breakfast = getRandom(BREAKFASTS, rand);
                lunch = getRandom(LUNCHES, rand);
                dinner = getRandom(DINNERS, rand);
                totalCalories = breakfast.calories + lunch.calories + dinner.calories;
                attempts++;
                if (attempts > 1000) {
                    break;
                }
            } while (Math.abs(totalCalories - targetCalories) > 50);

            int totalProtein = breakfast.protein + lunch.protein + dinner.protein;
            int totalCarbs = breakfast.carbs + lunch.carbs + dinner.carbs;
            int totalFat = breakfast.fat + lunch.fat + dinner.fat;

            plan.append("Dzień ").append(day)
                .append(" (cel: ").append(targetCalories).append(" kcal)\n")
                .append("Śniadanie: ").append(breakfast.getFormatted()).append("\n")
                .append("Obiad: ").append(lunch.getFormatted()).append("\n")
                .append("Kolacja: ").append(dinner.getFormatted()).append("\n")
                .append("Suma: ").append(totalCalories)
                .append(" kcal | B: ").append(totalProtein).append("g")
                .append(" | W: ").append(totalCarbs).append("g")
                .append(" | T: ").append(totalFat).append("g\n\n");
        }

        //zapisywanie do pliku
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(plan.toString());
        } catch (IOException e) {
            e.printStackTrace(); //obsługa błędu w razie problemu z zapisem pliku
        }
        return plan.toString();
    }

    private TMeal getRandom(List<TMeal> list, Random rand) {
        return list.get(rand.nextInt(list.size()));
    }
}