package by.epam.gym.command.common;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.util.List;
import java.util.Locale;

/**
 * Command to change view language.
 *
 * @author Dzmitry Turko
 * @see Locale
 */
public class ChangeLanguageCommand implements Command {
    private static final String HOME_PAGE = "/WEB-INF/jsp/common/home.jsp";
    private static final String TRAINER_PAGE = "/WEB-INF/jsp/trainer/trainerMain.jsp";
    private static final String CLIENT_PAGE = "/WEB-INF/jsp/client/clientMain.jsp";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String CLIENTS_ATTRIBUTE = "clients";
    private static final String RU_LANGUAGE = "ru";
    private static final String US_LANGUAGE = "en";
    private static final String RU_COUNTRY = "RU";
    private static final String US_COUNTRY = "US";
    private static final String LOCALE_PARAMETER = "locale";
    private static final Locale DEFAULT_LOCALE = new Locale(US_LANGUAGE, US_COUNTRY);

    /**
     * Implementation of command to change view language.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String localeValue = request.getParameter(LOCALE_PARAMETER);

        Locale locale;
        switch (localeValue) {
            case RU_LANGUAGE: {
                locale = new Locale(RU_LANGUAGE, RU_COUNTRY);
                break;
            }
            case US_LANGUAGE: {
                locale = new Locale(US_LANGUAGE, US_COUNTRY);
                break;
            }
            default: {
                locale = DEFAULT_LOCALE;
                break;
            }
        }

        HttpSession session = request.getSession();
        String fmtLocale = Config.FMT_LOCALE;
        Config.set(session, fmtLocale, locale);

        String url = getPage(request, session);
        return new Page(url);
    }

    /**
     * The method defines type of user and returns concrete page.
     *
     * @param request HttpServletRequest.
     * @param session HttpSession.
     * @return Page.
     */
    private String getPage(HttpServletRequest request, HttpSession session) throws ServiceException {
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (user != null) {
            UserRole role = user.getUserRole();
            switch (role) {
                case TRAINER:
                    List<User> clients = findAllClients();
                    request.setAttribute(CLIENTS_ATTRIBUTE, clients);
                    return TRAINER_PAGE;
                case CLIENT:
                    int userId = user.getId();
                    request.setAttribute(USER_ID_ATTRIBUTE, userId);
                    return CLIENT_PAGE;
                default:
                    throw new IllegalArgumentException("Unsupported user role while change language command.");
            }

        } else {
            return HOME_PAGE;
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
