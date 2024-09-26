public class Product {
    private String name;
    private float price;
    private float protein;
    private float carbohydrate;
    private float fat;

    private float calorie;

    public Product(String name, float price, float protein, float carbohydrate, float fat) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calorie = (4*protein + 4* carbohydrate + 9 * fat);
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public float getProtein() {
        return protein;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public float getFat() {
        return fat;
    }

    public float getCalorie() {
        return calorie;
    }


}
