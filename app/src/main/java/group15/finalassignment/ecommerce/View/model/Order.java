package group15.finalassignment.ecommerce.View.model;

import java.util.ArrayList;

public class Order {
    String email = "";
    String address = "";
    Long totalCost = 0L;
    ArrayList<CartItem> itemList = new ArrayList<>();

    public Order () {}

    public Order(String email, String address, Long totalCost, ArrayList<CartItem> itemList) {
        this.email = email;
        this.address = address;
        this.totalCost = totalCost;
        this.itemList = itemList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.email = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<CartItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<CartItem> itemList) {
        this.itemList = itemList;
    }
}
