package by.epam.gym.utils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class OrderCalculatorTest {
    private static final String DIFFERENT_PRICES_MESSAGE = "The calculated prices are different.";
    private static final String EQUALS_PRICES_MESSAGE = "The calculated prices are equals.";

    @DataProvider(name = "validData")
    public static Object[][] validDataForPriceCalculation() {
        return new Object[][]{
                {10, 10, new BigDecimal(9)},
                {45, 5, new BigDecimal(43)},
                {33, 17, new BigDecimal(28)},
                {50, 23, new BigDecimal(39)},
        };
    }

    @DataProvider(name = "inValidData")
    public static Object[][] inValidDataForPriceCalculation() {
        return new Object[][]{
                {10, 10, new BigDecimal(10)},
                {45, 6, new BigDecimal(44)},
                {35, 17, new BigDecimal(28)},
                {50, 23, new BigDecimal(40)},
        };
    }

    @Test(dataProvider = "validData")
    public void shouldPriceCalculatingBeSuccessful(int days, int discount, BigDecimal expectedPrice) {
//        when
        BigDecimal actualPrice = OrderCalculator.getOrderPrice(days, discount);
//        then
        Assert.assertEquals(actualPrice, expectedPrice, DIFFERENT_PRICES_MESSAGE);
    }

    @Test(dataProvider = "inValidData")
    public void shouldPriceCalculatingBeFailed(int days, int discount, BigDecimal expectedPrice) {
//        when
        BigDecimal actualPrice = OrderCalculator.getOrderPrice(days, discount);
//        then
        Assert.assertNotEquals(actualPrice, expectedPrice, EQUALS_PRICES_MESSAGE);
    }
}