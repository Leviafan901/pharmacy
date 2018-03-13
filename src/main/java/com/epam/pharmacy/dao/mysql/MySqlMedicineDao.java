package com.epam.pharmacy.dao.mysql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import com.epam.pharmacy.dao.AbstractDao;
import com.epam.pharmacy.domain.Medicine;
import com.epam.pharmacy.exceptions.PersistException;

public class MySqlMedicineDao extends AbstractDao<Medicine, Long> {

	private static final int ID_INDEX = 8;
	private static final int PRICE_INDEX = 7;
	private static final int NEED_PRESCRIPTION_INDEX = 6;
	private static final int PICTURE_INDEX = 5;
	private static final int DOSAGE_MG_INDEX = 4;
	private static final int COUNT_INDEX = 3;
	private static final int COUNT_IN_STORE_INDEX = 2;
	private static final int NAME_INDEX = 1;
	private static final String DELETE_QUERY = "DELETE FROM Medicine WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE Medicine SET name=? count_in_store=? count=? dosage_mg=? picture=? need_prescription=? price=? WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Medicine (name, count_in_store, count, dosage_mg, picture, need_prescription, price) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, count_in_store, count, dosage_mg, picture, need_prescription, price FROM Medicine WHERE id = ?;";
	private static final String SELECT_QUERY = "SELECT id, name, count_in_store, count, dosage_mg, picture, need_prescription, price FROM Medicine";

	public MySqlMedicineDao(Connection connection) throws PersistException {
		super(connection);
	}

	@Override
	public String getSelectQuery() {
		return SELECT_QUERY;
	}

	@Override
	public String getQueryByPK() {
		return SELECT_QUERY_BY_ID;
	}

	@Override
	public String getCreateQuery() {
		return CREATE_QUERY;
	}

	@Override
	public String getUpdateQuery() {
		return UPDATE_QUERY;
	}

	@Override
	public String getDeleteQuery() {
		return DELETE_QUERY;
	}

	@Override
	protected List<Medicine> parseResultSet(ResultSet resultSet) throws PersistException {
		LinkedList<Medicine> result = new LinkedList<Medicine>();
		try {
			while (resultSet.next()) {
				Medicine medicine = new Medicine();
				medicine.setId(resultSet.getLong("id"));
				medicine.setName(resultSet.getString("name"));
				medicine.setCountInStore(resultSet.getLong("last_name"));
				medicine.setCount(resultSet.getInt("count"));
				medicine.setDosageMg(resultSet.getInt("dosage_mg"));
				Blob picture = resultSet.getBlob("picture");
				medicine.setPicture(picture.getBytes(1, (int) picture.length()));
				medicine.setNeedPrescription(resultSet.getBoolean("need_prescription"));
				medicine.setPrice(resultSet.getBigDecimal("price"));
				result.add(medicine);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return result;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, Medicine medicine) throws PersistException {
		try (InputStream stream = new ByteArrayInputStream(medicine.getPicture())) {
			statement.setString(NAME_INDEX, medicine.getName());
			statement.setLong(COUNT_IN_STORE_INDEX, medicine.getCountInStore());
			statement.setInt(COUNT_INDEX, medicine.getCount());
			statement.setInt(DOSAGE_MG_INDEX, medicine.getDosageMg());
			statement.setBinaryStream(PICTURE_INDEX, stream);
			statement.setBoolean(NEED_PRESCRIPTION_INDEX, medicine.isNeedPrescription());
			statement.setBigDecimal(PRICE_INDEX, medicine.getPrice());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, Medicine medicine) throws PersistException {
		try (InputStream stream = new ByteArrayInputStream(medicine.getPicture())) {
			statement.setString(NAME_INDEX, medicine.getName());
			statement.setLong(COUNT_IN_STORE_INDEX, medicine.getCountInStore());
			statement.setInt(COUNT_INDEX, medicine.getCount());
			statement.setInt(DOSAGE_MG_INDEX, medicine.getDosageMg());
			statement.setBinaryStream(PICTURE_INDEX, stream);
			statement.setBoolean(NEED_PRESCRIPTION_INDEX, medicine.isNeedPrescription());
			statement.setLong(ID_INDEX, medicine.getId());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}
}
