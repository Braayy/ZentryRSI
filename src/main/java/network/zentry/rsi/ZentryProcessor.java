package network.zentry.rsi;

import network.zentry.rsi.annotation.ZentryRequest;
import network.zentry.rsi.exception.ZentryRequestException;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ZentryProcessor {

    static ZentryRequest checkAnnotationPresent(Class<?> clazz) {
        for (Annotation annotation : clazz.getDeclaredAnnotations()) {
            if (annotation instanceof ZentryRequest) {
                return (ZentryRequest) annotation;
            }
        }

        throw new ZentryRequestException("Not found.");
    }

    public static Class getClassSendRequest() {
        try {
            Throwable thr = new Throwable();
            thr.fillInStackTrace();
            StackTraceElement[] ste = thr.getStackTrace();
            return Class.forName(ste[2].getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}