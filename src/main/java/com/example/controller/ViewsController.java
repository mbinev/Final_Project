package com.example.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewsController {
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String indexView() {
		return "index";
	}
	
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
		if(request.getSession().getAttribute("logged") == null) {
			return "index";
		}
		return "addresses";
	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
		public String showProfile(HttpServletRequest req) {
		if(req.getSession().getAttribute("logged") == null) {
			return "index";
		}
	    return "profile";
    }

	@RequestMapping(value="register", method=RequestMethod.GET)
	public String showRegister() {
		return "register";
	}
	
}