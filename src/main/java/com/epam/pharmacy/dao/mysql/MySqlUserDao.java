package com.epam.pharmacy.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.epam.pharmacy.dao.AbstractDao;
import com.epam.pharmacy.domain.User;
import com.epam.pharmacy.domain.enumeration.Role;
import com.epam.pharmacy.exceptions.PersistException;

public class MySqlUserDao extends AbstractDao<User, Long> {

	private static final int NEEDED_USER = 0;
	private static final String DELETE_QUERY = "DELETE FROM User WHERE id = ?;";
	private static final String UPDATE_QUERY = "UPDATE User SET name=? last_name=? login=? password=? roles=? WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO User (name, last_name, login, password, roles) VALUES (?, ?, ?, ?, ?);";
	private static final String SELECT_QUERY_BY_ID = "SELECT name, last_name, login, MD5(password), roles FROM User WHERE id = ?;";
	private static final String SELECT_QUERY = "SELECT id, name, last_name, login, MD5(password), roles FROM User;";
	private static final String FIND_BY_LOGIN_PASSWORD = "SELECT * FROM User  WHERE login = ? AND password = ?;";
	
	public MySqlUserDao(Connection connection) throws PersistException {
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
	
	public User getUsersByLoginAndPassword(String login, String password)
			throws PersistException {
		List<User> users = null;
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    users = parseResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new PersistException("can't get by login and password ", e);
        }
        return users.get(NEEDED_USER);
    }

	@Override
	protected List<User> parseResultSet(ResultSet resultSet) throws PersistException {
		LinkedList<User> result = new LinkedList<User>();
		try {
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong("id"));
				user.setName(resultSet.getString("name"));
				user.setLastname(resultSet.getString("last_name"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				String role = resultSet.getString("roles");
				user.setRole(Role.valueOf(role));
				result.add(user);
			}
		} catch (Exception e) {
			throw new PersistException(e);
		}
		return result;
	}

	@Override
	protected void prepareStatementForInsert(PreparedStatement statement, User user)
			throws PersistException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}

	@Override
	protected void prepareStatementForUpdate(PreparedStatement statement, User user)
			throws PersistException {
		try {
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getLogin());
			statement.setString(4, user.getPassword());
			String role = String.valueOf(user.getRole());
			statement.setString(5, role);
			statement.setLong(7, user.getId());
		} catch (Exception e) {
			throw new PersistException(e);
		}
	}
}
