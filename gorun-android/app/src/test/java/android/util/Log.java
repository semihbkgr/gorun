package android.util;

import java.util.logging.Level;
import java.util.logging.Logger;

//Mock class of android.util.Log in Android SDK
//Log implementation for testing on non android host.
public class Log {

    private static final Logger logger;

    static{
        logger=Logger.getLogger(Log.class.getName());
        logger.setLevel(Level.ALL);
    }

    public static int i(String tag,String message){
        logger.info(stringLog(tag,message));
        return -1;
    }

    public static int w(String tag,String message){
        logger.warning(stringLog(tag,message));
        return -1;
    }

    private static String stringLog(String tag,String message){
        return String.format("%s: %s",tag,message);
    }

}
