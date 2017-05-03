package com.example.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.model.OrderObject;
import com.example.model.Product;
import com.example.model.db.ProductDAO;

@Controller
public class ProductsController {

	private static final String DEFAULT_CRUST = "Hand Tossed";
	private static final String DEFAULT_SIZE = "Medium (6 Slices)";

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String products(Model model) throws SQLException, ClassNotFoundException {
		ArrayList<Product> products = new ArrayList<Product>();
		HashMap<String, HashMap<String, Product>> items = ProductDAO.getInstance().getAllProducts();
		for (Entry<String, HashMap<String, Product>> entry : items.entrySet()) {
			products.addAll(entry.getValue().values());
		}
		Collections.sort(products);	
		model.addAttribute("products", products);
		model.addAttribute("categories", new ArrayList<>(items.keySet()));
		return "products";
	}

	@RequestMapping(value = "/singleProduct", method = RequestMethod.GET)
	public String products(HttpServletRequest request, Model model) throws SQLException, ClassNotFoundException {
		String productName = (String) request.getParameter("product");
		String category = (String) request.getParameter("category");
		if(productName == null || category == null){
			return "error500";
		}
		HashMap<String, HashMap<String, Product>> products = ProductDAO.getInstance().getAllSubproducts();
		Product product = ProductDAO.getInstance().getAllProducts().get(category).get(productName);
		for (Entry<String, HashMap<String, Product>> entry : products.entrySet()) {
			ArrayList<Product> subproducts = new ArrayList<Product>(entry.getValue().values());
			Collections.sort(subproducts);
			request.getSession().setAttribute(entry.getKey(), subproducts);
		}
		request.getSession().setAttribute("product", product);
		return "single-post";
	}

	@RequestMapping(value = "/buy", method = {RequestMethod.POST, RequestMethod.GET})
    public String products(HttpServletRequest request, HttpSession session) {
        String[] pro = request.getParameterValues("subproduct");
        String productPrice = request.getParameter("productPrice");
        Product product = (Product) session.getAttribute("product");
        ArrayList<String> subproducts = new ArrayList<>();
        if(pro == null && productPrice == null){
            pro = (String[]) request.getAttribute("subproducts");
            productPrice = (String) request.getAttribute("productPrice");
            subproducts.add((String) request.getAttribute("size"));
            subproducts.add((String) request.getAttribute("crust"));
        }else{         
            subproducts.add(request.getParameter("size"));
            subproducts.add(request.getParameter("crust"));
        }
        if (pro != null) {
            for (String string : pro) {
                subproducts.add(string);
            }
        }
       
        ArrayList<String> objSubs = new ArrayList<>();
        ArrayList<OrderObject> p = (ArrayList<OrderObject>) session.getAttribute("products");
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
        OrderObject obj = new OrderObject(product, description, Double.parseDouble(productPrice), objSubs, 1);
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
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteObj(HttpSession session, HttpServletRequest request) {
		ArrayList<OrderObject> products = (ArrayList<OrderObject>) session.getAttribute("products");
		String obj = request.getParameter("name");
		OrderObject order = null;
		for (OrderObject orderObj : products) {
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
	@ResponseStatus(value = HttpStatus.OK)
	public void changeAttributes(HttpSession session, HttpServletRequest request) throws Exception {
		ArrayList<OrderObject> products = (ArrayList<OrderObject>) session.getAttribute("products");
		String obj = request.getParameter("name");
		int numbers = Integer.parseInt(request.getParameter("numbers"));
		if(numbers < 1 || request.getParameter("numbers").isEmpty() || request.getParameter("numbers") == null){
			numbers = 1;
		}
		OrderObject order = null;
		for (OrderObject orderObj : products) {
			if (orderObj.toString().equals(obj)) {
				order = orderObj;
				break;
			}
		}
		order.setQuantity(numbers);
		String totalPrice = request.getParameter("totalPrice");
		String productCount = request.getParameter("productCount");
		double price = Double.parseDouble(totalPrice);
		int count = Integer.parseInt(productCount);
		session.setAttribute("totalPrice", price);
		session.setAttribute("productsNumber", count);
	}
	
	@RequestMapping(value = "/directBuy", method = RequestMethod.GET)
    public String directBuy(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ClassNotFoundException {
        String productName = (String) request.getParameter("product");
        String category = (String) request.getParameter("category");
        Product product = ProductDAO.getInstance().getAllProducts().get(category).get(productName);
        Double productPrice = product.getPrice();
        if(category.equals("Pizzaz")){         
            request.setAttribute("size", DEFAULT_SIZE);
            request.setAttribute("crust", DEFAULT_CRUST);
        }
        if (!product.getSubproducts().isEmpty()) {
        	for(Entry<String, HashMap<String, Product>> e : ProductDAO.getInstance().getAllSubproducts().entrySet()) {
        		for(String p : product.getSubproducts()) {
        			if(e.getValue().containsKey(p)) {
        				productPrice += e.getValue().get(p).getPrice();
        			}
        		}
        	}
        }
        String[] strArr = product.getSubproducts().toArray(new String[0]); 
        request.setAttribute("subproducts", strArr);
        request.setAttribute("productPrice", String.valueOf(productPrice));
        request.getSession().setAttribute("product", product);
        return "forward:/buy";
    }
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
