package com.kh.youtube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("test", "마지막 시간!");
        return "index";
    }

}
