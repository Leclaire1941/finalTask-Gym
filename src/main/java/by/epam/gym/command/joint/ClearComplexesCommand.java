package by.epam.gym.command.joint;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.service.ComplexService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command to clear all complexes at concrete training program by client and trainer.
 *
 * @author Dzmitry Turko
 * @see ComplexService
 */
public class ClearComplexesCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ClearComplexesCommand.class);
    private static final String MY_PROGRAM_COMMAND = "/controller?command=client_myProgram&userId=";
    private static final String EDIT_CLIENT_INFO_COMMAND = "/controller?command=trainer_editClientInfo&clientId=";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";

    private static final String DELETE_COMPLEXES_FAILED_MESSAGE = "&saveMessage=An error while deleting complexes.";
    private static final String DELETE_COMPLEXES_SUCCESS_MESSAGE = "&saveMessage=All complexes were deleted.";

    /**
     * Implementation of command to remove all complexes from concrete training program.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String userIdValue = getId(request);
        String nextCommand = getNextCommand(request);

        String programIdValue = request.getParameter(PROGRAM_ID_ATTRIBUTE);
        int programId = Integer.parseInt(programIdValue);
        ComplexService complexService = new ComplexService();
        boolean isComplexesRemoved = complexService.removeAllComplexes(programId);

        if (!isComplexesRemoved) {
            String url = nextCommand + userIdValue + DELETE_COMPLEXES_FAILED_MESSAGE;
            LOGGER.debug("UserID:" + userIdValue + " cannot delete complexes. Some problems from complex service.");
            return new Page(url, true);
        }

        String url = nextCommand + userIdValue + DELETE_COMPLEXES_SUCCESS_MESSAGE;
        return new Page(url, true);
    }


    /**
     * The method finds current user's role and returns correct next command to execute.
     *
     * @param request HttpServletRequest object.
     * @return String next command to execute.
     */
    private String getNextCommand(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        UserRole userRole;
        if (user != null) {
            userRole = user.getUserRole();
        } else {
            throw new UnsupportedOperationException("Unsupported operation from quest.");
        }

        if (userRole == UserRole.CLIENT) {
            return MY_PROGRAM_COMMAND;
        }
        return EDIT_CLIENT_INFO_COMMAND;
    }

    /**
     * The method finds which attribute was transferred to request and returns it's value.
     *
     * @param request HttpServletRequest object.
     * @return String the ID attribute.
     */
    private String getId(HttpServletRequest request) {
        String clientId = request.getParameter(CLIENT_ID_ATTRIBUTE);
        String userId = request.getParameter(USER_ID_ATTRIBUTE);

        if (clientId != null) {
            return clientId;

        } else if (userId != null) {
            return userId;

        } else {
            throw new IllegalArgumentException("There are no any user id argument to prepare complex.");
        }
    }
}
