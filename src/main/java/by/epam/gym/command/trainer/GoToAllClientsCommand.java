package by.epam.gym.command.trainer;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to show all users with role "client" in DB.
 *
 * @author Dzmitry Turko
 * @see UserService
 */
public class GoToAllClientsCommand implements Command {
    private static final String TRAINER_MAIN_PAGE = "/WEB-INF/jsp/trainer/trainerMain.jsp";
    private static final String CLIENTS_ATTRIBUTE = "clients";

    /**
     * Implementation of command to show all users with role "client" in DB.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        UserService userService = new UserService();
        List<User> clients = userService.findAllClients();
        request.setAttribute(CLIENTS_ATTRIBUTE, clients);

        return new Page(TRAINER_MAIN_PAGE);
    }
}
