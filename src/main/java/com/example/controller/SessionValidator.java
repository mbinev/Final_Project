package com.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.model.OrderObj;

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
		return true;
	}
}
