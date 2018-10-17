package by.epam.gym.filters;

import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filter to compare current page path and the paths that allowed to the user.
 *
 * @author Dzmitry Turko
 * @see Filter
 */
public class PageFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(PageFilter.class);
    private static final String LOGIN_PAGE_PARAMETER = "LOGIN_PAGE";
    private static final String USER_ATTRIBUTE = "user";

    private static final String CLIENT_PAGE_PATH_PATTERN = ".*/jsp/client/.*.jsp";
    private static final String TRAINER_PAGE_PATH_PATTERN = ".*/jsp/trainer/.*.jsp";
    private static final String COMMON_PAGE_PATH_PATTERN = ".*/jsp/common/.*.jsp*";
    private static final String WELCOME_PAGE_PATH_PATTERN = "//index.jsp";

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
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String currentPath = httpServletRequest.getServletPath();
        boolean isCommonJsp = checkPath(currentPath, COMMON_PAGE_PATH_PATTERN);
        boolean isWelcomePage = checkPath(currentPath, WELCOME_PAGE_PATH_PATTERN);
        if (isCommonJsp || isWelcomePage) {
            chain.doFilter(request, response);

        } else {
            HttpSession session = httpServletRequest.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (user == null) {
                LOGGER.warn("Unexpected action from quest" + currentPath);
                String contextPath = httpServletRequest.getContextPath();
                httpServletResponse.sendRedirect(contextPath + redirectPage);

            } else {
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * Method matches current path and allowed user's path.
     *
     * @param path        the servlet request.
     * @param pagePattern the servlet response.
     */
    private boolean checkPath(String path, String pagePattern) {
        Pattern pattern = Pattern.compile(pagePattern);
        Matcher matcher = pattern.matcher(path);

        return matcher.matches();
    }

    /**
     * This method cleans filter resources.
     */
    @Override
    public void destroy() {
    }
}
