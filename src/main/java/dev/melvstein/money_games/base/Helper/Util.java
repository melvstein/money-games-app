package dev.melvstein.money_games.base.Helper;

import java.time.Instant;
import java.util.Date;

public class Util {

    public static String getRootCauseMessage(Throwable throwable) {
        Throwable root = throwable;

        while (root.getCause() != null) {
            root = root.getCause();
        }

        return root.getMessage();
    }

    public static Date fromInstantToDate(Instant instant) {
        if (instant == null) return null;

        return Date.from(instant);
    }
}
