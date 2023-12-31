package com.confianca.cielo.ecommerce;

import com.google.gson.annotations.SerializedName;

public class Sale {
    @SerializedName("MerchantOrderId")
    private String merchantOrderId;

    @SerializedName("Customer")
    private Customer customer;

    @SerializedName("Payment")
    private Payment payment;

    public Sale(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public Customer customer(String name) {
        setCustomer(new Customer(name));
        return getCustomer();
    }

    public Customer customer(String name, String email, String birthDate, String identity, String identityType) {
        setCustomer(new Customer(name, email, birthDate, identity, identityType));
        return getCustomer();
    }

    public Payment payment(Integer amount, Integer installments) {
        setPayment(new Payment(amount, installments));
        return getPayment();
    }

    public Payment payment(Integer amount) {
        return payment(amount, 1);
    }

    public Payment payment(Integer amount, Integer installments, Payment.Currency currency, String country, String softDescriptor) {
        setPayment(new Payment(amount, installments, currency, country, softDescriptor));
        return getPayment();
    }

    public Payment payment(Integer amount, Integer installments, Payment.Currency currency, String softDescriptor) {
        setPayment(new Payment(amount, installments, currency, softDescriptor));
        return getPayment();
    }

    public Customer getCustomer() {
        return customer;
    }

    public Sale setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public Sale setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
        return this;
    }

    public Payment getPayment() {
        return payment;
    }

    public Sale setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

}
