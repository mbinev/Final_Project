package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.Address;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.db.AddressDAO;
import com.example.model.db.ProductDAO;

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
		public String showProfile() throws SQLException {
			return "profile";
		}
		
	}
	

