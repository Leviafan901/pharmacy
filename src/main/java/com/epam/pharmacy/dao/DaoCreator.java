package com.epam.pharmacy.dao;


import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.connection.ConnectionPool;
import com.epam.pharmacy.dao.mysql.MySqlUserDao;
import com.epam.pharmacy.exceptions.ConnectionException;
import com.epam.pharmacy.exceptions.PersistException;

/**
 * The class is intended for managing connections, namely the distribution and closing of connections.
 * Also creates instances of Dao objects and puts a connection in them.
 *
 * @author Sosenkov Alexei
 */
public class DaoCreator implements AutoCloseable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoCreator.class);
	
    /**
     * Field - contains a connection pool
     */
    private ConnectionPool connectionPool;
   
    /**
     * Field - Connect to the database.
     */
    private Connection connection;

    public DaoCreator() throws ConnectionException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }
    
    public MySqlUserDao getUserDao() throws PersistException {
        return new MySqlUserDao(connection);
    }
    
    /**
     * Puts the connection to the connection pool.
     */
    private void returnConnection() {
        connectionPool.returnConnection(connection);
    }
    
    /**
     * The method allows you to start a transaction.
     */
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Can't starting date transaction", e);
        }
    }

    /**
     * The method allows you to perform a transaction.
     **/
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOGGER.error("Can't committing date transaction", e);
        }
    }

    /**
     * The method to cancel the transaction.
     */
    public void rollbackTransaction() {
        try {
            LOGGER.debug("Call rollback transaction");
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Can't rollback data transaction", e);
        }
    }
    
	@Override
	public void close() throws Exception {
		returnConnection();
	}
}
