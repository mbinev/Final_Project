package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.model.Address;
import com.example.model.User;

public class AddressDAO implements IDao {

	private static AddressDAO instance;

	private AddressDAO() {
	}

	public static synchronized AddressDAO getInstance() {
		if (instance == null) {
			instance = new AddressDAO();
		}
		return instance;
	}

	public void addNewAddress(User user, Address address) throws SQLException {
		PreparedStatement st = DBManager.getInstance().getInsertStatement(getTableName(), getColumns());
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
	}

	public ArrayList<Address> getUserAddresses(long userID) throws SQLException {
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
		System.out.println(newColumns);
		String sql = String.format("SELECT address_id, %s FROM %s WHERE user_id=?", newColumns, getTableName());
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setLong(1, userID);
		ResultSet rs = st.executeQuery();
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
	}
	
	public ArrayList<Address> shopAddresses() throws SQLException {
		ArrayList<Address> list = new ArrayList<>();
		String sql = "SELECT address_id, name, street, address_number, post_code, city, mobile_number FROM addresses WHERE user_id IS NULL";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Address address = new Address(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
					rs.getString("post_code"), rs.getString("mobile_number"));
			address.setCity(rs.getString("city"));
			address.setAddressId(rs.getLong("address_id"));
			list.add(address);
		}
		return list;
	}

	public void deleteAddress(long id) throws SQLException {
		String sql = String.format("DELETE FROM %s WHERE %s = ?", getTableName(), getPrimaryKeyName());
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setLong(1, id);
		st.executeUpdate();
	}

	public void updateAddress(Address address) throws SQLException {
		String sql = String.format(
				"UPDATE addresses SET name='%s'," + "street='%s'," + "address_number='%s'," + "post_code='%s',"
						+ "mobile_number='%s'," + "bell='%s'," + "floor=%d," + "building_number=%d,"
						+ "apartment_number=%d," + "entrace='%s' " + "WHERE address_id=%d",
				address.getName(), address.getStreet(), address.getaddressNumber(), address.getPostcode(),
				address.getPhone(), address.getBell(), address.getFloor(), address.getBuildingNumber(),
				address.getApartmentNumber(), address.getEntrance(), address.getAddressId());
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.executeUpdate();
	}

	public Address getAddressById(long id) throws SQLException {
		String sql = "SELECT name, street, address_number, post_code,"
				+ "mobile_number, bell, floor, building_number, apartment_number, entrace, "
				+ "user_id FROM addresses WHERE address_id = ?";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		Address address = null;
		while(rs.next()) {
			address = new Address(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
					rs.getString("post_code"), rs.getString("mobile_number"));
			address.setFloor(rs.getInt("floor"));
			address.setBell(rs.getString("bell"));
			address.setBuildingNumber(rs.getInt("building_number"));
			address.setApartmentNumber(rs.getInt("apartment_number"));
			address.setEntrance(rs.getString("entrace"));
			address.setAddressId(id);
		}
		return address;
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
