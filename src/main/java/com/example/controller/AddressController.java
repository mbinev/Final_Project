package com.example.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Address;
import com.example.model.ShopAddress;
import com.example.model.User;
import com.example.model.db.AddressDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class AddressController {

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public String addAddress(HttpServletRequest req, HttpSession session) throws SQLException, ClassNotFoundException {
		// req
		String name = req.getParameter("name");
		String street = req.getParameter("street");
		String addressNumber = req.getParameter("address number");
		String postcode = req.getParameter("postcode");
		String phone = req.getParameter("phone");
		int floor = Integer.parseInt(req.getParameter("floor"));

		// not req
		String bell = req.getParameter("bell").equals("") ? "" : req.getParameter("bell");
		int buildingNumber = req.getParameter("building number").equals("") ? -1
				: Integer.parseInt(req.getParameter("building number"));
		int apartamentNumber = req.getParameter("apartament number").equals("") ? -1
				: Integer.parseInt(req.getParameter("apartament number"));
		String entrance = req.getParameter("entrance").equals("") ? "" : req.getParameter("entrance");

		// TODO validate and add city

		// add to data base
		User user = (User) session.getAttribute("user");
		Address address = new Address(name, street, addressNumber, postcode, phone);
		address.setFloor(floor);
		address.setBell(bell);
		address.setBuildingNumber(buildingNumber);
		address.setApartmentNumber(apartamentNumber);
		address.setEntrance(entrance);
		AddressDAO.getInstance().addNewAddress(user, address);
		session.setAttribute("addresses", user.getAddresses());
		return "addresses";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String showAddres(HttpServletRequest request, HttpSession session)
			throws SQLException, ClassNotFoundException {
		if (request.getParameter("id") != null) {
			int addressId = Integer.parseInt(request.getParameter("id"));
			long id = addressId;
			Address address = AddressDAO.getInstance().getAddressById(id);
			session.setAttribute("address", address);
			session.setAttribute("addressID", id);
		}
		return "addresses";
	}

	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public String updateAddres(HttpServletRequest req, HttpSession session)
			throws SQLException, ClassNotFoundException {
		String name = req.getParameter("name");
		String street = req.getParameter("street");
		String addressNumber = req.getParameter("address number");
		String postcode = req.getParameter("postcode");
		String phone = req.getParameter("phone");
		int floor = Integer.parseInt(req.getParameter("floor"));

		// not req
		String bell = req.getParameter("bell").equals("") ? "" : req.getParameter("bell");
		int buildingNumber = req.getParameter("building number").equals("") ? -1
				: Integer.parseInt(req.getParameter("building number"));
		int apartamentNumber = req.getParameter("apartament number").equals("") ? -1
				: Integer.parseInt(req.getParameter("apartament number"));
		String entrance = req.getParameter("entrance").equals("") ? "" : req.getParameter("entrance");

		Address address = new Address(name, street, addressNumber, postcode, phone);
		address.setFloor(floor);
		address.setBell(bell);
		address.setBuildingNumber(buildingNumber);
		address.setApartmentNumber(apartamentNumber);
		address.setEntrance(entrance);
		address.setAddressId((long) session.getAttribute("addressID"));
		AddressDAO addressDAO = AddressDAO.getInstance();
		addressDAO.updateAddress(address);
		User user = (User) session.getAttribute("user");
		ArrayList<Address> list = addressDAO.getUserAddresses(user.getUserId());
		session.setAttribute("addresses", list);
		session.setAttribute("address", null);
		return "profile";
	}

	@RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
	public String deleteAddress(HttpSession session, HttpServletRequest request)
			throws SQLException, ClassNotFoundException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		long id = addressId;
		AddressDAO.getInstance().deleteAddress(id);
		User user = (User) session.getAttribute("user");
		for (Iterator<Address> it = user.getAddresses().iterator(); it.hasNext();) {
			Address a = (Address) it.next();
			if (a.getAddressId() == id) {
				it.remove();
			}
		}
		session.setAttribute("addresses", user.getAddresses());
		return "addresses";
	}
	
	@ResponseBody
	@RequestMapping(value = "/markers", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpServletResponse response) throws SQLException, ClassNotFoundException {
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
