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
	
	@RequestMapping(value = "/addresses", method = RequestMethod.GET)
	public String showAddresses(HttpServletRequest request, HttpSession session) {
		if(request.getParameter("id") != null){
			int addressId = Integer.parseInt(request.getParameter("id"));
			long id = addressId;
			session.setAttribute("addressID", id);
		}
		return "addresses";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String showAddres(HttpServletRequest request, HttpSession session) throws SQLException {
		if(request.getParameter("id") != null){
			int addressId = Integer.parseInt(request.getParameter("id"));
			long id = addressId;
			System.out.println(id);
			Address address = AddressDAO.getInstance().getAddressById(id);
			System.out.println("address in update " + address);
			session.setAttribute("address", address);
			session.setAttribute("addressID", id);
		}
		return "addresses";
	}
	
	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public String updateAddres(HttpServletRequest req, HttpSession session) throws SQLException {
		String name = req.getParameter("name"); 
		String street = req.getParameter("street");
		String addressNumber = req.getParameter("address number");
		String postcode = req.getParameter("postcode");
		String phone = req.getParameter("phone");
		int floor = Integer.parseInt(req.getParameter("floor"));
		
		// not req
		String bell = req.getParameter("bell").equals("") ? "" : req.getParameter("bell"); 
		int buildingNumber = req.getParameter("building number").equals("") ? 
				-1 : Integer.parseInt(req.getParameter("building number")); 
		int apartamentNumber = req.getParameter("apartament number").equals("") ? -1 :
				Integer.parseInt(req.getParameter("apartament number"));
		String entrance = req.getParameter("entrance").equals("") ? "" : req.getParameter("entrance"); 
		
		Address address = new Address(name, street, addressNumber, postcode, phone, floor);
		address.setBell(bell);
		address.setBuildingNumber(buildingNumber);
		address.setApartmentNumber(apartamentNumber);
		address.setEntrance(entrance);
		address.setAddressId((long)session.getAttribute("addressID"));
		
		AddressDAO.getInstance().updateAddress(address);
		return "profile";
	}
	

	@RequestMapping(value="/profile", method=RequestMethod.GET)
		public String showProfile(HttpSession session) throws SQLException {
			User user = (User) session.getAttribute("user");
			ArrayList<Address> list = AddressDAO.getInstance().getUserAddresses(user.getUserId());
			session.setAttribute("addresses", list);
			return "profile";
		}
		
	}
	

