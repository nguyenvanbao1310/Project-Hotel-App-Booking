package com.example.hotel_project.model;

public class PaymentDTO {
    private long amount; // Số tiền thanh toán
    private String orderDescription; // Mô tả đơn hàng
    private String currency;
    // Getter and Setter
    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
