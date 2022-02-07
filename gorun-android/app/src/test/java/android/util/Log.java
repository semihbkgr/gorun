package android.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Mock class of android.util.Log in Android SDK
//Log implementation for testing on non android host.
public class Log {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int LOG_ID_MAIN = 0;
    public static final int LOG_ID_RADIO = 1;
    public static final int LOG_ID_EVENTS = 2;
    public static final int LOG_ID_SYSTEM = 3;
    public static final int LOG_ID_CRASH = 4;

    private static final Logger LOGGER;

    static {
        LOGGER = Logger.getLogger("JULMockOfAndroidLog");
        LOGGER.setLevel(Level.ALL);
    }

    private Log() {
    }

    public static int v(String tag, String msg) {
        return log(AndroidLogLevel.V, tag, msg);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return log(AndroidLogLevel.V, tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return log(AndroidLogLevel.D, tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return log(AndroidLogLevel.D, tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return log(AndroidLogLevel.I, tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return log(AndroidLogLevel.I, tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return log(AndroidLogLevel.W, tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return log(AndroidLogLevel.W, tag, msg, tr);
    }

    public static int w(String tag, Throwable tr) {
        return log(AndroidLogLevel.V, tag, tr);
    }

    public static int e(String tag, String msg) {
        return log(AndroidLogLevel.E, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return log(AndroidLogLevel.E, tag, msg, tr);
    }

    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static int println(int priority, String tag, String msg) {
        return println(LOG_ID_MAIN, priority, tag, msg);
    }

    public static int println(int bufID,
                              int priority, String tag, String msg) {
        return 0;
    }

    private static int log(AndroidLogLevel level, String tag, String message) {
        Level l = androidLogLevelToJULLevel(level);
        String m = formatLogMessage(tag, message);
        LOGGER.log(l, m);
        return 0;
    }

    private static int log(AndroidLogLevel level, String tag, Throwable throwable) {
        Level l = androidLogLevelToJULLevel(level);
        String m = formatLogMessage(tag, throwable);
        LOGGER.log(l, m);
        return 0;
    }

    private static int log(AndroidLogLevel level, String tag, String message, Throwable throwable) {
        Level l = androidLogLevelToJULLevel(level);
        String m = formatLogMessage(tag, message, throwable);
        LOGGER.log(l, m);
        return 0;
    }


    private static String formatLogMessage(String tag, String message) {
        return String.format("%s: %s", tag, message);
    }

    private static String formatLogMessage(String tag, Throwable throwable) {
        return String.format("%s, %s: %s", tag, throwable.getClass().getName(), throwable.getMessage());
    }

    private static String formatLogMessage(String tag, String message, Throwable throwable) {
        return String.format("%s: %s, %s: %s", tag, message, throwable.getClass().getName(), throwable.getMessage());
    }

    private static Level androidLogLevelToJULLevel(AndroidLogLevel level) {
        switch (level) {
            case V:
                return Level.ALL;
            case D:
                return Level.FINE;
            case I:
                return Level.INFO;
            case W:
                return Level.WARNING;
            case E:
                return Level.SEVERE;
            default:
                throw new IllegalArgumentException("Uncovered level, level: " + level.getClass());
        }
    }

    private enum AndroidLogLevel {
        V,
        D,
        I,
        W,
        E
    }

}
