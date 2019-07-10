package com.cafe24.mysite.controller.api;

import org.springframework.stereotype.Controller;

@Controller
public class Calcurator {
   public double sum(double a, double b){
        return a + b;
    }
}
