package com.example.aquabeing.models;

public class cartmodel {

    private String brand;
    private String quantity;
    private String total_price;
    private String customer_name;
    private String dealer_name;
    private String customer_id;
    private String amount,dealer_id;

    public cartmodel(){}

    public cartmodel(String brand, String price, String quantity, String total_price, String customername, String dealername,String customer_id,String amount,String dealer_id) {
        this.brand = brand;
        this.quantity = quantity;
        this.total_price = total_price;
        this.customer_name = customername;
        this.dealer_name = dealername;
        this.customer_id = customer_id;
        this.amount = amount;
        this.dealer_id = dealer_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealername) {
        this.dealer_name = dealername;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }
}