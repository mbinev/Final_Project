package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.model.Address;
import com.example.model.ShopAddress;
import com.example.model.User;

public class AddressDAO implements IDao {

	private static AddressDAO instance;
	private static HashMap<Long, ShopAddress> shops; // AddressId -> ShopAddress

	private AddressDAO() {
		shops = new HashMap<Long, ShopAddress>();
	}

	public static synchronized AddressDAO getInstance() {
		if (instance == null) {
			instance = new AddressDAO();
		}
		return instance;
	}

	public void addNewAddress(User user, Address address) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
			st.setString(1, address.getName());
			st.setString(2, address.getStreet());
			st.setString(3, address.getaddressNumber());
			st.setString(4, address.getPostcode());
			st.setString(5, address.getPhone());
			st.setString(6, address.getBell());
			st.setInt(7, address.getFloor());
			st.setInt(8, address.getBuildingNumber());
			st.setInt(9, address.getApartmentNumber());
			st.setString(10, address.getEntrance());
			st.setLong(11, user.getUserId());
			st.execute();
			user.getAddresses().add(address);
			rs = st.getGeneratedKeys();
			rs.next();
			address.setAddressId(rs.getLong(1));
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}

	public ArrayList<Address> getUserAddresses(long userID) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			ArrayList<Address> list = new ArrayList<>();
			String[] columns = getColumns();
			StringBuilder appendColumns = new StringBuilder();
			for (int i = 0; i < columns.length; i++) {
				if (i == columns.length - 1) {
					appendColumns.append(columns[i] + " ");
					break;
				}
				appendColumns.append(columns[i] + ", ");
			}
			String newColumns = appendColumns.toString();
			String sql = String.format("SELECT address_id, %s FROM %s WHERE user_id=?", newColumns, getTableName());
			st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.setLong(1, userID);
			rs = st.executeQuery();
			while (rs.next()) {
				Address address = new Address(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
						rs.getString("post_code"), rs.getString("mobile_number"));
				address.setFloor(rs.getInt("floor"));
				address.setBell(rs.getString("bell"));
				address.setBuildingNumber(rs.getInt("building_number"));
				address.setApartmentNumber(rs.getInt("apartment_number"));
				address.setEntrance(rs.getString("entrace"));
				address.setAddressId(rs.getLong("address_id"));
				list.add(address);
			}
			return list;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}
	
	public HashMap<Long, ShopAddress> shopAddresses() throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		PreparedStatement st2 = null;
		ResultSet rs2 = null;
		try{
		if(shops.isEmpty()){			
			String sql = "SELECT address_id, name, street, address_number, post_code, city, mobile_number FROM addresses WHERE user_id IS NULL";
			st = DBManager.getInstance().getConnection().prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()) {
				ShopAddress shop = new ShopAddress(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
						rs.getString("post_code"), rs.getString("mobile_number"));
				shop.setCity(rs.getString("city"));
				shop.setAddressId(rs.getLong("address_id"));
				shops.put(shop.getAddressId(), shop);
			}
			String sql2 = "SELECT address_id, lat, lng, info FROM shop_has_info";
			st2 = DBManager.getInstance().getConnection().prepareStatement(sql2);
			rs2 = st2.executeQuery();
			while (rs2.next()) {
				ShopAddress shop = shops.get(rs2.getLong("address_id"));
				shop.setLat(rs2.getDouble("lat"));
				shop.setLng(rs2.getDouble("lng"));
				shop.setInfo(rs2.getString("info"));
			}
		}
		return shops;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(st2 != null) {
				st2.close();
			}
			if(rs2 != null) {
				rs2.close();
			}
		}
	}

	public void deleteAddress(long id) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		
		try{
			String sql = String.format("DELETE FROM %s WHERE %s = ?", getTableName(), getPrimaryKeyName());
		    st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.setLong(1, id);
			st.executeUpdate();
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}

	public void updateAddress(Address address) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		
		try{
			String sql = String.format(
					"UPDATE addresses SET name='%s'," + "street='%s'," + "address_number='%s'," + "post_code='%s',"
							+ "mobile_number='%s'," + "bell='%s'," + "floor=%d," + "building_number=%d,"
							+ "apartment_number=%d," + "entrace='%s' " + "WHERE address_id=%d",
					address.getName(), address.getStreet(), address.getaddressNumber(), address.getPostcode(),
					address.getPhone(), address.getBell(), address.getFloor(), address.getBuildingNumber(),
					address.getApartmentNumber(), address.getEntrance(), address.getAddressId());
			st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.executeUpdate();
		} finally {
			if(st != null) {
				st.close();
			}
		}
	}

	public Address getAddressById(long id) throws SQLException, ClassNotFoundException {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT name, street, address_number, post_code,"
					+ "mobile_number, bell, floor, building_number, apartment_number, entrace, "
					+ "user_id FROM addresses WHERE address_id = ?";
			st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.setLong(1, id);
			rs = st.executeQuery();
			Address address = null;
			rs.next();
		    address = new Address(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
					rs.getString("post_code"), rs.getString("mobile_number"));
			address.setFloor(rs.getInt("floor"));
			address.setBell(rs.getString("bell"));
			address.setBuildingNumber(rs.getInt("building_number"));
			address.setApartmentNumber(rs.getInt("apartment_number"));
			address.setEntrance(rs.getString("entrace"));
			address.setAddressId(id);
			return address;
		} finally {
			if(st != null) {
				st.close();
			}
			if(rs != null) {
				rs.close();
			}
		}
	}

	@Override
	public String getTableName() {
		return "addresses";
	}

	@Override
	public String[] getColumns() {
		return new String[] { "name", "street", "address_number", "post_code", "mobile_number", "bell", "floor",
				"building_number", "apartment_number", "entrace", "user_id" };
	}

	@Override
	public String getPrimaryKeyName() {
		return "address_id";
	}

}
