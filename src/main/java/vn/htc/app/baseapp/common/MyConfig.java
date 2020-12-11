package vn.htc.app.baseapp.common;

import java.io.File;
import org.apache.log4j.BasicConfigurator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.XMLFileHandler;
import vn.htc.app.baseapp.objects.Remote_Server;

public class MyConfig {

    static Logger logger = Logger.getLogger(MyConfig.class);
    private static final ConfigurationManager CFM = ConfigurationManager.getInstance();
    public static Configuration config;
    //------------
    public static String PATH_CACHE_LOG_REQUEST_API;      //--> log file
    public static String PATH_CACHE_LOG_REQUEST_CAMPAGIN;  // --> log file
    public static String PATH_CACHE_LOG_REQUEST_SIPTRUNK;      //--> log file
    public static String PATH_CACHE_LOG_REQUEST_SIPAGENT;             //--> log file
    //-- Web Service
    public static String contextPath;
    public static int web_port;
    public static String LB_NODE = "";              // NODE Process
    public static boolean DEBUG = false;
    public static boolean SV_ALERT = false;
    public static boolean NODE_DEV = false;
    public static boolean BUID_CDR = false;
    //--
    public static String REJECT_ACC = "";    
    // -- MAIL CONFIG ---
    public static String MAIL_USER;
    public static String MAIL_PASS;
    public static String MAIL_DEBUG;
    public static String MAIL_HOST;    
    //INFOR BANK1 SMS INTERNATIONAL
    public static String[] SERVER_ASTERISK;
    public static Remote_Server[] SERVER_REMOTE;
    // Đường dẫn lưu file config: trunkin, trunkout, agent
    public static String AST_DIR;
    
    public MyConfig() {
        
    }

    public static void loadConfig() {
        File file = new File("../config/config.xml");
        MyLog.logDebug("Config File:" + file.getName());
        XMLFileHandler handler = new XMLFileHandler();
        handler.setFile(file);
        try {
            MyLog.logDebug("trying to load file config");
            CFM.load(handler, "engineConfig");
            MyLog.logDebug("file config successfully processed");
            config = ConfigurationManager.getConfiguration("engineConfig");
            //-- Read MyConfig WebServer
            contextPath = config.getProperty("contextPath", "/", "webService");
            web_port = config.getIntProperty("port", 6688, "webService");
            //--
            MAIL_USER = getString("MAIL_USER", "11alert@ahp.vn", "EMAIL");
            MAIL_PASS = getString("MAIL_PASS", "11ahp@alert.vn", "EMAIL");
            MAIL_HOST = getString("MAIL_HOST", "11mail.ahp.vn", "EMAIL");
            MAIL_DEBUG = getString("MAIL_DEBUG", "false", "EMAIL");                        
            //--
            LB_NODE = MyConfig.getString("LB_NODE", "NOTEx", "appconfig");
            SV_ALERT = MyConfig.getBoolean("SV_ALERT", false, "appconfig");
            NODE_DEV = MyConfig.getBoolean("NODE_DEV", false, "appconfig");
            BUID_CDR = MyConfig.getBoolean("BUID_CDR", false, "appconfig");
            REJECT_ACC = MyConfig.getString("REJECT_ACC", "", "appconfig");            
            //----------
            PATH_CACHE_LOG_REQUEST_API = MyConfig.getString("PATH_CACHE_LOG_REQUEST_API", "", "appconfig");
            PATH_CACHE_LOG_REQUEST_CAMPAGIN = MyConfig.getString("PATH_CACHE_LOG_REQUEST_CAMPAGIN", "", "appconfig");
            PATH_CACHE_LOG_REQUEST_SIPTRUNK = MyConfig.getString("PATH_CACHE_LOG_REQUEST_SIPTRUNK", "", "appconfig");
            PATH_CACHE_LOG_REQUEST_SIPAGENT = MyConfig.getString("PATH_CACHE_LOG_REQUEST_SIPAGENT", "", "appconfig");            
            //------ Đường dẫn lưu file config: trunkin, trunkout, agent
            AST_DIR = MyConfig.getString("AST_DIR", "", "appconfig");
            //--------
            String strSERVER_ASTERISK = MyConfig.getString("NAME_SERVER", "", "listServerAsterisk");
            SERVER_ASTERISK = strSERVER_ASTERISK.split(",");
            if(SERVER_ASTERISK.length>0){
                SERVER_REMOTE = new Remote_Server[SERVER_ASTERISK.length];
                for(int i=0; i<SERVER_ASTERISK.length;i++){
                    String nameServer = SERVER_ASTERISK[i];
                    String remote_ip = MyConfig.getString("REMOTE_IP", "", nameServer);
                    int remote_port = MyConfig.getInt("REMOTE_PORT", 0, nameServer);
                    String remote_user = MyConfig.getString("REMOTE_USER", "", nameServer);
                    String remote_pass = MyConfig.getString("REMOTE_PASS", "", nameServer);
                    String remote_app = MyConfig.getString("REMOTE_APP", "", nameServer);
                    Remote_Server oneServerRemote = new Remote_Server();
                    oneServerRemote.setName(nameServer);
                    oneServerRemote.setRemote_ip(remote_ip);
                    oneServerRemote.setRemote_port(remote_port);
                    oneServerRemote.setRemote_user(remote_user);
                    oneServerRemote.setRemote_pass(remote_pass);
                    oneServerRemote.setRemote_app(remote_app);
                    
                    SERVER_REMOTE[i]=oneServerRemote;
                }
            }
        } catch (ConfigurationManagerException e) {
            logger.error("can not load file config!");
            logger.error(Tool.getLogMessage(e));
            System.exit(0);
        }
    }

    public static void initLog4j() {
        String log4jPath = "../config/log4j.properties";
        //--
        File fileLog4j = new File(log4jPath);
        if (fileLog4j.exists()) {
            Tool.out("====>Initializing Log4j:" + log4jPath);
            PropertyConfigurator.configure(log4jPath);
        } else {
            System.err.println("=====> *** " + log4jPath + " file not found, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        }
    }

    //--------------------------------
    public static int getInt(String properties, int defaultVal, String categoryName) {
        try {
            return Integer.parseInt(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(Tool.getLogMessage(e));
            return defaultVal;
        }
    }

    public static boolean getBoolean(String properties, boolean defaultVal, String categoryName) {
        try {
            return Integer.parseInt(config.getProperty(properties, 1 + "", categoryName)) == 1;
        } catch (NumberFormatException e) {
            logger.error(Tool.getLogMessage(e));
            return defaultVal;
        }
    }

    public static long getLong(String properties, long defaultVal, String categoryName) {
        try {
            return Long.parseLong(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(Tool.getLogMessage(e));
            return defaultVal;
        }
    }

    public static Double getDouble(String properties, Double defaultVal, String categoryName) {
        try {
            return Double.parseDouble(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(Tool.getLogMessage(e));
            return defaultVal;
        }
    }

    public static String getString(String properties, String defaultVal, String categoryName) {
        try {
            String val = config.getProperty(properties, defaultVal, categoryName);
            MyLog.logDebug(properties + ": " + val);
            return val;
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
            return defaultVal;
        }
    }

}
