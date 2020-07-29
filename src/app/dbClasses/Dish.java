package app.dbClasses;

import java.sql.Blob;

public class Dish {
    private String name;
    private int price;
    private int countOfBuying;
    private Blob photo;
    private String descryption;

    public Dish(String name, int price, int countOfBuying, Blob photo, String Descryption) {
        this.name = name;
        this.price = price;
        this.countOfBuying = countOfBuying;
        this.photo = photo;
        this.descryption = Descryption;
    }

    public Dish(String DishName, Blob photo) {
        this.name = DishName;
        this.photo = photo;
    }

    public Dish() {

    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCountOfBuying() {
        return countOfBuying;
    }

    public void setCountOfBuying(int countOfBuying) {
        this.countOfBuying = countOfBuying;
    }
}
