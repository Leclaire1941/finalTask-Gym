package by.epam.gym.filters;

import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter to execute a user command. Checks a session with user.
 *
 * @author Dzmitry Turko
 * @see Filter
 */
public class CommandFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommandFilter.class);
    private static final String COMMAND_PARAM = "command";
    private static final String USER_ID_PARAM = "userId";
    private static final String LOGIN_PAGE_PARAMETER = "LOGIN_PAGE";
    private static final String USER_ATTRIBUTE = "user";
    private static final String COMMON_COMMAND_PATTERN = "common_";
    private static final String CLIENT_COMMAND_PATTERN = "client_";
    private static final String TRAINER_COMMAND_PATTERN = "trainer_";
    private static final String JOINT_COMMAND_PATTERN = "joint_";
    private String redirectPage;

    /**
     * This method initialize filters object.
     *
     * @param filterConfig the filters config.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        redirectPage = filterConfig.getInitParameter(LOGIN_PAGE_PARAMETER);
    }

    /**
     * Does main logic of filters.
     *
     * @param request  the servlet request.
     * @param response the servlet response.
     * @param chain    the filters chain of responsibility.
     * @throws IOException      object if execution of method is failed.
     * @throws ServletException object if execution of method is failed.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String currentCommand = httpServletRequest.getParameter(COMMAND_PARAM);
        boolean isQuestCommand = checkAction(currentCommand, COMMON_COMMAND_PATTERN);

        if (isQuestCommand) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);

            if (user != null) {
                UserRole userRole = user.getUserRole();
                boolean isRoleCorrect = checkRole(userRole, currentCommand);
                if (isRoleCorrect) {
                    String userIdParameterValue = httpServletRequest.getParameter(USER_ID_PARAM);
                    if (userIdParameterValue != null) {
                        int userIdParameter = Integer.parseInt(userIdParameterValue);
                        boolean isUserIdCorrect = user.getId() == userIdParameter;
                        if (isUserIdCorrect) {
                            chain.doFilter(request, response);
                        } else {
                            LOGGER.warn("Unexpected action from user:" + user.getId() + " command:" + currentCommand);
                            httpServletResponse.sendRedirect(redirectPage);
                        }
                    } else {
                        chain.doFilter(request, response);
                    }
                } else {
                    LOGGER.warn("Unexpected action from user:" + user.getId() + " command:" + currentCommand);
                    httpServletResponse.sendRedirect(redirectPage);
                }
            } else {
                LOGGER.warn("Unexpected action from quest");
                httpServletResponse.sendRedirect(redirectPage);
            }
        }

    }


    /**
     * Checks if the current command is for quest.
     *
     * @param currentCommand the servlet request.
     */
    private boolean checkAction(String currentCommand, String commandPattern) {
        return currentCommand.startsWith(commandPattern);
    }

    /**
     * This method cleans filter resources.
     */
    @Override
    public void destroy() {
    }

    /**
     * Checks if the user's role is correct for current command.
     *
     * @param userRole the user's role.
     * @param command  the current command.
     * @return boolean true if user role is correct and otherwise.
     */
    private boolean checkRole(UserRole userRole, String command) {

        switch (userRole) {
            case CLIENT: {
                return command.startsWith(CLIENT_COMMAND_PATTERN) || command.startsWith(JOINT_COMMAND_PATTERN);
            }
            case TRAINER: {
                return command.startsWith(TRAINER_COMMAND_PATTERN) || command.startsWith(JOINT_COMMAND_PATTERN);
            }
            default: {
                throw new IllegalArgumentException("Unknown user role.");
            }
        }
    }
}
