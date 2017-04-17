package com.example.model.db;

public interface IDao {
	/**
	 * 
	 * @return the table name
	 */
	String getTableName();
	/**
	 * 
	 * @return String[] columns without the primary key
	 */
	String[] getColumns();
	
	/**
	 * 
	 * @return the name of the primary key
	 */
	String getPrimaryKeyName();
	
}
