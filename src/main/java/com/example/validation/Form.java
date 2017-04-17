package com.example.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Form {
	public class Error {
		
		public Error(String formField, String errorMessage) {
			field= formField;
			value = errorMessage;
		}
		
		public String field;
		public String value;
	}
	
	private HashMap<String, List<Form.Error>> _errors;
	
	public Form() {
		_errors = new HashMap<String, List<Form.Error>>();
	}
	
	public void addError(Error error) {
		ArrayList<Form.Error> errors =
				(ArrayList<Form.Error>) _errors.get(error.field);
		if (errors == null) {
			errors = new ArrayList<Form.Error>();
		}
		
		errors.add(error);
		_errors.put(error.field, errors);
	}
	
	public List<Form.Error> getErrors(String field) {
		return _errors.get(field);
	}
}
