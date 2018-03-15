package com.epam.pharmacy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.command.Command;
import com.epam.pharmacy.command.CommandFactory;
import com.epam.pharmacy.command.CommandResult;
import com.epam.pharmacy.command.View;
import com.epam.pharmacy.connection.ConnectionPool;
import com.epam.pharmacy.exceptions.ConnectionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet class, the main and only servlet, a single entry point for all queries
 *
 * @author Sosenkov Alexei
 */

public class ControllerServlet extends HttpServlet {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerServlet.class);

    private CommandFactory commandFactory;
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        LOGGER.info("The servlet/app start working");
        commandFactory = new CommandFactory();
        try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionException e) {
			LOGGER.warn("Can't get the connectionPool Instanse!", e);
		}
        LOGGER.info("Connection pool size : {}", connectionPool.size());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        Command command = commandFactory.getCommand(request);
        CommandResult result = command.execute(request, response);
        View view = new View(request, response);
        view.navigate(result);
    }

    @Override
    public void destroy() {
        LOGGER.info("The servlet/app stopped working");
        connectionPool.closeAllConnections();
    }
}