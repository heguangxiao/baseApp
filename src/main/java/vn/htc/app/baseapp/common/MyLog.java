/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.common;

import org.apache.log4j.Logger;

public class MyLog {

    //-- A4
    private static final Logger DEBUG_LOG = Logger.getLogger("DEBUG_LOG");

    public static void logDebug(String message) {
        if (MyConfig.DEBUG) {
            String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
            DEBUG_LOG.info(className + ".class:[" + lineNumber + "] " + message);
        }
    }

    public static void logONDebug(String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        DEBUG_LOG.info(className + ".class:[" + lineNumber + "] " + message);
    }
    //-- A5
    private static final Logger REQUEST_LOG = Logger.getLogger("REQUEST_LOG");

    public static void logIncome(String message) {
        message = StringUtils.str2OneLine(message);
        REQUEST_LOG.info(message);
    }
    //-- A5
    private static final Logger REQUEST_ADS_LOG = Logger.getLogger("REQUEST_ADS_LOG");

    public static void logIncomeAds(String message) {
        REQUEST_ADS_LOG.info(message);
    }
    //-- A6
    private static final Logger SUBMIT_LOG = Logger.getLogger("SUBMIT_LOG");

    public static void logSubmit(String message) {
        message = StringUtils.str2OneLine(message);
        SUBMIT_LOG.info(message);
    }

}
