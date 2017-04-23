package com.example.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.model.Address;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.db.AddressDAO;
import com.example.model.db.UserDAO;
import com.example.validation.EmailSender;
import com.example.validation.Form;




@Controller
@SessionAttributes("user")
public class UserController {

	@RequestMapping(value="/register",method = RequestMethod.POST)
	public String registerUser(Model model, HttpServletRequest req) {
		try {
			String firstName = req.getParameter("first name");
			String lastName = req.getParameter("last name");
			String password = req.getParameter("password");
			String confirmPassword = req.getParameter("confirm password");
			String email = req.getParameter("email");
			Form form = new Form();
			model.addAttribute("form", form);
			
			if (UserDAO.getInstance().findByEmail(email) == null) {
				
				boolean validEmail = validateEmail(email);
				if(!validEmail) {
					form.addError(form.new Error("email", "Invalid email"));				
				}
				
				boolean validPassword = validatePassword(password);
				if(!validPassword){
					form.addError(form.new Error("password", "Incorrect password"));
				}
				
				if(!password.equals(confirmPassword)) {
					form.addError(form.new Error("confirm password", "Confirm password error"));
				}
				
				if (validEmail && validPassword && password.equals(confirmPassword)) {
					User u = new User(firstName, lastName, email, password);
					EmailSender.sendValidationEmail("dominos.pizza.itt@gmail.com",
							"Dominos pizza verification", "Please click on the following link: ");
					//TODO add link
					UserDAO.unconfirmedUsers.put(email, u);
					return "confirm-register";
				} else {
					//TODO stay on same page, keep the right data and ask the user to fill the form again
					return "register";
				}
			} else {
				form.addError(form.new Error("email", "This email is already registered"));
				return "register";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error500";
		}
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpSession session){
		try{
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			Form form = new Form();
			User user = null;
			if(UserDAO.unconfirmedUsers.containsKey(email)) {
				user = UserDAO.unconfirmedUsers.get(email);
				LocalDateTime expireTime = user.getRegistrationTime().plusHours(1);
				LocalDateTime now = LocalDateTime.now();
				if(now.isBefore(expireTime)) {
					user.setIsVerified();
					UserDAO.getInstance().addUser(user);
					UserDAO.unconfirmedUsers.remove(email);
				} else {
					user.setRegistrationTime();
					EmailSender.sendValidationEmail("dominos.pizza.itt@gmail.com",
							"Dominos pizza verification", "Please click on the following link: ");
					//TODO add link
					return "confirm-register";
				}
			}
			
			user = UserDAO.getInstance().findByEmail(email);
			boolean validLogin = UserDAO.getInstance().validLogin(user, email, password);	
			System.out.println(validLogin);
			
			if(validLogin) {
				session.setAttribute("user", user);
				session.setAttribute("logged", true);
				session.setMaxInactiveInterval(15*60); // 15 minutes
				return "index";
			} else {
				//stay on the same page, keep the correct data
				form.addError(form.new Error("email", "Invalid email or password"));
				session.setAttribute("form", form);
				return "login";
			}
		} catch (Exception e) {
				System.out.println(e.getMessage());
				e.getStackTrace();
				return "error500";
		}
	}
	
	@RequestMapping(value="/facebookLogin",method = RequestMethod.POST)
	public String loginWithFacebook(HttpServletRequest req) {
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String email = req.getParameter("email");
		//TODO redirect user to a page, where he can enter password, and than add him to the database
		return "";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
		
	}
	
	@RequestMapping(value="/addAddress", method=RequestMethod.POST)
	public String addAddress(HttpServletRequest req, HttpSession session) throws SQLException {
		//req
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
		
		//TODO validate and add city
		
		//add to data base
		User user = (User) session.getAttribute("user");
		long userId = user.getUserId();
		Address address = new Address(name, street, addressNumber, postcode, phone, floor);
		address.setBell(bell);
		address.setBuildingNumber(buildingNumber);
		address.setApartmentNumber(apartamentNumber);
		address.setEntrance(entrance);
		AddressDAO.getInstance().addNewAddress(user, address);
		return "addresses";
	}
	
//	@RequestMapping(value="/getAddresses", method=RequestMethod.GET)
//	public String getAddresses(HttpSession session) throws SQLException {
//		User user = (User) session.getAttribute("user");
//		ArrayList<Address> list = AddressDAO.getInstance().getUserAddresses(user.getUserId());
//		session.setAttribute("addresses", list);
//		return "addresses";
//	}
	
	@RequestMapping(value="/deleteAddress", method=RequestMethod.POST)
	public String deleteAddress(HttpSession session, HttpServletRequest request) throws SQLException {
		int addressId = Integer.parseInt(request.getParameter("id"));
		long id = addressId;
		AddressDAO.getInstance().deleteAddress(id);
		return "addresses";
	}
	

	private boolean validateEmail(String email) {
		Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
				"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		boolean isTaken = false;
		try {
			isTaken = UserDAO.getInstance().isEmailFree(email);
		} catch (SQLException e) {
			System.out.println("Problem validating email.");
		}
		return matcher.find() && isTaken;
	}

	private boolean validatePassword(String password) {
		Pattern VALID_PASSWORD_REGEX = Pattern
				.compile("(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}");
		// at least one digit,at least one upper case letter, at least 8
		// characters, no whitespaces
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}
	
//	@RequestMapping(value="form", method=RequestMethod.POST)
//    public String submitForm(@Valid User user, BindingResult result, Model m, HttpServletRequest req) {
//		System.out.println(req.getParameter("email"));
//        if(result.hasErrors()) {
//            return "index";
//        }
//        if(req.getParameter("password").equals(req.getParameter("confirm password"))) {
//        	 System.out.print(user.getFirstName());
//             return "test";
//        }
//        return "index";
//    }
//	
}
