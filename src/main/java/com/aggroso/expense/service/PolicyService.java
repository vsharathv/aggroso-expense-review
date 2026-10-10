package com.aggroso.expense.service;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class PolicyService {
    public Map<String, Map<String,Object>> policies = new HashMap<>();
    public PolicyService(){
        policies.put("MEALS", Map.of("name","MEALS","limit",75.0,"receiptThreshold",25.0,"description","Meals allowed up to $75 per meal"));
        policies.put("TRAVEL", Map.of("name","TRAVEL","limit",500.0,"receiptThreshold",50.0,"description","Travel allowed up to $500"));
        policies.put("LODGING", Map.of("name","LODGING","limit",300.0,"receiptThreshold",0.0,"description","Lodging up to $300"));
        policies.put("MISC", Map.of("name","MISC","limit",100.0,"receiptThreshold",20.0,"description","Misc expenses"));
    }
    public Map<String,Object> getPolicy(String category){
        if(category==null) return policies.get("MISC");
        return policies.getOrDefault(category.toUpperCase(), policies.get("MISC"));
    }
}