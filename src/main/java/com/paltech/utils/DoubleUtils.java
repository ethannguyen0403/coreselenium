package com.paltech.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DoubleUtils {
    /**
     * Round a double with 2 places
     *
     * @param val
     * @return a generated password
     */
    public static double roundWithTwoPlaces(RoundingMode mode, double val) {
        return new BigDecimal(Double.toString(val)).setScale(2, mode).doubleValue();
    }

    public static double roundUpWithTwoPlaces(double val) {
        return roundWithTwoPlaces(RoundingMode.HALF_UP, val);
    }

    public static double roundDownWithTwoPlaces(double val) {
        return roundWithTwoPlaces(RoundingMode.HALF_DOWN, val);
    }

    public static double roundEvenWithTwoPlaces(double val) {
        return roundWithTwoPlaces(RoundingMode.HALF_EVEN, val);
    }
    public static double truncateTo( double unroundedNumber, int decimalPlaces ){
        int truncatedNumberInt = (int)( unroundedNumber * Math.pow( 10, decimalPlaces ) );
        double truncatedNumber = (double)( truncatedNumberInt / Math.pow( 10, decimalPlaces ) );
        return truncatedNumber;
    }

    public static double parseDouble(String text) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            if (text.contains(",")) {
                Number number = format.parse(text);
                return number.doubleValue();
            }else
                return  Double.parseDouble(text);

    }
}
