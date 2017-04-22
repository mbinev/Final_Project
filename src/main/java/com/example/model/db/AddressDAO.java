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
		// String sql = "SELECT ? FROM ? WHERE user_id=?;";
		String sql = "SELECT address_id, name, street, address_number, post_code, mobile_number, bell, floor, building_number, apartment_number, entrace, user_id FROM addresses WHERE user_id=3;";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		// StringBuilder appendColumns = new StringBuilder();
		// String [] columns = getColumns();
		// for (int i = 0; i < columns.length; i++) {
		// if(i == columns.length - 1) {
		// appendColumns.append(columns[i]);
		// break;
		// }
		// appendColumns.append(columns[i] +", ");
		// }
		// String newColumns = appendColumns.toString();
		// System.out.println(newColumns);
		// st.setString(1, "name, street, address_number, post_code,
		// mobile_number, bell, floor, building_number, apartment_number,
		// entrace, user_id");
		// st.setString(2, "addresses");
		// st.setLong(3, userID);
		// System.out.println(st.toString());
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Address address = new Address(rs.getString("name"), rs.getString("street"), rs.getString("address_number"),
					rs.getString("post_code"), rs.getString("mobile_number"), rs.getInt("floor"));
			address.setBell(rs.getString("bell"));
			address.setBuildingNumber(rs.getInt("building_number"));
			address.setApartmentNumber(rs.getInt("apartment_number"));
			address.setEntrance(rs.getString("entrace"));
			address.setAddressId(rs.getLong("address_id"));
			list.add(address);
		}
		return list;
	}

	public void deleteAddress(long id) throws SQLException {
		//String sql = "DELETE FROM ? WHERE ?=?";
		String sql = "DELETE FROM addresses WHERE address_id = " + id;
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
//		st.setString(1, getTableName());
//		st.setString(2, getPrimaryKeyName());
//		st.setLong(3, id);
		st.executeUpdate();
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
