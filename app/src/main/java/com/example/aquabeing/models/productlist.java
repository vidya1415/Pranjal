package com.example.aquabeing.models;

public class productlist {
    private String brand;
    private String price;
    private String  quantity;
    private String type;
    private String pid;

    private productlist() {}

    private productlist(String brand,String price,String quantity,String type) {
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }



    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
