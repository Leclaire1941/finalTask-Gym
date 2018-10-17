package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.model.complex.Complex;
import by.epam.gym.model.program.Program;
import by.epam.gym.service.ComplexService;
import by.epam.gym.service.OrderService;
import by.epam.gym.service.ProgramService;
import by.epam.gym.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Command to show user's order, program and complex information.
 *
 * @author Dzmitry Turko
 * @see OrderService
 * @see ProgramService
 * @see ComplexService
 */
public class MyProgramCommand implements Command {
    private static final String MY_PROGRAM_PAGE = "/WEB-INF/jsp/client/myProgram.jsp";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String PROGRAM_ATTRIBUTE = "program";
    private static final String COMPLEXES_ATTRIBUTE = "complexes";
    private static final String TRAINER_NAME_ATTRIBUTE = "trainerName";
    private static final String USER_ID_ATTRIBUTE = "userId";

    private static final String ADDING_FEEDBACK_ATTRIBUTE = "addingFeedback";
    private static final String SAVE_MESSAGE_ATTRIBUTE = "saveMessage";

    /**
     * Implementation of command to show user's order, program and complex information.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        prepareMessages(request);

        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);
        int userId = Integer.parseInt(userIdValue);
        Order order = findOrderByUserId(userId);
        request.setAttribute(ORDER_ATTRIBUTE, order);

        int orderId = order.getId();
        Program program = findProgramByOrderId(orderId);
        request.setAttribute(PROGRAM_ATTRIBUTE, program);

        int programId = program.getId();
        String trainerName = findTrainerNameByProgramId(programId);
        request.setAttribute(TRAINER_NAME_ATTRIBUTE, trainerName);

        List<Complex> complexes = findComplexesByProgramId(programId);
        request.setAttribute(COMPLEXES_ATTRIBUTE, complexes);

        request.setAttribute(USER_ID_ATTRIBUTE, userId);

        return new Page(MY_PROGRAM_PAGE);
    }

    /**
     * The method sets to request any messages if they exist.
     *
     * @param request HttpServletRequest object.
     */
    private void prepareMessages(HttpServletRequest request) {
        String feedbackMessage = request.getParameter(ADDING_FEEDBACK_ATTRIBUTE);
        if (feedbackMessage != null) {
            request.setAttribute(ADDING_FEEDBACK_ATTRIBUTE, feedbackMessage);
        }
        String saveComplexMessage = request.getParameter(SAVE_MESSAGE_ATTRIBUTE);
        if (saveComplexMessage != null) {
            request.setAttribute(SAVE_MESSAGE_ATTRIBUTE, saveComplexMessage);
        }
    }

    /**
     * The method finds training program by order's ID and returns it's entity.
     *
     * @param orderId the order's ID.
     * @return Program.
     * @throws ServiceException object if execution of method is failed.
     */
    private Program findProgramByOrderId(int orderId) throws ServiceException {
        ProgramService programService = new ProgramService();
        Optional<Program> optionalProgram = programService.findProgramByOrderId(orderId);

        if (optionalProgram.isPresent()) {
            return optionalProgram.get();
        } else {
            throw new ServiceException("Couldn't build program for client information.");
        }
    }

    /**
     * The method finds list of complexes by program ID and returns this list.
     *
     * @param programId the program's ID.
     * @return list of complexes
     * @throws ServiceException object if execution of method is failed.
     */
    private List<Complex> findComplexesByProgramId(int programId) throws ServiceException {
        ComplexService complexService = new ComplexService();

        return complexService.findComplexesByProgramId(programId);
    }

    /**
     * The method finds user's trainer name by program ID and returns this name.
     *
     * @param programId the program's ID.
     * @return String.
     * @throws ServiceException object if execution of method is failed.
     */
    private String findTrainerNameByProgramId(int programId) throws ServiceException {
        UserService userService = new UserService();

        return userService.findTrainerNameByProgramId(programId);
    }

    /**
     * The method finds order by user's ID and returns it's entity.
     *
     * @param userId the user's ID.
     * @return Order.
     * @throws ServiceException object if execution of method is failed.
     */
    private Order findOrderByUserId(int userId) throws ServiceException {
        OrderService orderService = new OrderService();
        Optional<Order> optionalOrder = orderService.findOrderByUserId(userId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new ServiceException("Couldn't build order for client information.");
        }
    }
}