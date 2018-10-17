package by.epam.gym.command.trainer;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.service.OrderService;
import by.epam.gym.service.ProgramService;
import by.epam.gym.service.UserService;
import by.epam.gym.utils.DateManager;
import by.epam.gym.utils.DiscountValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * Command to update user's basic information about discount and program.
 *
 * @author Dzmitry Turko
 * @see ProgramService
 * @see UserService
 */
public class DoEditBasicInfoCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DoEditBasicInfoCommand.class);
    private static final String EDIT_CLIENT_INFO_COMMAND = "/controller?command=trainer_editClientInfo&clientId=";
    private static final String CLIENT_ID_ATTRIBUTE = "clientId";
    private static final String PROGRAM_ID_ATTRIBUTE = "programId";
    private static final String DISCOUNT_ATTRIBUTE = "discount";
    private static final String NUTRITION_ATTRIBUTE = "nutrition";
    private static final String EXPIRATION_DATE_ATTRIBUTE = "expirationDate";

    private static final String UPDATE_BASIC_INFO_SUCCESS_MESSAGE = "&saveBasicMessage=All basic information is saved.";
    private static final String DISCOUNT_ERROR_MESSAGE = "&saveBasicMessage=Info is not saved. Check your discount input value.";
    private static final String DATE_IS_BEFORE_CURRENT_MESSAGE = "&saveBasicMessage=Input date is before your current date.";
    private static final String DATE_IS_AFTER_ORDER_EXPIRATION_MESSAGE = "&saveBasicMessage=Input date is after order expiration date.";
    private static final String SOME_UPDATES_FAILED_MESSAGE = "&saveBasicMessage=Some changes didn't save. Check your information.";

    /**
     * Implementation of command to update user's basic information about discount and program.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command to execute.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String programExpiration = request.getParameter(EXPIRATION_DATE_ATTRIBUTE);
        String discountValue = request.getParameter(DISCOUNT_ATTRIBUTE);
        String clientIdValue = request.getParameter(CLIENT_ID_ATTRIBUTE);
        int clientId = Integer.parseInt(clientIdValue);
        Date orderExpiration = findOrderExpirationDate(clientId);

        Page pageWithErrorMessage = checkInputData(discountValue, programExpiration, orderExpiration, clientId);
        if (pageWithErrorMessage != null) {
            return pageWithErrorMessage;
        }

        String programIdValue = request.getParameter(PROGRAM_ID_ATTRIBUTE);
        int programId = Integer.parseInt(programIdValue);
        String nutrition = request.getParameter(NUTRITION_ATTRIBUTE);
        int discount = Integer.parseInt(discountValue);

        boolean isAllDataSaved = updateClientData(nutrition, programExpiration, programId, discount, clientId);
        if (!isAllDataSaved) {
            String url = EDIT_CLIENT_INFO_COMMAND + clientIdValue + SOME_UPDATES_FAILED_MESSAGE;
            return new Page(url, true);
        }

        String url = EDIT_CLIENT_INFO_COMMAND + clientIdValue + UPDATE_BASIC_INFO_SUCCESS_MESSAGE;
        return new Page(url, true);
    }

    /**
     * The method checks user's input data and returns null if it is correct.
     *
     * @param discountValue     new value of user's discount.
     * @param programExpiration new value of user's program expiration date.
     * @param orderExpiration   the order's expiration date.
     * @param clientId          the user's ID.
     * @return Page with next command and error message.
     * @throws ServiceException object if execution of method is failed.
     */
    private Page checkInputData(String discountValue, String programExpiration, Date orderExpiration, int clientId)
            throws ServiceException {

        boolean isDiscountAcceptable = DiscountValidator.checkDiscount(discountValue);
        if (!isDiscountAcceptable) {
            String url = EDIT_CLIENT_INFO_COMMAND + clientId + DISCOUNT_ERROR_MESSAGE;
            return new Page(url, true);
        }

        boolean isAfterToday = DateManager.checkProgramExpirationDate(programExpiration);
        if (!isAfterToday) {
            String url = EDIT_CLIENT_INFO_COMMAND + clientId + DATE_IS_BEFORE_CURRENT_MESSAGE;
            return new Page(url, true);
        }

        boolean isBeforeOrderExpiration = DateManager.checkOrderExpirationDate(programExpiration, orderExpiration);
        if (!isBeforeOrderExpiration) {
            String url = EDIT_CLIENT_INFO_COMMAND + clientId + DATE_IS_AFTER_ORDER_EXPIRATION_MESSAGE;
            return new Page(url, true);
        }

        return null;
    }

    /**
     * The method updates user's basic information about discount and program.
     *
     * @param discount          new value of user's discount.
     * @param nutrition         new type of user's nutrition.
     * @param programExpiration new value of user's program expiration date.
     * @param clientId          the user's ID.
     * @param programId         the program's ID.
     * @throws ServiceException object if execution of method is failed.
     */
    private boolean updateClientData(String nutrition, String programExpiration, int programId, int discount, int clientId)
            throws ServiceException {

        ProgramService programService = new ProgramService();
        boolean isProgramSaved = programService.updateClientProgram(nutrition, programExpiration, programId);
        UserService userService = new UserService();
        boolean isDiscountSaved = userService.updateDiscountById(discount, clientId);

        LOGGER.debug("Client Id:" + clientId + ". Program is saved - " + isProgramSaved + ". Discount is saved - " + isDiscountSaved);
        return isDiscountSaved && isProgramSaved;
    }

    /**
     * This method find order's expiration date and return it.
     *
     * @param userId the user's ID.
     * @return Date order expiration.
     * @throws ServiceException object if execution of method is failed.
     */
    private static Date findOrderExpirationDate(int userId) throws ServiceException {
        OrderService orderService = new OrderService();
        Optional<Order> optionalOrder = orderService.findOrderByUserId(userId);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return order.getExpirationDate();

        } else {
            throw new ServiceException("Couldn't build order for editing basic info command.");
        }
    }
}