package by.epam.gym.controller;

import by.epam.gym.command.Command;
import by.epam.gym.command.CommandFactory;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MVC pattern controller class.
 *
 * @author Dzmitry Turko
 * @see Page
 * @see Command
 * @see HttpServletResponse
 * @see HttpServletRequest
 */
public class Controller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Controller.class);
    private static final String ERROR_PAGE = "/jsp/common/error.jsp";
    private static final String COMMAND = "command";

    /**
     * Get method.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Post method.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * This method handle the request from user, define concrete command and response back.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(COMMAND);
        Command command = CommandFactory.getCommand(action);

        Page page;
        try {
            page = command.execute(request);
            if (page.isToRedirect()) {
                redirect(page, request, response);
            } else {
                forward(page, request, response);
            }

        } catch (ServiceException e) {
            LOGGER.fatal(e.getMessage(), e);
            page = new Page(ERROR_PAGE);
            forward(page, request, response);
        }
    }

    /**
     * This method is kind of call back to UI. Forward to concrete url.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param page     the object with value of concrete url.
     * @throws ServletException object if execution of method is failed.
     * @throws IOException      object if execution of method is failed.
     */
    private void forward(Page page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = page.getUrl();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
    }

    /**
     * This method is kind of call back to UI. Redirect to concrete url.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param page     the object with value of concrete url.
     * @throws IOException object if execution of method is failed.
     */
    private void redirect(Page page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String url = page.getUrl();
        String contextPath = request.getContextPath();
        response.sendRedirect(contextPath + url);
    }
}