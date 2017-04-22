package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.User;
import com.example.model.db.UserDAO;

import io.undertow.server.session.Session;

@Controller
@SessionAttributes("filename")
@MultipartConfig
public class UploadController {
	
	private String vzemiToqImage;

	private static final String FILE_LOCATION = "C:\\Pictures\\";

	@RequestMapping(value="/image/{fileName}", method=RequestMethod.GET)
	@ResponseBody
	public void prepareForUpload(@PathVariable("fileName") String fileName, HttpServletResponse resp, Model model) throws IOException {
		File file = new File(FILE_LOCATION + vzemiToqImage);
		Files.copy(file.toPath(), resp.getOutputStream());
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)
	public String receiveUpload(@RequestParam("failche") MultipartFile multiPartFile, Model model, HttpSession session) throws IOException, SQLException{
		User user = (User) session.getAttribute("user");
		String email = user.getEmail();
		user = UserDAO.getInstance().findByEmail(email);
		vzemiToqImage = new Random().nextInt(1000000) + multiPartFile.getOriginalFilename();
		File fileOnDisk = new File(FILE_LOCATION + vzemiToqImage);
		Files.copy(multiPartFile.getInputStream(), fileOnDisk.toPath(), StandardCopyOption.REPLACE_EXISTING);
		UserDAO.getInstance().updateAvatarLink(user, FILE_LOCATION + vzemiToqImage);
		user.setAvatarLink(FILE_LOCATION + vzemiToqImage);
		model.addAttribute("filename", multiPartFile.getOriginalFilename());
		return "profile";
	}
}
