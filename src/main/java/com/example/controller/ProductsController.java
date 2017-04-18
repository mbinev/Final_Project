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

	@RequestMapping(value="/catalog",method = RequestMethod.GET)
	public String products(Model model) {
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
	
	@RequestMapping(value="/products",method = RequestMethod.GET)
	public String products(HttpServletRequest request, Model model) {
		String productName = (String) request.getParameter("product");
		String category = (String) request.getParameter("category");
		System.out.println(productName);
		System.out.println("category" + request.getParameter("category"));
		Product pro = null;
		try {
			ArrayList<Product> products = ProductDAO.getInstance().getAllProducts().get(category);
			for (Product p : products) {
				if(p.getName().equals(productName)){
					pro = p;
					break;
				}
			}
			request.getSession().setAttribute("product", pro);
			ArrayList<Product> allProducts = new ArrayList<>();
			for (ArrayList<Product> arr : ProductDAO.getInstance().getAllProducts().values()) {
				allProducts.addAll(arr);
			}
			request.getSession().setAttribute("subproducts", allProducts);
		} catch (SQLException e) {
			System.out.println("fsafa");
		}
		return "single-post";
	}
	
	@RequestMapping(value="/products",method = RequestMethod.POST)
	public String products(Model model, HttpServletRequest request) {
		String[] subproducts = request.getParameterValues("subproduct");
		ArrayList<String> pro = new ArrayList<>();
		for (String string : subproducts) {
			pro.add(string);
		}
		ArrayList<Product> allProducts = new ArrayList<>();
		ArrayList<Product> products = new ArrayList<>();
		try {
			for (ArrayList<Product> arr : ProductDAO.getInstance().getAllProducts().values()) {
				allProducts.addAll(arr);
			}
			for (Product product : allProducts) {
				if(pro.contains(product.getName()) || pro.contains("Extra "+product.getName())){
					products.add(product);
				}
			}
			request.getSession().setAttribute("products", products);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "cart";
	}
	
}
