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
	
	public synchronized void addUser(User u) throws SQLException {
		PreparedStatement st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
		st.setString(1, u.getFirstName());
		st.setString(2, u.getLastName());
		st.setString(3, u.getEmail());
		st.setString(4, u.getPassword());
		st.setTimestamp(5, Timestamp.valueOf(u.getRegistrationTime()));
		st.setBoolean(6, u.getIsVerified());
		st.execute();
		ResultSet rs = st.getGeneratedKeys();
		rs.next();
		u.setUserId(rs.getLong(1));
		unconfirmedUsers.put(u.getEmail(), u);
	}
	
	public synchronized User getUser(long primary) throws SQLException{
		PreparedStatement st = DBManager.getInstance().getSelectStatement(
				getTableName(), getColumns(), getPrimaryKeyName());
		st.setLong(1, primary);
		ResultSet rs = st.executeQuery();
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
	}
	
	public synchronized User findByEmail(String email) throws SQLException {
		PreparedStatement st = DBManager.getInstance()
		.getSelectStatement(getTableName(), getColumns(), "email");

        st.setString(1, email);
        ResultSet rs = st.executeQuery();
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
	}
	
	public void updateAvatarLink(User user, String avatarLink) throws SQLException {
		long userId = user.getUserId();
		String sql = "UPDATE users SET avatar = ? WHERE user_id = ?";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setString(1, avatarLink);
		st.setLong(2, userId);
		System.out.println(sql);
		st.executeUpdate();
	}
	
	
	public synchronized boolean isEmailFree(String email) throws SQLException{
		//TODO fix that
		String sql = "SELECT email FROM users WHERE email='"+email+"'";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		if (!rs.next()) {    
		    return true; 
		} 
		return false;
	}
	
	public synchronized boolean validLogin(User user, String email, String password) 
			throws SQLException, NoSuchAlgorithmException {
		
		if (user == null) {
			System.out.println("?");
			return false;	
		}
		
		String hashedPassword = User.hashPassword(password);
		System.out.println(user.getPassword());
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