package com.aggroso.expense.service;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AiClassifierService {
    public Map<String,Object> classify(String desc, String inputCat){
        String d = desc==null?"":desc.toLowerCase();
        String cat = "MISC"; double conf=60; boolean uncertain=false; String reason="Generic";
        if(d.contains("dinner")||d.contains("lunch")||d.contains("meal")||d.contains("food")||d.contains("taj")){
            cat="MEALS"; conf=95; reason="Food related keywords found";
        } else if(d.contains("flight")||d.contains("uber")||d.contains("travel")||d.contains("taxi")){
            cat="TRAVEL"; conf=92; reason="Travel keywords found";
        } else if(d.contains("hotel")||d.contains("stay")){
            cat="LODGING"; conf=90; reason="Lodging keyword";
        } else if(d.contains("??")||d.length()<3){ cat="MISC"; conf=20; uncertain=true; reason="Ambiguous description"; }
        Map<String,Object> m=new HashMap<>();
        m.put("category",cat); m.put("confidence",conf); m.put("uncertain",uncertain); m.put("reason",reason);
        return m;
    }
}