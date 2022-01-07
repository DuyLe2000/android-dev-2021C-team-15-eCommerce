package group15.finalassignment.ecommerce.View.model;

import java.util.ArrayList;

public class Order {
    String phone = "";
    String address = "";
    Long totalCost = 0L;
    ArrayList<CartItem> itemList = new ArrayList<>();

    public Order () {}

    public Order(String phone, String address, Long totalCost, ArrayList<CartItem> itemList) {
        this.phone = phone;
        this.address = address;
        this.totalCost = totalCost;
        this.itemList = itemList;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
