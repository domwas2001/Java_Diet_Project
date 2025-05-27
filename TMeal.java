
package projekt2;

public class TMeal {
    public String name;
    public int calories;
    public int protein;
    public int carbs;
    public int fat;

    public TMeal(String name, int calories, int protein, int carbs, int fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getFormatted() {
        return name + " (" + calories + " kcal, B: " + protein + "g, W: " + carbs + "g, T: " + fat + "g)";
    }
}