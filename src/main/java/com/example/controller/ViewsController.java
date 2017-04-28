package com.example.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Address;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.db.AddressDAO;
import com.example.model.db.ProductDAO;
import com.example.model.db.UserDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class ViewsController {
	
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cartView(){
        return "cart";
    }
	
	@RequestMapping(value = "/contact-us", method = RequestMethod.GET)
    public String contactUsView(){
        return "contact-us";
    }
	
	@RequestMapping(value = "/about-us", method = RequestMethod.GET)
    public String aboutUsView(){
        return "about-us";
    }
	
	@RequestMapping(value = "/order-success", method = RequestMethod.GET)
    public String orderSuccessUsView(){
        return "order-success";
    }
	
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
    public String error500View(){
        return "error500";
    }
	
	@RequestMapping(value = "/confirmRegisterWithCode", method = RequestMethod.GET)
    public String confirmRegisterWithCodeView(){
        return "confirm-register-with-code";
    }
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) throws SQLException{
		List<String> categories = null;
		List<Product> products = new ArrayList<>();
		categories = new ArrayList<String>(ProductDAO.getInstance().getAllProducts().keySet());
		for (String category : categories) {
			products.addAll(ProductDAO.getInstance().getAllProducts().get(category));
		}
		model.addAttribute("products", products);
		model.addAttribute("categories", categories);
        return "test";
    }
	
	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public String showAddresses(HttpServletRequest request, HttpSession session) {
		if(request.getParameter("id") != null){
			int addressId = Integer.parseInt(request.getParameter("id"));
			long id = addressId;
			session.setAttribute("addressID", id);
		}
		return "addresses";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
		public String showProfile() {
			return "profile";
		}
		
	@RequestMapping(value="register", method=RequestMethod.GET)
	public String showRegister() {
		return "register";
	}
	
	@ResponseBody
	@RequestMapping(value = "/markers", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpServletResponse response) {
		JsonObject marker1 = new JsonObject();
		JsonObject marker2 = new JsonObject();
		JsonObject marker3 = new JsonObject();
		JsonArray markers = new JsonArray();
		marker1.addProperty("lat", 42.6996424670517);
		marker1.addProperty("lng", 23.31228017807007);
		marker1.addProperty("zoom", 16);
		marker1.addProperty("name","Opulchenska");
		marker1.addProperty("info","<h4>Pizza</h4> bul. Todor Alexandrov </br>1303 </br>Sofia </br>Bulgaria");
		markers.add(marker1);
		marker2.addProperty("lat", 42.692671891757854);
		marker2.addProperty("lng", 23.310391902923584);
		marker2.addProperty("zoom", 16);
		marker2.addProperty("name","Russian Monument");
		marker2.addProperty("info","<h4>Pizza</h4> Russian Monument </br>1606 </br>Sofia </br>Bulgaria");
		markers.add(marker2);
		marker3.addProperty("lat", 42.70131402715675);
		marker3.addProperty("lng", 23.322772979736328);
		marker3.addProperty("zoom", 16);
		marker3.addProperty("name","Luvov most");
		marker3.addProperty("info","<h4>Pizza</h4> bul. Knyaginya Maria Luiza 45 </br>1202 </br>Sofia </br>Bulgaria");
		markers.add(marker3);
		System.out.println(markers.toString());
		return markers.toString();
	}
}
    
	

