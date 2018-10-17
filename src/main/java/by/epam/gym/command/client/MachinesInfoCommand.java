package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Machine;
import by.epam.gym.model.Page;
import by.epam.gym.service.MachineService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Command to show all information about machines.
 *
 * @author Dzmitry Turko
 */
public class MachinesInfoCommand implements Command {
    private static final String MACHINES_INFO_PAGE = "/WEB-INF/jsp/client/machinesInfo.jsp";
    private static final String MACHINES_ATTRIBUTE = "machines";
    private static final String USER_ID_ATTRIBUTE = "userId";

    /**
     * Implementation of command to show all information about machines.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        MachineService machineService = new MachineService();

        List<Machine> machines = machineService.findAll();
        request.setAttribute(MACHINES_ATTRIBUTE, machines);

        String userId = request.getParameter(USER_ID_ATTRIBUTE);
        request.setAttribute(USER_ID_ATTRIBUTE, userId);

        return new Page(MACHINES_INFO_PAGE);
    }
}