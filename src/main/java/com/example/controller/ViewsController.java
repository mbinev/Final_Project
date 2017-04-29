package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.ShopAddress;
import com.example.model.db.AddressDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	public String loginUser(HttpServletRequest req, HttpServletResponse response) throws SQLException {
		ArrayList<ShopAddress> shops = new ArrayList<>(AddressDAO.getInstance().shopAddresses().values());
		JsonArray markers = new JsonArray();
		for (ShopAddress shop : shops) {
			JsonObject marker = new JsonObject();
			marker.addProperty("lat", shop.getLat());
			marker.addProperty("lng", shop.getLng());
			marker.addProperty("zoom", 16);
			marker.addProperty("name", shop.getName());
			marker.addProperty("info", shop.getInfo());
			markers.add(marker);
		}
		return markers.toString();
	}
}
    
	

