package by.epam.gym.command.client;

import by.epam.gym.command.Command;
import by.epam.gym.exception.ServiceException;
import by.epam.gym.model.Page;
import by.epam.gym.model.user.User;
import by.epam.gym.service.UserService;
import by.epam.gym.utils.PrepaymentValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Command to add money to users's account balance.
 *
 * @author Dzmitry Turko
 * @see User
 * @see UserService
 */
public class DoPrepaymentCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(DoPrepaymentCommand.class);
    private static final String ORDER_INFO_COMMAND = "/controller?command=client_orderInfo&userId=";
    private static final String USER_ID_ATTRIBUTE = "userId";
    private static final String AMOUNT_ATTRIBUTE = "amount";

    private static final String PREPAYMENT_SUCCESS_MESSAGE = "&prepaymentMessage=Prepayment was done successfully.";
    private static final String PREPAYMENT_FAILED_MESSAGE = "&prepaymentMessage=Prepayment is not possible. " +
            "Contact your administrator.";
    private static final String PREPAYMENT_ERROR_MESSAGE = "&prepaymentMessage=Prepayment is not possible. " +
            "Check your input data.";

    /**
     * Implementation of command to add money to user's account balance.
     *
     * @param request HttpServletRequest object.
     * @return Page with next command.
     * @throws ServiceException object if execution of method is failed.
     */
    @Override
    public Page execute(HttpServletRequest request) throws ServiceException {
        String prepaymentValue = request.getParameter(AMOUNT_ATTRIBUTE);
        String userIdValue = request.getParameter(USER_ID_ATTRIBUTE);

        boolean isAmountValid = PrepaymentValidator.checkPrepayment(prepaymentValue);
        if (!isAmountValid) {
            String url = ORDER_INFO_COMMAND + userIdValue + PREPAYMENT_ERROR_MESSAGE;
            return new Page(url, true);
        }

        int userId = Integer.parseInt(userIdValue);
        User user = findUserById(userId);

        BigDecimal oldBalance = user.getAccountBalance();
        BigDecimal increaseValue = new BigDecimal(prepaymentValue);
        BigDecimal newBalance = takePrepaymentById(userId, increaseValue);
        LOGGER.debug("ID:" + userIdValue + "Old balance - " + oldBalance + ", amount - " + increaseValue +
                ", new balance - " + newBalance);

        String paymentPrefix = newBalance.compareTo(oldBalance) > 0 ?
                PREPAYMENT_SUCCESS_MESSAGE : PREPAYMENT_FAILED_MESSAGE;

        String url = ORDER_INFO_COMMAND + userId + paymentPrefix;
        return new Page(url, true);
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
     * The method updates user's account balance by ID and returns new balance.
     *
     * @param userId        the user's ID.
     * @param increaseValue the value to increase balance.
     * @return BigDecimal.
     * @throws ServiceException object if execution of method is failed.
     */
    private BigDecimal takePrepaymentById(int userId, BigDecimal increaseValue) throws ServiceException {
        UserService userService = new UserService();
        return userService.takePrepaymentById(userId, increaseValue);
    }
}
