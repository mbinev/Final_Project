package com.example.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.example.model.OrderObj;
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

	// @RequestMapping(value="/tryRegister", method = RequestMethod.POST)
	// public String checkPersonInfo(@Valid User user, BindingResult
	// bindingResult) {
	// System.out.println(user);
	// System.out.println(bindingResult);
	// if (bindingResult.hasErrors()) {
	// return "index";
	// }
	//
	// return "confirm-register";
	// }

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request) throws AddressException, MessagingException, SQLException {
		Scanner sc = null;
		try {
			sc = new Scanner(request.getInputStream());
		} catch (IOException e) {
			System.out.println("problem with register user " + e.getMessage());
		}
		
		StringBuilder sb = new StringBuilder();
		
		while(sc.hasNextLine()){
			sb.append(sc.nextLine());
		}
		
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(sb.toString()).getAsJsonObject();
		
		JsonObject respJSON = new JsonObject();
		
		String name = obj.get("name").getAsString();
		String familyName = obj.get("familyName").getAsString();
		String email = obj.get("email").getAsString();
		String passwordFirst = obj.get("passwordFirst").getAsString();
		String passwordSecond = obj.get("passwordSecond").getAsString();

		boolean NameisNullOrEmpty = nullOrEmpty(name);
		boolean FamilyIsNullOrEmpty = nullOrEmpty(familyName);
		boolean validEmail = validateEmail(email);
		boolean validPassword = validatePassword(passwordFirst);
		if(NameisNullOrEmpty || FamilyIsNullOrEmpty || !validEmail || !validPassword || !passwordFirst.equals(passwordSecond)){
			respJSON.addProperty("error", true);
			JsonArray errorsArray = new JsonArray();
			if(name.length() < 4){
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "nameError");
				error.addProperty("errorMessege", "Min 4 letters");
				errorsArray.add(error);
				
			}
			if(familyName.length() < 4){
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "familyNameError");
				error.addProperty("errorMessege", "Min 4 letters");
				errorsArray.add(error);
				
			}
			if(!validEmail){
//				errorsArray.add(new JsonPrimitive("priceError"));
				if(!UserDAO.getInstance().isEmailFree(email)){
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "emailError");
					error.addProperty("errorMessege", "Email is already taken");
					errorsArray.add(error);	
				}
				else{
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "emailError");
					error.addProperty("errorMessege", "Invalid Email");
					errorsArray.add(error);
				}
				
			}
			if(!validPassword){
//				errorsArray.add(new JsonPrimitive("brandError"));	
				JsonObject error = new JsonObject();
				error.addProperty("errorPlace", "passwordFirstError");
				error.addProperty("errorMessege", "Invalid Password");
				errorsArray.add(error);
			}
			else{
				if (!passwordFirst.equals(passwordSecond)){
					JsonObject error = new JsonObject();
					error.addProperty("errorPlace", "passwordSecondError");
					error.addProperty("errorMessege", "Confirm password doesnt match password!");
					errorsArray.add(error);
				}
			}
			
			respJSON.add("errors", errorsArray);

			System.out.println(respJSON.toString());
			return respJSON.toString();
		}
		else{
			respJSON.addProperty("error", false);
			User u = new User(name, familyName, email, passwordFirst);
			String code = EmailSender.sendValidationEmail("mbinev2@gmail.com");
			u.setRegistrationCode(code);
			UserDAO.unconfirmedUsers.put(email, u);
			System.out.println("Registered");
		}
		System.out.println(respJSON.toString());
		return respJSON.toString();
		
		
	}
	@ResponseBody
	@RequestMapping(value = "/confirmRegisterWithCode", method = RequestMethod.POST)
	public String confirmRegister(Model model, HttpServletRequest req, HttpSession session, HttpServletResponse response) throws SQLException {
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String code = req.getParameter("code");
		User user = null;

		if (UserDAO.unconfirmedUsers.containsKey(email)) {
			user = UserDAO.unconfirmedUsers.get(email);
			LocalDateTime expireTime = user.getRegistrationTime().plusHours(1);
			LocalDateTime now = LocalDateTime.now();
			if (password.equals(user.getPassword()) && now.isBefore(expireTime) && code.equals(user.getRegistrationCode())) {
				user.setIsVerified();
				UserDAO.getInstance().addUser(user);
				UserDAO.unconfirmedUsers.remove(email);
				prepareLogin(session, response, user);
				return "index";
			} else {
				session.setAttribute("incorrectData", "Incorrect data, please try again.");
				return "confirm-register";
			}
		} else {
			return "error500";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpSession session, HttpServletResponse response)
			throws SQLException, NoSuchAlgorithmException {
		Scanner sc = null;
		try {
			sc = new Scanner(req.getInputStream());
		} catch (IOException e) {
			System.out.println("problem with register user " + e.getMessage());
		}
		
		StringBuilder sb = new StringBuilder();
		
		while(sc.hasNextLine()){
			sb.append(sc.nextLine());
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
			System.out.println(respJSON.toString());
			return respJSON.toString();
		}
	}

	private void prepareLogin(HttpSession session, HttpServletResponse response, User user) throws SQLException {
		session.setAttribute("user", user);
		session.setAttribute("logged", true);
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-control", "no-cache");
	}
	
	private boolean nullOrEmpty(String firstName) {
		return firstName == null  || firstName.isEmpty();
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
		session.invalidate();
		return "index";
	}

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public String addAddress(HttpServletRequest req, HttpSession session) throws SQLException {
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
		long userId = user.getUserId();
		Address address = new Address(name, street, addressNumber, postcode, phone);
		address.setFloor(floor);
		address.setBell(bell);
		address.setBuildingNumber(buildingNumber);
		address.setApartmentNumber(apartamentNumber);
		address.setEntrance(entrance);
		AddressDAO.getInstance().addNewAddress(user, address);
		return "addresses";
	}

	// @RequestMapping(value="/getAddresses", method=RequestMethod.GET)
	// public String getAddresses(HttpSession session) throws SQLException {
	// User user = (User) session.getAttribute("user");
	// ArrayList<Address> list =
	// AddressDAO.getInstance().getUserAddresses(user.getUserId());
	// session.setAttribute("addresses", list);
	// return "addresses";
	// }

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String showAddres(HttpServletRequest request, HttpSession session) throws SQLException {
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
	public String updateAddres(HttpServletRequest req, HttpSession session) throws SQLException {
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
		return "profile";
	}

	@RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
	public String deleteAddress(HttpSession session, HttpServletRequest request) throws SQLException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		long id = addressId;
		AddressDAO.getInstance().deleteAddress(id);
		return "addresses";
	}

	private boolean validateEmail(String email) throws SQLException {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		boolean isTaken = false;
		isTaken = UserDAO.getInstance().isEmailFree(email);

		return matcher.find() && isTaken;
	}

	private boolean validatePassword(String password) {
		Pattern VALID_PASSWORD_REGEX = Pattern.compile("(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}");
		// at least one digit,at least one upper case letter, at least 8
		// characters, no whitespaces
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String order(HttpServletRequest request) throws SQLException {
		HttpSession session = request.getSession();
		ArrayList<OrderObj> products = (ArrayList<OrderObj>) session.getAttribute("products");
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
		if(address == null){
			ArrayList<Address> shopAddresses = AddressDAO.getInstance().shopAddresses();
			for (Address adr : shopAddresses) {
				if(adr.getName().equals(addressName)){
					address = adr;
					break;
				}
			}
		}
		if(address == null){			
			return "error500";
		}
		Order order = new Order(user.getUserId(), LocalDateTime.now());
		order.setAddressId(address.getAddressId());
		order.setProducts(products);
		OrderDAO.getInstance().makeOrder(order, ProductDAO.getInstance().getAllProducts());
		session.setAttribute("productsNumber", 0);
		session.setAttribute("totalPrice", 0.0);
		session.setAttribute("products", new ArrayList<OrderObj>());
		return "order-success";
	}

	// @RequestMapping(value="form", method=RequestMethod.POST)
	// public String submitForm(@Valid User user, BindingResult result, Model m,
	// HttpServletRequest req) {
	// System.out.println(req.getParameter("email"));
	// if(result.hasErrors()) {
	// return "index";
	// }
	// if(req.getParameter("password").equals(req.getParameter("confirm
	// password"))) {
	// System.out.print(user.getFirstName());
	// return "test";
	// }
	// return "index";
	// }
	//
}
