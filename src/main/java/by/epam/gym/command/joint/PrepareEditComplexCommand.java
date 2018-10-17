package by.epam.gym.command.joint;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Machine;
import by.epam.gym.model.Page;
import by.epam.gym.model.complex.ComplexDifficulty;
import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.service.MachineService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to prepare page with editing complex information.
 *
 * @author Dzmitry Turko
 * @see MachineService
 */
public class PrepareEditComplexCommand implements Command {
    private static final String EDIT_COMPLEX_PAGE = "/WEB-INF/jsp/common/editComplex.jsp";
    private static final String COMPLEX_ID_ATTRIBUTE = "complexId";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String MACHINES_ATTRIBUTE = "machines";
    private static final String DIFFICULTY_LIST_ATTRIBUTE = "difficultyList";

    /**
     * Implementation of command to prepare page with editing complex information.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String userId = getId(request);
        request.setAttribute(USER_ID_ATTRIBUTE, userId);

        String complexId = request.getParameter(COMPLEX_ID_ATTRIBUTE);
        request.setAttribute(COMPLEX_ID_ATTRIBUTE, complexId);

        MachineService machineService = new MachineService();
        List<Machine> machines = machineService.findAll();
        request.setAttribute(MACHINES_ATTRIBUTE, machines);

        List<ComplexDifficulty> difficultyList = new ArrayList<>();
        addAllDifficulties(difficultyList);
        request.setAttribute(DIFFICULTY_LIST_ATTRIBUTE, difficultyList);

        return new Page(EDIT_COMPLEX_PAGE);
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
            throw new IllegalArgumentException("There are no any user id argument for prepare complex.");
        }
    }

    /**
     * The method adds all types of complex difficulty to difficulty list.
     *
     * @param difficultyList list with difficulty types.
     */
    private void addAllDifficulties(List<ComplexDifficulty> difficultyList) {
        difficultyList.add(ComplexDifficulty.EASY);
        difficultyList.add(ComplexDifficulty.MEDIUM);
        difficultyList.add(ComplexDifficulty.HARD);
    }
}
