package com.aggroso.expense.service;
import org.springframework.stereotype.Service;
import com.aggroso.expense.model.Claim;
import java.time.LocalDate;
import java.util.*;

@Service
public class ValidationService {
    public List<Claim> allClaims = new ArrayList<>();
    public Map<String,Object> validate(Claim c, Map<String,Object> policy){
        List<String> issues=new ArrayList<>(); List<String> missing=new ArrayList<>();
        boolean duplicate=false;
        if(c.claimant==null||c.claimant.isBlank()) missing.add("claimant name");
        if(c.description==null||c.description.isBlank()) missing.add("description");
        if(c.date==null) missing.add("date"); else if(c.date.isAfter(LocalDate.now())) issues.add("Future date not allowed");
        else if(c.date.isBefore(LocalDate.now().minusDays(90))) issues.add("Claim older than 90 days");
        if(c.amount<=0) issues.add("Invalid amount");
        if(policy!=null){
            double limit = ((Number)policy.get("limit")).doubleValue();
            if(c.amount>limit) issues.add("Exceeds "+policy.get("name")+" limit $"+limit);
            double receiptThreshold = ((Number)policy.get("receiptThreshold")).doubleValue();
            if(c.amount>receiptThreshold && !c.receiptAvailable) issues.add("Missing receipt - required above $"+receiptThreshold);
        }
        for(Claim old: allClaims){
            if(old.claimant!=null && old.claimant.equals(c.claimant) && Math.abs(old.amount-c.amount)<0.01 && old.description.equalsIgnoreCase(c.description)){
                if(Math.abs(old.date.toEpochDay()-c.date.toEpochDay())<=7){ duplicate=true; issues.add("Possible duplicate claim within 7 days"); break; }
            }
        }
        double total = allClaims.stream().filter(x->x.claimant!=null&&x.claimant.equals(c.claimant)).mapToDouble(x->x.amount).sum()+c.amount;
        Map<String,Object> res=new HashMap<>();
        res.put("issues",issues); res.put("missing",missing); res.put("duplicate",duplicate); res.put("total",total);
        return res;
    }
}