package by.epam.gym.command.common;

import by.epam.gym.command.Command;
import by.epam.gym.model.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to show home page with login command.
 *
 * @author Dzmitry Turko
 */
public class HomePageCommand implements Command {
    private static final String HOME_PAGE = "/WEB-INF/jsp/common/home.jsp";

    /**
     * Implementation of command to show home page with login command.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        return new Page(HOME_PAGE);
    }
}
