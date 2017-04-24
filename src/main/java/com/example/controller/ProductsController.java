package com.example.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	@RequestMapping(value = "/catalog", method = RequestMethod.GET)
	public String products(Model model) throws SQLException {
		List<String> categories = null;
		List<Product> products = new ArrayList<>();
		categories = new ArrayList<String>(ProductDAO.getInstance().getAllProducts().keySet());
		for (String category : categories) {
			products.addAll(ProductDAO.getInstance().getAllProducts().get(category));
		}
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
		System.out.println(products);
		System.out.println(categories);
		return "products";
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String products(HttpServletRequest request, Model model) throws SQLException {
		String productName = (String) request.getParameter("product");
		String category = (String) request.getParameter("category");
		System.out.println(productName);
		System.out.println(category);
		Product pro = null;
		ArrayList<Product> products = ProductDAO.getInstance().getAllProducts().get(category);
		for (Product p : products) {
			if (p.getName().equals(productName)) {
				pro = p;
				break;
			}
		}
		request.removeAttribute("product");
		request.getSession().setAttribute("crusts", ProductDAO.getInstance().getAllProducts().get("Crusts"));
		request.getSession().setAttribute("sizes", ProductDAO.getInstance().getAllProducts().get("Sizes"));
		request.getSession().setAttribute("toppings", ProductDAO.getInstance().getAllProducts().get("Toppings"));
		request.getSession().setAttribute("product", pro);
		System.out.println(request.getSession().getAttribute("sizes"));

		return "single-post";
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public String products(Model model, HttpServletRequest request, HttpSession session) {
		String[] pro = request.getParameterValues("subproduct");
		String productPrice = request.getParameter("productPrice");
		System.out.println(productPrice);
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
		System.out.println(product.getName());
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
			System.out.println(strg);
		}
		OrderObj obj = new OrderObj();
		obj.setDescription(description);
		obj.setProduct(product);
		obj.setPrice(Double.parseDouble(productPrice));
		obj.setSubproducts(objSubs);
		p.add(obj);
		System.out.println(description);
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
		ArrayList<OrderObj> p = (ArrayList<OrderObj>) session.getAttribute("products");
		String obj = request.getParameter("name");
		System.out.println(obj);
		OrderObj order = null;
		for (OrderObj orderObj : p) {
			if (orderObj.toString().equals(obj)) {
				order = orderObj;
				break;
			}
		}
		double price = (double) session.getAttribute("totalPrice");
		System.out.println(price);
		price = price - order.getPrice();
		System.out.println(price);
		session.setAttribute("totalPrice", price);
		if (p.remove(order)) {
			System.out.println("item was removed");
		}
		session.setAttribute("productsNumber", p.size());
		if((int)session.getAttribute("productsNumber") == 0){
			session.setAttribute("totalPrice", 0.0);
		}

	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
