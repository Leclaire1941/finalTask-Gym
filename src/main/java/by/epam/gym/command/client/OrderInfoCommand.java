package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.service.OrderService;
import by.epam.gym.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Command to show all information about user's order.
 *
 * @author Dzmitry Turko
 * @see OrderService
 * @see UserService
 */
public class OrderInfoCommand implements Command {
    private static final String ORDER_INFO_PAGE = "/WEB-INF/jsp/client/orderInfo.jsp";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String ORDER_ATTRIBUTE = "order";
    private static final String ACCOUNT_BALANCE_ATTRIBUTE = "accountBalance";
    private static final String DISCOUNT_ATTRIBUTE = "discount";
    private static final String CURRENT_DATE_ATTRIBUTE = "date";
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final String PREPAYMENT_MESSAGE_ATTRIBUTE = "prepaymentMessage";
    private static final String EXTENSION_MESSAGE_ATTRIBUTE = "extensionMessage";


    /**
     * Implementation of command to show all information about user's order.
     *
     * @param request HttpServletRequest object.
     * @return Page.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        prepareMessages(request);

        String currentDate = getCurrentDate(request);
        request.setAttribute(CURRENT_DATE_ATTRIBUTE, currentDate);

        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);
        int userId = Integer.parseInt(userIdValue);
        User user = findUserById(userId);
        request.setAttribute(USER_ID_ATTRIBUTE, userId);

        Order order = findOrderByUserId(userId);
        request.setAttribute(ORDER_ATTRIBUTE, order);

        BigDecimal accountBalance = user.getAccountBalance();
        request.setAttribute(ACCOUNT_BALANCE_ATTRIBUTE, accountBalance);

        int discount = user.getDiscount();
        request.setAttribute(DISCOUNT_ATTRIBUTE, discount);

        return new Page(ORDER_INFO_PAGE);

    }

    /**
     * The method sets to request any messages if they exist.
     *
     * @param request HttpServletRequest object.
     */
    private void prepareMessages(HttpServletRequest request) {
        String prepaymentMessage = request.getParameter(PREPAYMENT_MESSAGE_ATTRIBUTE);
        if (prepaymentMessage != null) {
            request.setAttribute(PREPAYMENT_MESSAGE_ATTRIBUTE, prepaymentMessage);
        }
        String extendMessage = request.getParameter(EXTENSION_MESSAGE_ATTRIBUTE);
        if (extendMessage != null) {
            request.setAttribute(EXTENSION_MESSAGE_ATTRIBUTE, extendMessage);
        }
    }

    /**
     * The method defines current date and returns it.
     *
     * @param request HttpServletRequest object.
     * @return String.
     */
    private String getCurrentDate(HttpServletRequest request) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(date);
    }

    /**
     * The method finds user by ID and returns it's entity.
     *
     * @param userId the user's ID.
     * @return User.
     * @throws ServiceException object if execution of method is failed.
     */
    private User findUserById(int userId) throws ServiceException {
        UserService userService = new UserService();
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ServiceException("Couldn't build user for client information.");
        }
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