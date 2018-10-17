package by.epam.gym.utils;

import by.epam.gym.exception.ServiceException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateManagerTest {
    private static final String DAYS_DIFFERENCE_IS_INCORRECT_MESSAGE = "The difference between days is invalid.";
    private static final String DAYS_DIFFERENCE_IS_CORRECT_MESSAGE = "The difference between days is correct.";
    private static final String NEW_DATE_IS_BEFORE_CURRENT_MESSAGE = "New date is before current date.";
    private static final String NEW_DATE_IS_AFTER_CURRENT_MESSAGE = "New date is after current date.";
    private static final String NEW_DATE_IS_BEFORE_ORDER_EXPIRATION_MESSAGE = "New date is before order expiration date.";
    private static final String NEW_DATE_IS_AFTER_ORDER_EXPIRATION_MESSAGE = "New date is after order expiration date.";

    //    getDaysDifference method
    @DataProvider
    public static Object[][] validDataForDaysDifferenceCounting() {
        return new Object[][]{
                {"2018-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 9).getTime(), 1},
                {"2018-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 10).getTime(), 0},
                {"2018-09-10", new GregorianCalendar(2018, Calendar.AUGUST, 10).getTime(), 31},
                {"2019-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 10).getTime(), 365},
        };
    }

    //    getDaysDifference method
    @DataProvider
    public static Object[][] inValidDataForDaysDifferenceCounting() {
        return new Object[][]{
                {"2018-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 9).getTime(), 2},
                {"2018-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 10).getTime(), 1},
                {"2019-08-10", new GregorianCalendar(2018, Calendar.AUGUST, 10).getTime(), 366},
        };
    }

    //    checkProgramExpirationDate method
    @DataProvider
    public static Object[][] validDataForCheckProgramExpiration() {
        return new Object[][]{
                {"2018-12-10"},
                {"2019-01-01"},
                {"2019-02-10"},
        };
    }

    //    checkProgramExpirationDate method
    @DataProvider
    public static Object[][] inValidDataForCheckProgramExpiration() {
        return new Object[][]{
                {"2017-12-10"},
                {"2016-01-01"},
                {"2018-10-13"},
        };
    }

    //    checkOrderExpirationDate method
    @DataProvider
    public static Object[][] validDataForCheckOrderExpiration() {
        return new Object[][]{
                {"2018-08-10", new GregorianCalendar(2018, 8, 11).getTime()},
                {"2019-01-01", new GregorianCalendar(2019, 0, 2).getTime()},
                {"2030-2-10", new GregorianCalendar(2030, 1, 11).getTime()},

        };
    }

    //    checkOrderExpirationDate method
    @DataProvider
    public static Object[][] inValidDataForCheckOrderExpiration() {
        return new Object[][]{
                {"2019-08-10", new GregorianCalendar(2018, 8, 11).getTime()},
                {"2019-02-01", new GregorianCalendar(2019, 0, 2).getTime()},
                {"2050-2-10", new GregorianCalendar(2030, 1, 11).getTime()},

        };
    }

    @Test(dataProvider = "validDataForDaysDifferenceCounting")
    public void shouldReturnCorrectDaysDifference(String newDateValue, Date orderExpiration, int expectedDifference)
            throws ServiceException {
//        when
        int actualDifference = DateManager.getDaysDifference(newDateValue, orderExpiration);
//        then
        Assert.assertEquals(actualDifference, expectedDifference, DAYS_DIFFERENCE_IS_INCORRECT_MESSAGE);
    }

    @Test(dataProvider = "inValidDataForDaysDifferenceCounting")
    public void shouldReturnInCorrectDaysDifference(String newDateValue, Date orderExpiration, int expectedDifference)
            throws ServiceException {
//        when
        int actualDifference = DateManager.getDaysDifference(newDateValue, orderExpiration);
//        then
        Assert.assertNotEquals(actualDifference, expectedDifference, DAYS_DIFFERENCE_IS_CORRECT_MESSAGE);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void shouldThrowsServiceExceptionWhetCalculate() throws ServiceException {
//        given
        String newDateValue = "2018 the 1st of september";
        Date orderExpiration = new GregorianCalendar(2018, Calendar.AUGUST, 9).getTime();
//        when
        DateManager.getDaysDifference(newDateValue, orderExpiration);
//        then throw new ServiceException()
    }


    @Test(dataProvider = "validDataForCheckProgramExpiration")
    public void shouldReturnTrueWhenDateIsAfterCurrentDate(String newDate) throws ServiceException {
//        when
        boolean isNewAfterCurrentDate = DateManager.checkProgramExpirationDate(newDate);
//        then
        Assert.assertTrue(isNewAfterCurrentDate, NEW_DATE_IS_BEFORE_CURRENT_MESSAGE);
    }

    @Test(dataProvider = "inValidDataForCheckProgramExpiration")
    public void shouldReturnFalseWhenDateIsAfterCurrentDate(String newDate) throws ServiceException {
//        when
        boolean isNewAfterCurrentDate = DateManager.checkProgramExpirationDate(newDate);
//        then
        Assert.assertFalse(isNewAfterCurrentDate, NEW_DATE_IS_AFTER_CURRENT_MESSAGE);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void shouldThrowsServiceExceptionWhenChecksProgramExpiration() throws ServiceException {
//        given
        String newDateValue = "2018 the 1st of september";
//        when
        DateManager.checkProgramExpirationDate(newDateValue);
//        then throw new ServiceException()
    }


    @Test(dataProvider = "validDataForCheckOrderExpiration")
    public void shouldReturnTrueWhenDateIsBeforeOrderExpiration(String newDate, Date orderExpiration)
            throws ServiceException {
//        when
        boolean isNewBeforeOrderExpirationDate = DateManager.checkOrderExpirationDate(newDate, orderExpiration);
//        then
        Assert.assertTrue(isNewBeforeOrderExpirationDate, NEW_DATE_IS_AFTER_ORDER_EXPIRATION_MESSAGE);
    }

    @Test(dataProvider = "inValidDataForCheckOrderExpiration")
    public void shouldReturnFalseWhenDateIsAfterOrderExpiration(String newDate, Date orderExpiration)
            throws ServiceException {
//        when
        boolean isNewBeforeOrderExpirationDate = DateManager.checkOrderExpirationDate(newDate, orderExpiration);
//        then
        Assert.assertFalse(isNewBeforeOrderExpirationDate, NEW_DATE_IS_BEFORE_ORDER_EXPIRATION_MESSAGE);
    }
}