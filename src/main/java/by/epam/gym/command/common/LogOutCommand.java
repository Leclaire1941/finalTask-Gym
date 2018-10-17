package by.epam.gym.command.common;

import by.epam.gym.command.Command;
import by.epam.gym.model.Page;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command to user log out.
 *
 * @author Dzmitry Turko
 */
public class LogOutCommand implements Command {
    private static final String HOME_PAGE = "/";

    /**
     * Implementation of command to user log out.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     */
    @Override
    public Page execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new Page(HOME_PAGE, true);
    }
}
