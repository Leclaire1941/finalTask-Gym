package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.model.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to show client's main page.
 *
 * @author Dzmitry Turko
 */
public class GoToMainCommand implements Command {
    private static final String CLIENT_MAIN_PAGE = "/WEB-INF/jsp/client/clientMain.jsp";
    private static final String USER_ID_ATTRIBUTE = "userId";

    /**
     * Implementation of command to show client's main page.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        String userId = request.getParameter(USER_ID_ATTRIBUTE);
        request.setAttribute(USER_ID_ATTRIBUTE, userId);

        return new Page(CLIENT_MAIN_PAGE);
    }
}
