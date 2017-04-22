package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

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
	
	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public String getAddresses(HttpSession session) throws SQLException {
		User user = (User) session.getAttribute("user");
		ArrayList<Address> list = AddressDAO.getInstance().getUserAddresses(user.getUserId());
		session.setAttribute("addresses", list);
		return "addresses";
	}
	

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String showProfile() {
		return "profile";
		
	}
	
}
