package by.epam.gym.utils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DiscountValidatorTest {
    private static final String DISCOUNT_IS_VALID_MESSAGE = "The discount value is valid.";
    private static final String DISCOUNT_IS_NOT_VALID_MESSAGE = "The discount value is not valid.";

    @DataProvider
    public static Object[][] validDataForDiscountVerification() {
        return new Object[][]{
                {"0"},
                {"1"},
                {"5"},
                {"14"},
                {"21"},
                {"30"},
        };
    }

    @DataProvider
    public static Object[][] inValidDataForDiscountVerification() {
        return new Object[][]{
                {"-10"},
                {"-1"},
                {""},
                {"31"},
                {"100"},
        };
    }

    @Test(dataProvider = "validDataForDiscountVerification")
    public void shouldReturnTrueWhenValueIsCorrect(String discountValue) {
//        when
        boolean actualResult = DiscountValidator.checkDiscount(discountValue);
//        then
        Assert.assertTrue(actualResult, DISCOUNT_IS_NOT_VALID_MESSAGE);
    }

    @Test(dataProvider = "inValidDataForDiscountVerification")
    public void shouldReturnFalseWhenValueIsInCorrect(String discountValue) {
//        when
        boolean actualResult = DiscountValidator.checkDiscount(discountValue);
//        then
        Assert.assertFalse(actualResult, DISCOUNT_IS_VALID_MESSAGE);
    }

}