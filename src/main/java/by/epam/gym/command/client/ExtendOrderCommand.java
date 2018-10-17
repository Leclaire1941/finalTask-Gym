package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.service.OrderService;
import by.epam.gym.utils.DateManager;
import by.epam.gym.utils.OrderCalculator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Command to update order expiration date and minus order's price from user's balance.
 *
 * @author Dzmitry Turko
 * @see OrderService
 */
public class ExtendOrderCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(ExtendOrderCommand.class);
    private static final String ORDER_INFO_COMMAND = "/controller?command=client_orderInfo&userId=";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String EXTENSION_DATE_ATTRIBUTE = "extensionDate";
    private static final String ACCOUNT_BALANCE_ATTRIBUTE = "accountBalance";
    private static final String DISCOUNT_ATTRIBUTE = "discount";

    private static final String EXTENSION_WRONG_DATE_MESSAGE = "&extensionMessage=Input date is before your current expiration date.";
    private static final String EXTENSION_SUCCESS_MESSAGE = "&extensionMessage=Extension was done successfully.";
    private static final String EXTENSION_FAILED_MESSAGE = "&extensionMessage=Extension failed. You have no enough money.";
    private static final String EXTENSION_ERROR_MESSAGE = "&extensionMessage=Error while extending order.";


    /**
     * Implementation of command to update order's expiration date and users's account balance.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String newDateValue = request.getParameter(EXTENSION_DATE_ATTRIBUTE);
        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);
        int userId = Integer.parseInt(userIdValue);

        Date orderExpiration = findOrderExpirationDate(userId);
        int days = DateManager.getDaysDifference(newDateValue, orderExpiration);

        if (days < 0) {
            String url = ORDER_INFO_COMMAND + userIdValue + EXTENSION_WRONG_DATE_MESSAGE;
            return new Page(url, true);
        }

        String discountValue = request.getParameter(DISCOUNT_ATTRIBUTE);
        int discount = Integer.parseInt(discountValue);
        BigDecimal price = OrderCalculator.getOrderPrice(days, discount);

        String accountBalanceValue = request.getParameter(ACCOUNT_BALANCE_ATTRIBUTE);
        BigDecimal balance = new BigDecimal(accountBalanceValue);

        if (price.compareTo(balance) > 0) {
            String url = ORDER_INFO_COMMAND + userIdValue + EXTENSION_FAILED_MESSAGE;
            return new Page(url, true);
        }

        boolean isExtended = extendOrder(newDateValue, price, userId);
        String url = isExtended ? ORDER_INFO_COMMAND + userIdValue + EXTENSION_SUCCESS_MESSAGE
                : ORDER_INFO_COMMAND + userIdValue + EXTENSION_ERROR_MESSAGE;

        LOGGER.debug("ID:" + userIdValue + "price - " + price + ", newDateValue - " + newDateValue +
                ", order is extended - " + isExtended);
        return new Page(url, true);
    }

    /**
     * The method updates order's expiration date and users's account balance.
     *
     * @param newDateValue new value of order's expiration date.
     * @param price        value to subtract from user's account balance.
     * @param userId       the user's ID.
     * @return boolean.
     * @throws ServiceException object if execution of method is failed.
     */
    private boolean extendOrder(String newDateValue, BigDecimal price, int userId) throws ServiceException {
        OrderService orderService = new OrderService();
        return orderService.extendOrder(newDateValue, price, userId);
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
            throw new ServiceException("Couldn't build order for DateManager information.");
        }
    }
}