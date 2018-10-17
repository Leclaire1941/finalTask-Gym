package by.epam.gym.utils;

import by.epam.gym.exception.ServiceException;
import by.epam.gym.service.OrderService;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util class to work with date.
 *
 * @author Dzmitry Turko
 * @see OrderService
 */
public class DateManager {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * This method calculates difference between new date and user's old date and returns difference.
     *
     * @param newDateValue    new date value to save at DB.
     * @param orderExpiration the order's expiration date.
     * @return int days difference.
     * @throws ServiceException object if execution of method is failed.
     */
    public static int getDaysDifference(String newDateValue, Date orderExpiration) throws ServiceException {
        Date newDate;
        try {
            newDate = new SimpleDateFormat(DATE_PATTERN).parse(newDateValue);
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        Days days = Days.daysBetween(new DateTime(orderExpiration), new DateTime(newDate));

        return days.getDays() + 1;
    }

    /**
     * This method checks if new program expiration date is before current date.
     *
     * @param programExpiration new program expiration date to save.
     * @return true if new date is before old date and otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public static boolean checkProgramExpirationDate(String programExpiration) throws ServiceException {
        Date newDate;
        try {
            newDate = new SimpleDateFormat(DATE_PATTERN).parse(programExpiration);
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        Date currentDate = new Date();
        return newDate.after(currentDate);
    }

    /**
     * This method checks if new program expiration date is after order's expiration date.
     *
     * @param programExpiration new program expiration date to save.
     * @param orderExpiration   the order's expiration date.
     * @return true if new date is after order expiration date and otherwise.
     * @throws ServiceException object if execution of method is failed.
     */
    public static boolean checkOrderExpirationDate(String programExpiration, Date orderExpiration) throws ServiceException {
        Date newDate;
        try {
            newDate = new SimpleDateFormat(DATE_PATTERN).parse(programExpiration);
        } catch (ParseException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        return newDate.before(orderExpiration);
    }
}