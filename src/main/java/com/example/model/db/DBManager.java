package com.example.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DBManager {

	private static DBManager instance;
	private Connection connection = null;
	
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Problem loading the driver!");
		}
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dominos?user=user&password=user");
		} catch (SQLException e) {
			System.out.println("Unable to connect to Database" + e.getMessage());
		}
		
	}
	
	public static synchronized DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public String getInsertSql(String table, String[] columns) {
		String columnsList = String.join(", ", columns);
		System.out.println(columnsList);
		String questions = String.join(", ",getQuestionMarks(columns));
		return String.format(
				// insert into users (first_name, last_name, email, password) values (?, ?, ?, ?)
				"INSERT INTO %s (%s) VALUES (%s)", table, columnsList, questions);
	}
	
	public PreparedStatement getInsertStatement(String table, String[] columns) 
			throws SQLException{
		String sql = getInsertSql(table, columns);
		PreparedStatement sth = getConnection().prepareStatement(sql);
		
		return sth;
	}
	
	public PreparedStatement getSelectStatement(String table, String[] columns, String primary) 
			throws SQLException{
		ArrayList<String> allColumns = new ArrayList<String>();
		List<String> columnsList = Arrays.asList(columns);
		allColumns.addAll(columnsList);
		allColumns.add("user_id"); // ???
		
		String columnsJoined = String.join(", ", 
				allColumns.toArray(new String[columns.length]));//+1
		String sql = String.format(
				// select col1, col2, col3, col4 from <tableName> where <primaryKeyName> = ?
				"SELECT %s FROM %s WHERE %s = ?", columnsJoined, table, primary);
		PreparedStatement sth = getConnection().prepareStatement(sql);
		
		return sth;
	}
	
	private String[] getQuestionMarks(String[] values) {
		String[] questions = new String[values.length];
		for (int i = 0 ; i < questions.length; i++) {
			if(i == 3 && values[3].equals("password")){				
				questions[i] = "md5(?)";
			}else{
				questions[i] = "(?)";
			}
		}
		
		return questions;
	}
}