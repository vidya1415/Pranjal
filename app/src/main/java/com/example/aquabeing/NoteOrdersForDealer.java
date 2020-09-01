package com.example.aquabeing;

public class NoteOrdersForDealer {
    private String customer_name;
    private String customer_address, brand, type, amount, total_price;
    public NoteOrdersForDealer(){
        //empty construtor;
    }

    public NoteOrdersForDealer(String customer_name, String customer_address, String brand, String type, String amount, String total_price) {
        this.customer_name = customer_name;
        this.customer_address = customer_address;
        this.brand = brand;
        this.type = type;
        this.amount = amount;
        this.total_price = total_price;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getTotal_price() {
        return total_price;
    }
}