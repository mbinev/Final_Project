package com.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.model.Address;
import com.example.model.OrderObj;
import com.example.model.User;
import com.example.model.db.AddressDAO;

public class SessionValidator extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("products") == null) {
			ArrayList<OrderObj> products = new ArrayList<>();
			session.setAttribute("products", products);
			session.setAttribute("totalPrice", 0.0);
			session.setAttribute("productsNumber", 0);
			session.setMaxInactiveInterval(15 * 60);
		}
		if(session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			ArrayList<Address> list = AddressDAO.getInstance().getUserAddresses(user.getUserId());
			session.setAttribute("addresses", list);
		}
		return true;
	}
}
