package com.epam.pharmacy.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {

	private Map<String, Command> commands;

    private void init() {
        commands = new HashMap<>();

        //GET request
        commands.put("GET/welcome", new ShowPageCommand("welcome"));

        //common user-admin action

        //user action

        //admin action

        //POST request

        //common user-admin action

        //user action
        commands.put("POST/login", new LoginCommand());

        //admin action

    }
    public Command getCommand(HttpServletRequest request) {
        if (commands == null) {
            init();
        }
        return commands.get(request.getMethod() + request.getPathInfo());
    }
}
