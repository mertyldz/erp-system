package com.allianz.erpsystem.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {

    public static final BigDecimal KDV = new BigDecimal(18);

    public static BigDecimal calculateTaxFreeTotalPrice(BigDecimal bigDecimal) {
        return bigDecimal.divide(
                new BigDecimal(100).add(KDV), 5, RoundingMode.HALF_UP
        ).multiply(new BigDecimal(100));
    }

    public static BigDecimal calculateTaxPrice(BigDecimal bigDecimal) {
        return bigDecimal.divide(
                new BigDecimal(100).add(KDV), 5, RoundingMode.HALF_UP
        ).multiply(KDV);
    }

    public static BigDecimal calculateNewPriceForTaxFreePrice(BigDecimal bigDecimal) {
        return Helper.KDV.multiply(bigDecimal).divide(new BigDecimal(100), 5, RoundingMode.HALF_UP).add(bigDecimal);
    }
}
