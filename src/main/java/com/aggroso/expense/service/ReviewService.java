package com.aggroso.expense.service;
import org.springframework.stereotype.Service;
import com.aggroso.expense.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
@Service
public class ReviewService {
    @Autowired PolicyService policyService;
    @Autowired AiClassifierService aiService;
    @Autowired ValidationService validationService;
    public ReviewResult review(Claim claim){
        Map<String,Object> ai = aiService.classify(claim.description, claim.category);
        String aiCat = (String)ai.get("category");
        Map<String,Object> policy = policyService.getPolicy(aiCat);
        Map<String,Object> val = validationService.validate(claim, policy);
        List<String> issues=(List)val.get("issues");
        List<String> missing=(List)val.get("missing");
        boolean dup=(boolean)val.get("duplicate");
        double total=(double)val.get("total");
        String status="COMPLIANT"; String exp="Claim is compliant with policy.";
        if(!missing.isEmpty()){ status="NEEDS_CLARIFICATION"; exp="Missing required information: "+String.join(", ",missing); }
        else if(!issues.isEmpty()){ status="NEEDS_REVIEW"; exp=String.join("; ",issues); }
        if((boolean)ai.get("uncertain")){ status="NEEDS_CLARIFICATION"; exp+=" AI uncertain classification."; }
        ReviewResult r=new ReviewResult();
        r.claimId=claim.id!=null?claim.id:UUID.randomUUID().toString();
        r.claimant=claim.claimant; r.finalStatus=status; r.aiCategory=aiCat; r.originalCategory=claim.category;
        r.confidence=(double)ai.get("confidence"); r.uncertain=(boolean)ai.get("uncertain");
        r.issues=issues; r.missingInfo=missing; r.explanation=exp; r.isDuplicate=dup; r.totalForClaimant=total;
        if(policy!=null) r.policyEvidence.add(policy.get("name")+": limit $"+policy.get("limit")+", receipt above $"+policy.get("receiptThreshold")+" - "+policy.get("description"));
        Map<String,String> hist=new HashMap<>(); hist.put("action",status); hist.put("time",java.time.LocalDateTime.now().toString()); hist.put("reason",exp);
        r.history.add(hist);
        validationService.allClaims.add(claim);
        return r;
    }
}
