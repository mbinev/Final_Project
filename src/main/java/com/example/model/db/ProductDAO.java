package com.example.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.model.Product;

public class ProductDAO implements IDao{
	
private static ProductDAO instance;
	
	private static HashMap<String, ArrayList<Product>> products; //category -> products

	private ProductDAO() {
		products = new HashMap<String, ArrayList<Product>>();
	}
	
	public static synchronized ProductDAO getInstance(){
		if(instance == null){
			instance = new ProductDAO();
		}
		return instance;
	}
	
	public synchronized void addProduct(Product	p) throws SQLException {
		PreparedStatement st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
		st.setString(2, p.getName());
		st.setDouble(3, p.getPrice());
		st.setString(4, p.getCategory());
		st.execute();
		ResultSet rs = st.getGeneratedKeys();
		rs.next();
		p.setProductId(rs.getLong(1));
	}
	
	public HashMap<String, ArrayList<Product>> getAllProducts() throws SQLException{
		if(products.isEmpty()){
			Connection con = DBManager.getInstance().getConnection();
			PreparedStatement st1 = null;
			PreparedStatement st2 = null;
			PreparedStatement st3 = null;
			
			try {
				String sql1 = "SELECT DISTINCT category FROM products";
				st1 = con.prepareStatement(sql1);
				ResultSet rs1 = st1.executeQuery();
				while(rs1.next()){
					products.put(rs1.getString("category"), new ArrayList<Product>());
				}
				String sql2 = "SELECT product_id, name, price, category FROM products";
				st2 = con.prepareStatement(sql2);
				ResultSet rs2 = st2.executeQuery();
				while(rs2.next()){
					Product p = new Product(rs2.getString("name"), rs2.getDouble("price"), rs2.getString("category"));
					p.setProductId(rs2.getLong("product_id"));
					products.get(p.getCategory()).add(p);
					String sql3 = "SELECT name FROM products WHERE product_id IN (SELECT sub_product_id FROM product_has_products WHERE product_id = "+p.getProductId()+" AND order_id IS NULL)";
					st3 = con.prepareStatement(sql3);
					ResultSet rs3 = st3.executeQuery();
					while(rs3.next()){
						p.getSubproducts().add(rs3.getString("name"));
					}
				}
			} catch (SQLException e) {
				System.err.print("Problem with extracting products");
				System.out.println(e.getMessage());
				products = null;
			} finally {
				if (st1 != null) {
		            st1.close();
		        }
		        if (st2 != null) {
		            st2.close();
		        }
			}
		}
		return products;
	}

	@Override
	public String getTableName() {
		return "products";
	}
	
	@Override
	public String[] getColumns() {
		return new String[] {
				"product_id", "name", "price", "category"};
	}

	@Override
	public String getPrimaryKeyName() {
		return "product_id";
	}
}
