package com.example.model.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.example.model.Address;
import com.example.model.User;

public class AddressDAO implements IDao{

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

	@Override
	public String getTableName() {
		return "addresses";
	}
	
	@Override
	public String[] getColumns() {
		return new String[] {
				"name", "street", "address_number", "post_code", "mobile_number", "bell", "floor", "building_number", "apartment_number", "entrace", "user_id"};
		        // to bell - varchar
	}

	@Override
	public String getPrimaryKeyName() {
		return "address_id";
	}

}
