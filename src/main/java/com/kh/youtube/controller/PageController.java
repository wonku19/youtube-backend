package com.kh.youtube.controller;



import com.kh.youtube.domain.Category;
import com.kh.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private CategoryService category;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("category",category.showAll());
        return "index";
    }

}
