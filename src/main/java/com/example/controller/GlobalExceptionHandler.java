package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SQLException.class)
	public String handleSQLException(HttpServletRequest request, Exception ex){
		logException(request, ex);
		return "error500";
	}
	
	@ExceptionHandler(IOException.class)
	public String handleIOException(HttpServletRequest request, Exception ex){
		logException(request, ex);
		return "error500";
	}
	
	@ExceptionHandler(NoSuchAlgorithmException.class)
	public String handleNoSuchAlgorithmException(HttpServletRequest request, Exception ex){
		logException(request, ex);
		return "error500";
	}
	
	@ExceptionHandler(NullPointerException.class)
	public String handleNullPointerException(HttpServletRequest request, Exception ex){
		logException(request, ex);
		return "error500";
	}
	
	private void logException(HttpServletRequest request, Exception ex) {
		StringBuilder sb = new StringBuilder();
		sb.append("Date: " + LocalDateTime.now());
		sb.append("Exception URL: " + request.getRequestURL().toString());
		sb.append("Exception message: " + ex.getMessage() + '\n');
		String content = sb.toString();
		File file = new File("C:\\Users\\Теди\\Desktop\\ExceptionLoggs\\Loggs.txt");
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file, true);
			byte[] contentInBytes = content.getBytes();

			fos.write(contentInBytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem with logging exception.");
		}
	}
}
