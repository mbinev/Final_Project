package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.model.Order;
import com.example.model.Product;

public class OrderDAO implements IDao{

	private static OrderDAO instance;
	
	private OrderDAO() {
	}
	
	public static synchronized OrderDAO getInstance(){
		if(instance == null){
			instance = new OrderDAO();
		}
		return instance;
	}
	
	public synchronized void addOrder(Order	o) throws SQLException {
		PreparedStatement st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
		st.setLong(2, o.getUserId());
		st.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
		st.execute();
		ResultSet rs = st.getGeneratedKeys();
		rs.next();
		o.setOrderId(rs.getLong(1));
	}
	
	public ArrayList<Order> getOrder(long userId) throws SQLException{
		PreparedStatement st = DBManager.getInstance().getSelectStatement(
				getTableName(), getColumns(), getPrimaryKeyName());
		st.setLong(1, userId);
		ResultSet rs = st.executeQuery();
		ArrayList<Order> orders = new ArrayList<>();
		Order order = null;
		while(rs.next()){
			order = new Order(userId,
					rs.getTimestamp("time").toLocalDateTime());
			order.setOrderId(rs.getLong("order_id"));
			orders.add(order);
		}
		return orders;
	}
	
	public void makeOrder(Order order, ArrayList<Product> products) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		try {
			con.setAutoCommit(false);
			System.out.println("ok");
			String sql1 = "INSERT INTO orders (user_id, date) VALUES (?, ?)";
			st1 = con.prepareStatement(sql1);
			st1.setLong(1, order.getUserId());
			st1.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			st1.execute();
			ResultSet rs = st1.getGeneratedKeys();
			rs.next();
			order.setOrderId(rs.getLong(1));
			String sql2 = "INSERT INTO orders_has_products (order_id, product_id, owner_id) VALUES (?, ?, ?)";
			st2 = con.prepareStatement(sql2);
			for (Product product : products) {
				st2.setLong(1, order.getOrderId());
				st2.setLong(2, product.getProductId());
				st2.setLong(3, product.getOwnerId());
				st2.executeUpdate();
			}
			con.commit();
		} catch (SQLException e) {
			if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                System.out.println(e.getMessage());
	                con.rollback();
	            } catch(SQLException excep) {
	               System.out.println("Problem with rollback");
	            }
	        }
		} finally {
			if (st1 != null) {
	            st1.close();
	        }
	        if (st2 != null) {
	            st2.close();
	        }
	        con.setAutoCommit(true);
		}
	}

	@Override
	public String getTableName() {
		return "orders";
	}
	
	@Override
	public String[] getColumns() {
		return new String[] {
				"order_id", "user_id", "date"};
	}

	@Override
	public String getPrimaryKeyName() {
		return "order_id";
	}
	

}
