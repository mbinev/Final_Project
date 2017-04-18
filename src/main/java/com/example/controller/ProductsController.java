package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Product;
import com.example.model.db.ProductDAO;


@Controller
@SessionAttributes("product")
public class ProductsController {

	@RequestMapping(value="/products",method = RequestMethod.GET)
	public String products(Model model, HttpServletRequest request) {
		List<String> categories = null;
		List<Product> products = new ArrayList<>();
		try {
			categories = new ArrayList<String>(ProductDAO.getInstance().getAllProducts().keySet());
			for (String category : categories) {
				products.addAll(ProductDAO.getInstance().getAllProducts().get(category));
			}
		} catch (SQLException e) {
			System.out.println("MenuServlet problem");
			e.printStackTrace();
		}
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		System.out.println(products);
		System.out.println(categories);
		return "products";
	}
	
	@RequestMapping(value="/products/{product_id}",method = RequestMethod.GET)
	public String products(Model model, @PathVariable("product_id") Integer productId) {
//		Product product = ...get product by id from somewhere 
//		model.addAttribute("product", product);
		System.out.println(productId);
		
		return "product";
	}
	
	@RequestMapping(value="/products",method = RequestMethod.POST)
	public String products(@ModelAttribute Product p) {
		
		//add the new product somewhere
		
		System.out.println(p.getName());
		
		// redirect to home page
		return "redirect:index.html";
	}
	
}
