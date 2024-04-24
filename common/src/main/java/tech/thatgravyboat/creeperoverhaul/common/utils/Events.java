package tech.thatgravyboat.creeperoverhaul.common.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.function.BooleanSupplier;

public enum Events {
    CHRISTMAS(() -> matchMonth(Calendar.DECEMBER) && matchDays(20, 31)),
    HALLOWEEN(() -> matchMonth(Calendar.OCTOBER) && matchDays(25, 31)),
    ST_PATRICKS_DAY(() -> matchMonth(Calendar.MARCH) && matchDays(15, 19)),
    APRIL_FOOLS(() -> matchMonth(Calendar.APRIL) && matchDays(1, 1)),
    NONE(() -> false);

    private final BooleanSupplier checker;

    Events(BooleanSupplier checker) {
        this.checker = checker;
    }

    private boolean isDay() {
        return checker.getAsBoolean();
    }

    public static Events getCurrentEvent() {
        return Arrays.stream(Events.values())
                .filter(Events::isDay)
                .findFirst()
                .orElse(Events.NONE);
    }

    private static boolean matchMonth(int month) {
        return Calendar.getInstance().get(Calendar.MONTH) == month;
    }

    private static boolean matchDays(int start, int end) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return day >= start && day <= end;
    }
}
