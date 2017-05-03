package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.model.Order;
import com.example.model.OrderObject;
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
	
	public ArrayList<Order> getOrder(long userId) throws SQLException, ClassNotFoundException{
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			st = DBManager.getInstance().getSelectStatement(
					getTableName(), getColumns(), getPrimaryKeyName());
			st.setLong(1, userId);
			rs = st.executeQuery();
			ArrayList<Order> orders = new ArrayList<>();
			Order order = null;
			while(rs.next()){
				order = new Order(userId,
						rs.getTimestamp("time").toLocalDateTime());
				order.setOrderId(rs.getLong("order_id"));
				orders.add(order);
			}
			return orders;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public void makeOrder(Order order, HashMap<String, HashMap<String, Product>> products) throws SQLException, ClassNotFoundException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement st1 = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		ResultSet rs = null;
		try {
			con.setAutoCommit(false);
			String sql1 = "INSERT INTO orders (user_id, date, address_id) VALUES (?, ?, ?)";
			st1 = con.prepareStatement(sql1);
			st1.setLong(1, order.getUserId());
			st1.setTimestamp(2, Timestamp.valueOf(order.getDate()));
			st1.setLong(3, order.getAddressId());
			st1.execute();
			rs = st1.getGeneratedKeys();
			rs.next();
			order.setOrderId(rs.getLong(1));
			String sql2 = "INSERT INTO orders_has_products (order_id, product_id, quantity) VALUES (?, ?, ?)";
			st2 = con.prepareStatement(sql2);
			ArrayList<Long> generatedKeys = new ArrayList<>();
			for (OrderObject obj : order.getProducts()) {
				st2.setLong(1, order.getOrderId());
				st2.setLong(2, obj.getProduct().getProductId());
				st2.setInt(3, obj.getQuantity());
				st2.executeUpdate();
				ResultSet rs2 = st2.getGeneratedKeys();
				rs2.next();
				generatedKeys.add(rs2.getLong(1));
			}	
			String sql3 = "INSERT INTO orders_has_products (order_id, product_id, owner_id) VALUES (?, ?, ?)";
			st3 = con.prepareStatement(sql3);
			for (OrderObject obj : order.getProducts()) {
				if(!obj.getSubproducts().isEmpty()){					
					for (String subName : obj.getSubproducts()) {
						for (HashMap<String, Product> map : products.values()) {
							if(map.containsKey(subName)){
								Product p = map.get(subName);
								st3.setLong(1, order.getOrderId());
								st3.setLong(2, p.getProductId());
								st3.setLong(3, generatedKeys.get(0));
								st3.executeUpdate();
							}
						}
					}
				}
				generatedKeys.remove(0);
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
	        if (st3 != null) {
	            st3.close();
	        }
	        if (rs != null) {
	            rs.close();
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
				"order_id", "user_id", "date", "address_id"};
	}

	@Override
	public String getPrimaryKeyName() {
		return "order_id";
	}
	

}
