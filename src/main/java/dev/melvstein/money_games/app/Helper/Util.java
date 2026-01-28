package dev.melvstein.money_games.app.Helper;

import java.time.Instant;
import java.util.Date;

public class Util {
    public static String getClassName() {
        String className = "unknownClassName";
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements.length > 2) {
            StackTraceElement element = stackTraceElements[2];
            className = element.getClassName();
            String[] parts = className.split("\\.");
            className = parts[parts.length - 1];
        }

        return className;
    }

    public static String getMethodName() {
        String methodName = "unknownMethodName";
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements.length > 2) {
            StackTraceElement element = stackTraceElements[2];
            methodName = element.getMethodName();
        }

        return methodName;
    }

    public static String getClassAndMethod() {
        String classAndMethod = "unknownClassAndMethod";
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements.length > 2) {
            StackTraceElement element = stackTraceElements[2];
            String className = element.getClassName();
            String methodName = element.getMethodName();
            String[] parts = className.split("\\.");
            className = parts[parts.length - 1];
            classAndMethod = className + "." + methodName;
        }

        return classAndMethod;
    }

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
