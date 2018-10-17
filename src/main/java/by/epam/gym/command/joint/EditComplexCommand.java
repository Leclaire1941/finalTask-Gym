package by.epam.gym.command.joint;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.complex.Complex;
import by.epam.gym.model.user.User;
import by.epam.gym.model.user.UserRole;
import by.epam.gym.service.ComplexService;
import by.epam.gym.service.MachineService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Command to update complex information by client.
 *
 * @author Dzmitry Turko
 * @see ComplexService
 * @see MachineService
 */
public class EditComplexCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(EditComplexCommand.class);
    private static final String MY_PROGRAM_COMMAND = "/controller?command=client_myProgram&userId=";
    private static final String EDIT_CLIENT_INFO_COMMAND = "/controller?command=trainer_editClientInfo&clientId=";
    private static final String USER_ATTRIBUTE = "user";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String COMPLEX_ID_ATTRIBUTE = "complexId";
    private static final String MACHINE_NAME_ATTRIBUTE = "machineName";
    private static final String DIFFICULTY_ATTRIBUTE = "difficulty";
    private static final String REPEAT_COUNTS_ATTRIBUTE = "repeatCounts";

    private static final String UPDATE_COMPLEX_SUCCESS_MESSAGE = "&saveMessage=Complex saved.";
    private static final String UPDATE_COMPLEX_FAILED_MESSAGE = "&saveMessage=Complex is not saved. Some problems were detected.";
    private static final String INPUT_DATA_ERROR_MESSAGE = "&saveMessage=Complex is not saved. Check your input data.";
    private static final String BELOW_ZERO_ERROR_MESSAGE = "&saveMessage=Complex is not saved. Repeat counts is below zero.";

    /**
     * Implementation of command to update users's complex data.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);
        String nextCommand = getNextCommand(request);
        String repeatCountsValue = request.getParameter(REPEAT_COUNTS_ATTRIBUTE);
        Page pageWithErrorMessage = checkRepeatCountsValue(repeatCountsValue, userIdValue, nextCommand);

        if (pageWithErrorMessage != null) {
            return pageWithErrorMessage;
        }

        String complexIdValue = request.getParameter(COMPLEX_ID_ATTRIBUTE);
        int complexId = Integer.parseInt(complexIdValue);
        String difficulty = request.getParameter(DIFFICULTY_ATTRIBUTE);
        int repeatCounts = Integer.parseInt(repeatCountsValue);
        String machineName = request.getParameter(MACHINE_NAME_ATTRIBUTE);
        int machineId = findMachineIdByName(machineName);

        ComplexService complexService = new ComplexService();
        Complex complex = complexService.createComplexForUpdate(machineId, difficulty, repeatCounts, complexId);

        boolean isSaveSuccessful = complexService.updateComplex(complex);
        if (!isSaveSuccessful) {
            String url = nextCommand + userIdValue + UPDATE_COMPLEX_FAILED_MESSAGE;
            LOGGER.debug("UserID:" + userIdValue + " cannot edit complex. Some problems from complex service.");
            return new Page(url, true);
        }

        String url = nextCommand + userIdValue + UPDATE_COMPLEX_SUCCESS_MESSAGE;
        LOGGER.debug("UserID:" + userIdValue + " complex was edited - " + complex);
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
            throw new UnsupportedOperationException("Unexpected operation from quest.");
        }

        if (userRole == UserRole.CLIENT) {
            return MY_PROGRAM_COMMAND;
        }
        return EDIT_CLIENT_INFO_COMMAND;
    }

    /**
     * The method checks user's input data and returns null if data is valid.
     *
     * @param repeatCountsValue the input repeat counts data.
     * @param userIdValue       the user's ID.
     * @return Page.
     */
    private Page checkRepeatCountsValue(String repeatCountsValue, String userIdValue, String nextCommand) {
        if (repeatCountsValue.isEmpty()) {
            String url = nextCommand + userIdValue + INPUT_DATA_ERROR_MESSAGE;
            return new Page(url, true);
        }
        if (Integer.parseInt(repeatCountsValue) < 0) {
            String url = nextCommand + userIdValue + BELOW_ZERO_ERROR_MESSAGE;
            return new Page(url, true);
        }
        return null;
    }

    /**
     * The method finds machine ID by it's name.
     *
     * @param machineName the name of machine.
     * @return int.
     * @throws ServiceException object if execution of method is failed.
     */
    private int findMachineIdByName(String machineName) throws ServiceException {
        MachineService machineService = new MachineService();
        return machineService.findMachineIdByName(machineName);
    }
}