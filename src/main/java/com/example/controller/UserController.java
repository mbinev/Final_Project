package com.example.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Address;
import com.example.model.Order;
import com.example.model.OrderObject;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.db.AddressDAO;
import com.example.model.db.OrderDAO;
import com.example.model.db.ProductDAO;
import com.example.model.db.UserDAO;
import com.example.validation.EmailSender;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@SessionAttributes("user")
public class UserController {

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request)
			throws AddressException, MessagingException, SQLException, IOException, ClassNotFoundException {

		Scanner sc = null;
		StringBuilder sb = new StringBuilder();

		try {
			sc = new Scanner(request.getInputStream());

			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
		} finally {
			if (sc != null) {
				sc.close();
			}
		}

		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();

		JsonObject respJSON = new JsonObject();

		String name = obj.get("name").getAsString();
		String familyName = obj.get("familyName").getAsString();
		String email = obj.get("email").getAsString();
		String passwordFirst = obj.get("passwordFirst").getAsString().trim();
		String passwordSecond = obj.get("passwordSecond").getAsString().trim();

		boolean NameisNullOrEmpty = nullOrEmpty(name);
		boolean FamilyIsNullOrEmpty = nullOrEmpty(familyName);
		boolean validEmail = validateEmail(email);
		boolean validPassword = validatePassword(passwordFirst);

		if (NameisNullOrEmpty || FamilyIsNullOrEmpty || !validEmail || !validPassword
				|| !passwordFirst.equals(passwordSecond)) {
			respJSON.addProperty("error", true);
			JsonArray errorsArray = new JsonArray();
			if (name.length() < 4) {
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "nameError");
				error.addProperty("errorMessege", "Min 4 letters");
				errorsArray.add(error);

			}
			if (familyName.length() < 4) {
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "familyNameError");
				error.addProperty("errorMessege", "Min 4 letters");
				errorsArray.add(error);

			}
			if (!validEmail) {
				if (!UserDAO.getInstance().isEmailFree(email)) {
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "emailError");
					error.addProperty("errorMessege", "Email is already taken");
					errorsArray.add(error);
				} else {
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "emailError");
					error.addProperty("errorMessege", "Invalid Email");
					errorsArray.add(error);
				}

			}
			if (!validPassword) {
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "passwordFirstError");
				error.addProperty("errorMessege", "Invalid Password");
				errorsArray.add(error);
			} else {
				if (!passwordFirst.equals(passwordSecond)) {
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "passwordSecondError");
					error.addProperty("errorMessege", "Confirm password doesnt match password!");
					errorsArray.add(error);
				}
			}

			respJSON.add("errors", errorsArray);

			return respJSON.toString();
		} else {
			respJSON.addProperty("error", false);
			User u = new User(name, familyName, email, passwordFirst);
			String code = EmailSender.sendValidationEmail("dominos.pizza.itt@gmail.com");
			u.setRegistrationCode(code);
			UserDAO.unconfirmedUsers.put(email, u);
		}

		return respJSON.toString();

	}

	@RequestMapping(value = "/confirmRegisterWithCode", method = RequestMethod.POST)
	public String confirmRegister(Model model, HttpServletRequest req, HttpSession session,
			HttpServletResponse response) throws SQLException, ClassNotFoundException {
		String password = req.getParameter("password").trim();
		String email = req.getParameter("email");
		String code = req.getParameter("code");
		User user = null;
		String jsp = "";

		if (UserDAO.unconfirmedUsers.containsKey(email)) {
			user = UserDAO.unconfirmedUsers.get(email);
			LocalDateTime expireTime = user.getRegistrationTime().plusHours(1);
			LocalDateTime now = LocalDateTime.now();
			if (password.equals(user.getPassword()) && now.isBefore(expireTime)
					&& code.equals(user.getRegistrationCode())) {
				user.setIsVerified();
				UserDAO.getInstance().addUser(user);
				UserDAO.unconfirmedUsers.remove(email);
				prepareLogin(session, response, user);
				jsp = "index";
			} else {
				session.setAttribute("incorrectData", "Incorrect data, please try again.");
				jsp = "confirm-register";
			}
		} else {
			jsp = "error500";
		}
		return jsp;
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpSession session, HttpServletResponse response)
			throws SQLException, NoSuchAlgorithmException, IOException, ClassNotFoundException {
		String url = req.getHeader("Referer");
		int index = url.lastIndexOf('/') + 1;
		url = url.substring(index);
		session.setAttribute("url", url);
		
		Scanner sc = null;
		StringBuilder sb = new StringBuilder();

		try {
			sc = new Scanner(req.getInputStream());

			while (sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
		} finally {
			if (sc != null) {
				sc.close();
			}
		}

		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();
		JsonObject respJSON = new JsonObject();
		String email = obj.get("email").getAsString();
		String password = obj.get("password").getAsString();
		UserDAO userDAO = UserDAO.getInstance();
		User user = userDAO.findByEmail(email);
		boolean validLogin = userDAO.validLogin(user, email, password);
		if (validLogin) {
			prepareLogin(session, response, user);
			respJSON.addProperty("error", false);
			return respJSON.toString();
		} else {
			respJSON.addProperty("error", true);
			JsonArray errorsArray = new JsonArray();
			JsonObject error = new JsonObject();
			error.addProperty("errorPlace", "status");
			error.addProperty("errorMessege", "Invalid email or password!");
			errorsArray.add(error);
			respJSON.add("errors", errorsArray);
			return respJSON.toString();
		}
	}

	private void prepareLogin(HttpSession session, HttpServletResponse response, User user)
			throws SQLException, ClassNotFoundException {
		user.setAddresses(AddressDAO.getInstance().getUserAddresses(user.getUserId()));
		session.setAttribute("addresses", user.getAddresses());
		session.setAttribute("user", user);
		session.setAttribute("logged", true);
	}

	@RequestMapping(value = "/facebookLogin", method = RequestMethod.POST)
	public String loginWithFacebook(HttpServletRequest req) {
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String email = req.getParameter("email");
		// TODO redirect user to a page, where he can enter password, and than
		// add him to the database
		return "";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session, ServletResponse response) {
		session.setAttribute("logged", false);
		session.invalidate();
		return "index";
	}

	private boolean validateEmail(String email) throws SQLException, ClassNotFoundException {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		boolean isTaken = false;
		isTaken = UserDAO.getInstance().isEmailFree(email);

		return matcher.find() && isTaken;
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String order(HttpServletRequest request) throws SQLException, ClassNotFoundException {
		HttpSession session = request.getSession();
		ArrayList<OrderObject> products = (ArrayList<OrderObject>) session.getAttribute("products");
		User user = (User) session.getAttribute("user");
		ArrayList<Address> addresses = (ArrayList<Address>) session.getAttribute("addresses");
		String addressName = request.getParameter("address");
		Address address = null;
		for (Address adrs : addresses) {
			if (adrs.getName().equals(addressName)) {
				address = adrs;
				break;
			}
		}
		if (address == null) {
			ArrayList<Address> shopAddresses = new ArrayList<Address>(
					AddressDAO.getInstance().shopAddresses().values());
			for (Address adr : shopAddresses) {
				if (adr.getName().equals(addressName)) {
					address = adr;
					break;
				}
			}
		}
		if (address == null) {
			return "error500";
		}
		Order order = new Order(user.getUserId(), LocalDateTime.now());
		order.setAddressId(address.getAddressId());
		order.setProducts(products);
		OrderDAO.getInstance().makeOrder(order, ProductDAO.getInstance().getAllSubproducts());
		session.setAttribute("productsNumber", 0);
		session.setAttribute("totalPrice", 0.0);
		session.setAttribute("products", new ArrayList<OrderObject>());
		return "order-success";
	}
	
	private boolean validatePassword(String password) {
		Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}");
		// at least one digit,at least one upper case letter, at least 8
		// characters, no whitespaces
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}

	private boolean nullOrEmpty(String firstName) {
		return firstName == null || firstName.isEmpty();
	}

}
