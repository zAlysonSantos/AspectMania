package br.alysonsantos.util;

import java.text.DecimalFormat;

public class NumberUtils {
    private static String[] MONEY_TAGS = {"K", "M", "B", "T", "Q", "QQ", "S", "SS", "O", "N", "D"};

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###.##");

    public static String format(double value) {
        return DECIMAL_FORMAT.format(value);
    }

    public static String formatShort(double value) {
        return formatShort(value, 0);
    }

    private static String formatShort(double n, int iteration) {
        double f = ((long) n / 100) / 10.0D;
        return f < 1000 || iteration >= MONEY_TAGS.length - 1 ? DECIMAL_FORMAT.format(f) + MONEY_TAGS[iteration] : formatShort(f, iteration + 1);
    }
}