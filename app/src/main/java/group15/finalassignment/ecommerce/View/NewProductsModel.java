package group15.finalassignment.ecommerce.View;

import java.io.Serializable;

public class NewProductsModel implements Serializable {

    String image_url;
    String description;
    String name;
    String rating;
    int price;

    public NewProductsModel() {
    }

    public NewProductsModel(String image_url, String description, String name, String rating, int price) {
        this.image_url = image_url;
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
