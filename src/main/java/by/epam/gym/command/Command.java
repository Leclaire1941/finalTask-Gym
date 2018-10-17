package by.epam.gym.command;

import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command. Realization of pattern - Command.
 *
 * @author Dzmitry Turko
 */
public interface Command {

    /**
     * Need to be implemented by commands classes.
     *
     * @param request HttpServletRequest object.
     * @see HttpServletRequest
     */
    Page execute(HttpServletRequest request) throws ServiceException;

}
