package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthStyle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.OrderObj;
import com.example.model.Product;
import com.example.model.db.ProductDAO;


@Controller
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
		System.out.println(category);
		Product pro = null;
		try {
			ArrayList<Product> products = ProductDAO.getInstance().getAllProducts().get(category);
			for (Product p : products) {
				if(p.getName().equals(productName)){
					pro = p;
					break;
				}
			}
			request.removeAttribute("product");
			request.getSession().setAttribute("crusts", ProductDAO.getInstance().getAllProducts().get("Crusts"));
			request.getSession().setAttribute("sizes", ProductDAO.getInstance().getAllProducts().get("Sizes"));
			request.getSession().setAttribute("toppings", ProductDAO.getInstance().getAllProducts().get("Toppings"));
			request.getSession().setAttribute("product", pro);
		} catch (SQLException e) {
			System.out.println("fsafa");
		}
		return "single-post";
	}
	
	@RequestMapping(value="/products",method = RequestMethod.POST)
	public String products(Model model, HttpServletRequest request, HttpSession session) {
		String[] pro = request.getParameterValues("subproduct");
		Product product = (Product) session.getAttribute("product");
		ArrayList<String> subproducts = new ArrayList<>();
		subproducts.add(request.getParameter("size"));
		subproducts.add(request.getParameter("crust"));
		if(pro != null){			
			for (String string : pro) {
				subproducts.add(string);
			}
		}
		
		ArrayList<OrderObj> p = (ArrayList<OrderObj>) session.getAttribute("products");
		System.out.println(product.getName());
		String description = product.getName();
		for (String strg : product.getSubproducts()) {
			if(!subproducts.contains(strg)){
				description = description.concat(" -"+strg);
				subproducts.remove(strg);
			}
		}
		
		for (String strg : subproducts) {
			description = description.concat(" +"+strg);
			System.out.println(strg);
		}
		OrderObj obj = new OrderObj();
		obj.setDescription(description);
		obj.setProduct(product);
		p.add(obj);
		System.out.println(description);
		double price = (double) session.getAttribute("totalPrice");
		price = price + product.getPrice();
		session.setAttribute("totalPrice", price);
		session.setAttribute("products", p);
		session.setAttribute(product.toString(), description);
		session.setAttribute("productsNumber", p.size());
		return "cart";
	}
	
	@RequestMapping(value="/deleteOrderObj",method = RequestMethod.POST)
	public void deleteObj(HttpSession session, HttpServletRequest request) {
		ArrayList<OrderObj> p = (ArrayList<OrderObj>) session.getAttribute("products");
		String obj = request.getParameter("name");
		System.out.println(obj);
		OrderObj order = null;
		for (OrderObj orderObj : p) {
			if(orderObj.toString().equals(obj)){
				order = orderObj;
				break;
			}
		}
		double price = (double) session.getAttribute("totalPrice");
		System.out.println(price);
		price = price - order.getProduct().getPrice();
		System.out.println(price);
		session.setAttribute("totalPrice", price);
		if(p.remove(order)){
			System.out.println("item was removed");
		}
		session.setAttribute("productsNumber", p.size());
		
	}
	
}
