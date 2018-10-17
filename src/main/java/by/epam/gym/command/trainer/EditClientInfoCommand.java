package by.epam.gym.command.trainer;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.model.complex.Complex;
import by.epam.gym.model.program.Program;
import by.epam.gym.model.user.User;
import by.epam.gym.service.ComplexService;
import by.epam.gym.service.OrderService;
import by.epam.gym.service.ProgramService;
import by.epam.gym.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Command to show all information about concrete client.
 *
 * @author Dzmitry Turko
 * @see OrderService
 * @see UserService
 * @see ProgramService
 * @see ComplexService
 */
public class EditClientInfoCommand implements Command {
    private static final String EDIT_CLIENT_INFO_PAGE = "/WEB-INF/jsp/trainer/editClientInfo.jsp";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String CLIENT_ATTRIBUTE = "client";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String PROGRAM_ATTRIBUTE = "program";
    private static final String COMPLEXES_ATTRIBUTE = "complexes";

    private static final String SAVE_COMPLEX_MESSAGE_ATTRIBUTE = "saveMessage";
    private static final String SAVE_BASIC_MESSAGE_ATTRIBUTE = "saveBasicMessage";

    /**
     * Implementation of command to show all information about concrete client.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        prepareMessages(request);

        String clientIdValue = request.getParameter(CLIENT_ID_ATTRIBUTE);
        request.setAttribute(CLIENT_ID_ATTRIBUTE, clientIdValue);

        int clientId = Integer.parseInt(clientIdValue);
        User client = findClientById(clientId);
        request.setAttribute(CLIENT_ATTRIBUTE, client);

        Order order = findOrderByClientId(clientId);
        request.setAttribute(ORDER_ATTRIBUTE, order);

        int orderId = order.getId();
        Program program = findProgramByOrderId(orderId);
        request.setAttribute(PROGRAM_ATTRIBUTE, program);

        int programId = program.getId();
        List<Complex> complexes = findComplexesByProgramId(programId);
        request.setAttribute(COMPLEXES_ATTRIBUTE, complexes);

        return new Page(EDIT_CLIENT_INFO_PAGE);
    }

    /**
     * The method sets to request any messages if they exist.
     *
     * @param request HttpServletRequest object.
     */
    private void prepareMessages(HttpServletRequest request) {
        String saveComplexMessage = request.getParameter(SAVE_COMPLEX_MESSAGE_ATTRIBUTE);
        if (saveComplexMessage != null) {
            request.setAttribute(SAVE_COMPLEX_MESSAGE_ATTRIBUTE, saveComplexMessage);
        }
        String saveBasicMessage = request.getParameter(SAVE_BASIC_MESSAGE_ATTRIBUTE);
        if (saveBasicMessage != null) {
            request.setAttribute(SAVE_BASIC_MESSAGE_ATTRIBUTE, saveBasicMessage);
        }
    }

    /**
     * The method finds user by ID and returns it's entity.
     *
     * @param userId the user's ID.
     * @return User.
     * @throws ServiceException object if execution of method is failed.
     */
    private User findClientById(int userId) throws ServiceException {
        UserService userService = new UserService();
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ServiceException("Couldn't build user for trainer information.");
        }
    }

    /**
     * The method finds program by order's ID and returns it's entity.
     *
     * @param orderId the user's ID.
     * @return Program.
     * @throws ServiceException object if execution of method is failed.
     */
    private Program findProgramByOrderId(int orderId) throws ServiceException {
        ProgramService programService = new ProgramService();
        Optional<Program> optionalProgram = programService.findProgramByOrderId(orderId);

        if (optionalProgram.isPresent()) {
            return optionalProgram.get();
        } else {
            throw new ServiceException("Couldn't build program for trainer information.");
        }
    }

    /**
     * The method finds order by user's ID and returns it's entity.
     *
     * @param userId the user's ID.
     * @return Order.
     * @throws ServiceException object if execution of method is failed.
     */
    private Order findOrderByClientId(int userId) throws ServiceException {
        OrderService orderService = new OrderService();
        Optional<Order> optionalOrder = orderService.findOrderByUserId(userId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new ServiceException("Couldn't build order for trainer information.");
        }
    }

    /**
     * The method finds all complexes in concrete program and returns list of them.
     *
     * @param programId the program's ID.
     * @return List.
     * @throws ServiceException object if execution of method is failed.
     */
    private List<Complex> findComplexesByProgramId(int programId) throws ServiceException {
        ComplexService complexService = new ComplexService();

        return complexService.findComplexesByProgramId(programId);
    }

}