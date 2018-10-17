package by.epam.gym.command.common;

import by.epam.gym.command.Command;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.service.UserService;
import by.epam.gym.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * Command to user log in.
 *
 * @author Dzmitry Turko
 * @see UserService
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);
    private static final String CLIENT_MAIN_PAGE = "/WEB-INF/jsp/client/clientMain.jsp";
    private static final String TRAINER_MAIN_PAGE = "/WEB-INF/jsp/trainer/trainerMain.jsp";
    private static final String HOME_PAGE = "/WEB-INF/jsp/common/home.jsp";
    private static final String LOGIN_ATTRIBUTE = "login";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String USER_ID_ATTRIBUTE = "userId";

    private static final String LOGIN_ERROR_ATTRIBUTE = "loginError";
    private static final String LOGIN_ERROR_MESSAGE = "Incorrect login or password.";

    /**
     * Implementation of command to user log in.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String login = request.getParameter(LOGIN_ATTRIBUTE);
        String password = request.getParameter(PASSWORD_ATTRIBUTE);

        UserService userService = new UserService();
        Optional<User> optionalUser = userService.login(login, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = request.getSession();
            session.setAttribute(USER_ATTRIBUTE, user);
            LOGGER.debug("User was connected:" + user);

            UserRole userRole = user.getUserRole();
            if (userRole == UserRole.TRAINER) {
                List<User> clients = findAllClients();
                request.setAttribute(CLIENTS_ATTRIBUTE, clients);
            } else {
                int userId = user.getId();
                request.setAttribute(USER_ID_ATTRIBUTE, userId);
            }

            return getPage(user);

        } else {
            request.setAttribute(LOGIN_ERROR_ATTRIBUTE, LOGIN_ERROR_MESSAGE);
            LOGGER.warn("Cannot connect user - login:" + login + " password:" + password);
            return new Page(HOME_PAGE);
        }

    }

    /**
     * The method defines type of user and returns concrete page.
     *
     * @param user the user of application.
     * @return Page.
     */
    private Page getPage(User user) {
        UserRole userRole = user.getUserRole();

        switch (userRole) {
            case CLIENT:
                return new Page(CLIENT_MAIN_PAGE);
            case TRAINER:
                return new Page(TRAINER_MAIN_PAGE);
            default:
                throw new IllegalArgumentException("Illegal userRole value in getPage() function");
        }
    }

    /**
     * The method finds all clients for trainer page and returns list of clients.
     *
     * @return List.
     * @throws ServiceException object if execution of method is failed.
     */
    private List<User> findAllClients() throws ServiceException {
        UserService userService = new UserService();
        return userService.findAllClients();
    }
}