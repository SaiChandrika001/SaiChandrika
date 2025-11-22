package com.example.controllr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.model.Product;
@Controller
public class ProductController {

	
	   @Autowired
	    private RestTemplate restTemplate;

	    private final String REST_URL = "http://localhost:8080/api/products";

	    @GetMapping("/products")
	    public String showProducts(Model model) {
	        Product[] products = restTemplate.getForObject(REST_URL, Product[].class);
	        model.addAttribute("products", products);
	        return "product";
	    }
	
}
