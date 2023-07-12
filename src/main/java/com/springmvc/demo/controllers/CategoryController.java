package com.springmvc.demo.controllers;

import com.springmvc.demo.models.Category;
import com.springmvc.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "categories")
//http:localhost:8080/categories
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllCategories(ModelMap modelMap){
        Iterable<Category> categories = categoryRepository.findAll();

        modelMap.addAttribute("categories",categories);
        return "category";//category.jsp
    }
}
