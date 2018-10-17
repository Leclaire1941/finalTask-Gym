package by.epam.gym.command.joint;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Machine;
import by.epam.gym.model.Page;
import by.epam.gym.model.complex.ComplexDifficulty;
import by.epam.gym.service.MachineService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to prepare page with adding complex information.
 *
 * @author Dzmitry Turko
 * @see ComplexDifficulty
 * @see MachineService
 */
public class PrepareAddComplexCommand implements Command {
    private static final String ADD_COMPLEX_PAGE = "/WEB-INF/jsp/common/addComplex.jsp";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";
    private static final String MACHINES_ATTRIBUTE = "machines";
    private static final String DIFFICULTY_LIST_ATTRIBUTE = "difficultyList";
    private static final String USER_ID_ATTRIBUTE = "userId";

    /**
     * Implementation of command to prepare page with adding complex information.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);
        request.setAttribute(USER_ID_ATTRIBUTE, userIdValue);

        String programIdValue = request.getParameter(PROGRAM_ID_ATTRIBUTE);
        request.setAttribute(PROGRAM_ID_ATTRIBUTE, programIdValue);

        MachineService machineService = new MachineService();
        List<Machine> machines = machineService.findAll();
        request.setAttribute(MACHINES_ATTRIBUTE, machines);

        List<ComplexDifficulty> difficultyList = new ArrayList<>();
        addAllDifficulties(difficultyList);
        request.setAttribute(DIFFICULTY_LIST_ATTRIBUTE, difficultyList);

        return new Page(ADD_COMPLEX_PAGE);
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
