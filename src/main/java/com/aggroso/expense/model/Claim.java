package com.aggroso.expense.model;
import java.time.LocalDate;
public class Claim {
    public String id;
    public String claimant;
    public LocalDate date;
    public String category;
    public double amount;
    public String currency;
    public String description;
    public boolean receiptAvailable;
    public String rawInputCategory;
    public Claim() {}
}