package helpers;


import java.time.Duration;

public class Utils {

    public static void pause(final Duration time){
        try {
            Thread.sleep(time.toMillis());
        } catch (Exception e){

        }
    }

    public static String getCurrentMethodName() {return Thread.currentThread().getStackTrace()[3].getMethodName();}

    public static String getCurrentClassName() {return Thread.currentThread().getStackTrace()[3].getClassName();}


}
