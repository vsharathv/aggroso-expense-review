package com.aggroso.expense.controller;
import com.aggroso.expense.model.*;
import com.aggroso.expense.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ReviewController {
    
    @Autowired ReviewService reviewService;
    public static List<ReviewResult> historyStore = new ArrayList<>();

    @PostMapping("/review")
    public ReviewResult review(@RequestBody Claim claim){
        if(claim.id==null) claim.id = UUID.randomUUID().toString().substring(0,8);
        ReviewResult res = reviewService.review(claim);
        historyStore.add(res);
        return res;
    }

    @GetMapping("/history")
    public List<ReviewResult> history(){
        return historyStore;
    }

    @PostMapping("/action")
    public Map<String,String> action(@RequestBody Map<String,String> req){
        String claimId = req.get("claimId");
        String act = req.get("action");
        String reason = req.get("reason");
        for(ReviewResult r: historyStore){
            if(r.claimId.equals(claimId)){
                Map<String,String> h=new HashMap<>();
                h.put("action",act);
                h.put("time",java.time.LocalDateTime.now().toString());
                h.put("reason",reason!=null?reason:"");
                r.history.add(h);
                r.finalStatus = act;
                break;
            }
        }
        return Map.of("status","ok","message","Action "+act+" done");
    }
}