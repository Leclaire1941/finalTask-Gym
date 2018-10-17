package by.epam.gym.utils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PrepaymentValidatorTest {
    private static final String PREPAYMENT_IS_VALID_MESSAGE = "The prepayment value is valid.";
    private static final String PREPAYMENT_IS_NOT_VALID_MESSAGE = "The prepayment value is not valid.";

    @DataProvider
    public static Object[][] validDataForPrepaymentVerification() {
        return new Object[][]{
                {"1"},
                {"6"},
                {"11"},
                {"33"},
                {"50"},
        };
    }

    @DataProvider
    public static Object[][] inValidDataForPrepaymentVerification() {
        return new Object[][]{
                {""},
                {"0"},
                {"-5"},
                {"-10"},
                {"51"},
                {"100"},
        };
    }

    @Test(dataProvider = "validDataForPrepaymentVerification")
    public void shouldReturnTrueWhenValueIsCorrect(String prepaymentValue) {
//        when
        boolean actualResult = PrepaymentValidator.checkPrepayment(prepaymentValue);
//        then
        Assert.assertTrue(actualResult, PREPAYMENT_IS_NOT_VALID_MESSAGE);
    }

    @Test(dataProvider = "inValidDataForPrepaymentVerification")
    public void shouldReturnFalseWhenValueIsInCorrect(String prepaymentValue) {
//        when
        boolean actualResult = PrepaymentValidator.checkPrepayment(prepaymentValue);
//        then
        Assert.assertFalse(actualResult, PREPAYMENT_IS_VALID_MESSAGE);
    }
}