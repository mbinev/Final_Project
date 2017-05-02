package com.example.model.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import com.example.model.Address;
import com.example.model.User;

public class UserDAO implements IDao {

	private static UserDAO instance;
	public static HashMap<String, User> unconfirmedUsers = new HashMap<String, User>();
	
	private UserDAO() {
	}
	
	public static synchronized UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public synchronized void addUser(User u) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
			st.setString(1, u.getFirstName());
			st.setString(2, u.getLastName());
			st.setString(3, u.getEmail());
			st.setString(4, u.getPassword());
			st.setTimestamp(5, Timestamp.valueOf(u.getRegistrationTime()));
			st.setBoolean(6, u.getIsVerified());
			st.execute();
			rs = st.getGeneratedKeys();
			rs.next();
			u.setUserId(rs.getLong(1));
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public synchronized User getUser(long primary) throws SQLException, ClassNotFoundException{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DBManager.getInstance().getSelectStatement(
					getTableName(), getColumns(), getPrimaryKeyName());
			st.setLong(1, primary);
			rs = st.executeQuery();
			User user = null;
			while(rs.next()){
				user = new User(rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getString("password"));
						rs.getTimestamp("register_date").toLocalDateTime();
						rs.getBoolean("is_validated");
				long userId = rs.getLong("user_id");
				user.setUserId(userId);
			}
			return user;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public User findByEmail(String email) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = DBManager.getInstance()
			.getSelectStatement(getTableName(), getColumns(), "email");
	
	        st.setString(1, email);
	        rs = st.executeQuery();
	        User user = null;
	        while(rs.next()){
				user = new User(rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("email"),
						rs.getString("password"));
						rs.getTimestamp("register_date").toLocalDateTime();
						rs.getBoolean("is_validated");
				long userId = rs.getLong("user_id");
				user.setUserId(userId);
			}
			return user;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public void updateAvatarLink(User user, String avatarLink) throws SQLException, ClassNotFoundException {
		long userId = user.getUserId();
		String sql = "UPDATE users SET avatar = ? WHERE user_id = ?";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setString(1, avatarLink);
		st.setLong(2, userId);
		st.executeUpdate();
	}
	
	
	public synchronized boolean isEmailFree(String email) throws SQLException, ClassNotFoundException{
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = String.format("SELECT email FROM %s WHERE email=?", getTableName());
			st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.setString(1, email);
			rs = st.executeQuery();
			if (!rs.next()) {    
			    return true; 
			} 
			return false;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public synchronized boolean validLogin(User user, String email, String password) 
			throws SQLException, NoSuchAlgorithmException {
		
		if (user == null) {
			return false;	
		}
		
		String hashedPassword = User.hashPassword(password);
		return user.getPassword().equals(hashedPassword);
	}
	
	@Override
	public String getTableName() {
		return "users";
	}
	
	@Override
	public String[] getColumns() {
		return new String[] {
				"first_name", "last_name", "email", "password", "register_date", "is_validated"};
	}
	
	@Override
	public String getPrimaryKeyName() {
		return "user_id";
	}

}