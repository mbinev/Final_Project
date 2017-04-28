package com.example.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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

	@RequestMapping(value = "/catalog", method = RequestMethod.GET)
	public String products(Model model) throws SQLException {
		List<String> categories = null;
		List<Product> products = new ArrayList<>();
		HashMap<String, ArrayList<Product>> items = ProductDAO.getInstance().getAllItems();
		categories = new ArrayList<String>(items.keySet());
		for (String category : categories) {
			products.addAll(items.get(category));
		}
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		return "products";
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String products(HttpServletRequest request, Model model) throws SQLException {
		String productName = (String) request.getParameter("product");
		String category = (String) request.getParameter("category");
		HashMap<String, ArrayList<Product>> products = ProductDAO.getInstance().getAllProducts();
		Product pro = null;
		ArrayList<Product> items = products.get(category);
		for (Product p : items) {
			if (p.getName().equals(productName)) {
				pro = p;
				break;
			}
		}
		request.removeAttribute("product");
		request.getSession().setAttribute("crusts", products.get("Crusts"));
		request.getSession().setAttribute("sizes", products.get("Sizes"));
		request.getSession().setAttribute("toppings", products.get("Toppings"));
		request.getSession().setAttribute("product", pro);
		return "single-post";
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String products(HttpServletRequest request, HttpSession session) {
		String[] pro = request.getParameterValues("subproduct");
		String productPrice = request.getParameter("productPrice");
		Product product = (Product) session.getAttribute("product");
		ArrayList<String> subproducts = new ArrayList<>();
		subproducts.add(request.getParameter("size"));
		subproducts.add(request.getParameter("crust"));
		if (pro != null) {
			for (String string : pro) {
				subproducts.add(string);
			}
		}
		ArrayList<String> objSubs = new ArrayList<>();
		ArrayList<OrderObj> p = (ArrayList<OrderObj>) session.getAttribute("products");
		String description = new String();
		for (String strg : product.getSubproducts()) {
			if (!subproducts.contains(strg)) {
				description = description.concat(" -" + strg);
				subproducts.remove(strg);
			}
		}
		if (!subproducts.isEmpty()) {
			objSubs.addAll(subproducts);
		}
		for (String strg : subproducts) {
			if (strg != null && !product.getSubproducts().contains(strg)) {
				description = description.concat(" +" + strg);
				objSubs.add(strg);
			}
		}
		OrderObj obj = new OrderObj();
		obj.setDescription(description);
		obj.setProduct(product);
		obj.setPrice(Double.parseDouble(productPrice));
		obj.setSubproducts(objSubs);
		obj.setQuantity(1);
		p.add(obj);
		double price = (double) session.getAttribute("totalPrice");
		price = price + Double.parseDouble(productPrice);
		session.setAttribute("totalPrice", round(price, 2));
		session.setAttribute("products", p);
		session.setAttribute(product.toString(), description);
		session.setAttribute("productsNumber", p.size());
		return "cart";
	}

	@RequestMapping(value = "/deleteOrderObj", method = RequestMethod.POST)
	public void deleteObj(HttpSession session, HttpServletRequest request) {
		ArrayList<OrderObj> products = (ArrayList<OrderObj>) session.getAttribute("products");
		String obj = request.getParameter("name");
		OrderObj order = null;
		for (OrderObj orderObj : products) {
			if (orderObj.toString().equals(obj)) {
				order = orderObj;
				break;
			}
		}
		double price = (double) session.getAttribute("totalPrice");
		price = price - order.getPrice();
		session.setAttribute("totalPrice", price);
		products.remove(order);
		session.setAttribute("productsNumber", products.size());
		if((int)session.getAttribute("productsNumber") == 0){
			session.setAttribute("totalPrice", 0.0);
		}

	}
	
	@RequestMapping(value = "/changeAttributes", method = RequestMethod.POST)
	public void changeAttributes(HttpSession session, HttpServletRequest request) {
		ArrayList<OrderObj> products = (ArrayList<OrderObj>) session.getAttribute("products");
		String obj = request.getParameter("name");
		System.out.println(obj);
		System.out.println(request.getParameter("numbers"));
		int numbers = Integer.parseInt(request.getParameter("numbers"));
		OrderObj order = null;
		for (OrderObj orderObj : products) {
			if (orderObj.toString().equals(obj)) {
				order = orderObj;
				break;
			}
		}
		System.out.println(order);
		System.out.println(products);
		order.setQuantity(numbers);
		String totalPrice = request.getParameter("totalPrice");
		String productCount = request.getParameter("productCount");
		double price = Double.parseDouble(totalPrice);
		int count = Integer.parseInt(productCount);
		session.setAttribute("totalPrice", price);
		session.setAttribute("productsNumber", count);
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	@RequestMapping(value = "/directBuy", method = RequestMethod.GET)
	public String directBuy(HttpServletRequest request) throws SQLException {
		String productName = (String) request.getParameter("product");
		String category = (String) request.getParameter("category");
		HttpSession session = request.getSession();
		Double productPrice = null;
		HashMap<String, ArrayList<Product>> products = ProductDAO.getInstance().getAllProducts();
		Product product = null;
		ArrayList<Product> items = products.get(category);
		for (Product p : items) {
			if (p.getName().equals(productName)) {
				product = p;
				productPrice = p.getPrice();
				break;
			}
		}
		ArrayList<String> subproducts = new ArrayList<>();
		if(category.equals("Pizzaz")){			
			subproducts.add("Medium (6 Slices)");
			subproducts.add("Hand Tossed");
		}
		for (String product2 : product.getSubproducts()) {
			request.setAttribute("subproduct", product2);
			productPrice += 1.25;
		}
		if (!product.getSubproducts().isEmpty()) {
			for (String string : product.getSubproducts()) {
				subproducts.add(string);
			}
		}
		ArrayList<String> objSubs = new ArrayList<>();
		ArrayList<OrderObj> p = (ArrayList<OrderObj>) session.getAttribute("products");
		String description = new String();
		for (String strg : product.getSubproducts()) {
			if (!subproducts.contains(strg)) {
				description = description.concat(" -" + strg);
				subproducts.remove(strg);
			}
		}
		if (!subproducts.isEmpty()) {
			objSubs.addAll(subproducts);
		}
		for (String strg : subproducts) {
			if (strg != null && !product.getSubproducts().contains(strg)) {
				description = description.concat(" +" + strg);
				objSubs.add(strg);
			}
		}
		OrderObj obj = new OrderObj();
		obj.setDescription(description);
		obj.setProduct(product);
		obj.setPrice(productPrice);
		obj.setSubproducts(objSubs);
		obj.setQuantity(1);
		p.add(obj);
		System.out.println(p);
		double price = (double) session.getAttribute("totalPrice");
		price = price + productPrice;
		session.setAttribute("totalPrice", round(price, 2));
		session.setAttribute("products", p);
		session.setAttribute(product.toString(), description);
		session.setAttribute("productsNumber", p.size());
		return "cart";
	}

}
