package com.epam.pharmacy.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class View {

	private static final Logger log = LoggerFactory.getLogger(View.class);

	private static final String PATH_TO_JSP = "/WEB-INF/jsp/";
	private static final String JSP_FORMAT = ".jsp";

    private HttpServletRequest request;
    private HttpServletResponse response;

    public View(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void navigate(CommandResult result) {
        try {
            if (result.isRedirect()) {
                response.sendRedirect(result.getView());
            } else {
                String path = PATH_TO_JSP + result.getView() + JSP_FORMAT;
                request.getRequestDispatcher(path).forward(request, response);
            }
        } catch (ServletException | IOException e) {
            log.error("Can't work redirect dispatcher" ,e);
        }
    }
}
