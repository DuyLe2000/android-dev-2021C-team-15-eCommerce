package group15.finalassignment.ecommerce.View.model;

import java.io.Serializable;

public class Product implements Serializable {
    String image_url;
    String description;
    String name;
    String rating;
    Long price;

    public Product() {
    }

    public Product(String image_url, String description, String name, String rating, Long price) {
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}