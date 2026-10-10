package com.aggroso.expense.model;
import java.util.*;
public class ReviewResult {
    public String claimId;
    public String claimant;
    public String finalStatus;
    public String aiCategory;
    public String originalCategory;
    public double confidence;
    public boolean uncertain;
    public List<String> policyEvidence = new ArrayList<>();
    public List<String> issues = new ArrayList<>();
    public List<String> missingInfo = new ArrayList<>();
    public String explanation;
    public List<Map<String,String>> history = new ArrayList<>();
    public double totalForClaimant;
    public boolean isDuplicate;
}