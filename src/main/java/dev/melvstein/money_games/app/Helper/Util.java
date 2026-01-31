package dev.melvstein.money_games.app.Helper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
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

    public static String generateHmacSHA256Hex(String data, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            byte[] hash = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC SHA256 signature", e);
        }
    }

}
