package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Address;
import com.example.model.User;
import com.example.model.db.AddressDAO;

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
	
	@RequestMapping(value = "/error500", method = RequestMethod.GET)
    public String error500View(){
        return "error500";
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
		public String showProfile() throws SQLException {
			return "profile";
		}
		
	}
	

