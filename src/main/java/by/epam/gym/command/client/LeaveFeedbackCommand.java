package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Order;
import by.epam.gym.model.Page;
import by.epam.gym.service.OrderService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Command to update feedback about order.
 *
 * @author Dzmitry Turko
 * @see OrderService
 */
public class LeaveFeedbackCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LeaveFeedbackCommand.class);
    private static final String PROGRAM_INFO_PAGE = "/controller?command=client_myProgram&userId=";
    private static final String FEEDBACK_ATTRIBUTE = "feedback";
    private static final String ORDER_ID_ATTRIBUTE = "orderId";
    private static final String USER_ID_ATTRIBUTE = "userId";

    private static final String FEEDBACK_SUCCESS_MESSAGE = "&addingFeedback=Feedback added successfully.";
    private static final String FEEDBACK_FAILED_MESSAGE = "&addingFeedback=You have already left a review for this program.";
    private static final String FEEDBACK_CHECK_MESSAGE = "&addingFeedback=Failed! Check your input data.";

    /**
     * Implementation of command to update feedback about order.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String feedbackValue = request.getParameter(FEEDBACK_ATTRIBUTE);
        String userId = request.getParameter(USER_ID_ATTRIBUTE);
        if (feedbackValue.isEmpty()) {
            String url = PROGRAM_INFO_PAGE + userId + FEEDBACK_CHECK_MESSAGE;
            return new Page(url, true);
        }

        String orderIdValue = request.getParameter(ORDER_ID_ATTRIBUTE);
        int orderId = Integer.parseInt(orderIdValue);
        Order order = findOrderById(orderId);
        String oldFeedback = order.getFeedback();

        if (oldFeedback != null) {
            String url = PROGRAM_INFO_PAGE + userId + FEEDBACK_FAILED_MESSAGE;
            return new Page(url, true);
        }

        boolean isFeedbackSaved = updateFeedbackByOrderId(feedbackValue, orderId);
        String feedbackPrefix = isFeedbackSaved ? FEEDBACK_SUCCESS_MESSAGE : FEEDBACK_FAILED_MESSAGE;

        LOGGER.debug("User id:" + userId + ", feedback is saved " + isFeedbackSaved);
        String url = PROGRAM_INFO_PAGE + userId + feedbackPrefix;
        return new Page(url, true);
    }

    /**
     * The method updates order's feedback by order's ID.
     *
     * @param newFeedback new value of order's feedback.
     * @param orderId     the order's ID.
     * @throws ServiceException object if execution of method is failed.
     */
    private boolean updateFeedbackByOrderId(String newFeedback, int orderId) throws ServiceException {
        OrderService orderService = new OrderService();
        return orderService.updateFeedbackByOrderId(newFeedback, orderId);
    }

    /**
     * The method finds order by ID and returns it's entity.
     *
     * @param orderId the order's ID.
     * @return Order.
     * @throws ServiceException object if execution of method is failed.
     */
    private Order findOrderById(int orderId) throws ServiceException {
        OrderService orderService = new OrderService();
        Optional<Order> optionalOrder = orderService.findById(orderId);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            throw new ServiceException("Couldn't build order for client information.");
        }
    }
}