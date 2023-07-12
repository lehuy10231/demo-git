package com.springmvc.demo.controllers;

import com.springmvc.demo.models.Product;
import com.springmvc.demo.repositories.CategoryRepository;
import com.springmvc.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequestMapping(path = "products")
//http:localhost:8080/products/getProductsByCategoryID/C0103
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/getProductsByCategoryID/{categoryID}", method = RequestMethod.GET)
    public String getProductsByCategoryID(ModelMap modelMap, @PathVariable String categoryID){
        Iterable<Product>  products = productRepository.findByCategoryID(categoryID);
        modelMap.addAttribute("products",products);

        return "productList"; //productList.jsp
    }
    @RequestMapping(value = "/changeCategory/{productID}", method = RequestMethod.GET)
    public String changeCategory(ModelMap modelMap, @PathVariable String productID){

        modelMap.addAttribute("categories",categoryRepository.findAll());
        modelMap.addAttribute("product", productRepository.findById(productID).get());
        return "updateProduct"; //updateProduct.jsp
    }

    @RequestMapping(value = "/insertProduct", method = RequestMethod.GET)
    public String insertProduct(ModelMap modelMap) {
        modelMap.addAttribute("product" ,new Product());
        modelMap.addAttribute("categories",categoryRepository.findAll());
        return "insertProduct";
    }

    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    public String insertProduct(ModelMap modelMap, @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insertProduct";
        }
        try {
            product.setProductID(UUID.randomUUID().toString());
            productRepository.save(product);
            return "redirect:/categories";
        } catch (Exception e) {

            return "insertProduct";
        }

    }

    @RequestMapping(value = "/deleteProduct/{productID}" , method = RequestMethod.POST )
    public String deleteProduct(ModelMap modelMap, @PathVariable String productID){
        productRepository.deleteById(productID);
        return "redirect:/categories";
    }
    @RequestMapping(value = "/updateProduct/{productID}" , method = RequestMethod.POST)
    public String updateProduct(ModelMap modelMap , @ModelAttribute("product") Product product,  @PathVariable String productID) {

        if (productRepository.findById(productID).isPresent()) {
            Product foundProduct = productRepository.findById(product.getProductID()).get();
            if (!product.getProductName().isEmpty()){
                foundProduct.setProductName(product.getProductName());
            }
            if (!product.getProductID().isEmpty()){
                foundProduct.setCategoryID(product.getCategoryID());
            }
            if (!product.getDescription().isEmpty()){
                foundProduct.setDescription(product.getDescription());
            }
            if (product.getPrice() > 0 ){
                foundProduct.setPrice(product.getPrice());
            }
            productRepository.save(foundProduct);
        }

        return "redirect:/products/getProductsByCategoryID/"+product.getCategoryID();

    }

}
