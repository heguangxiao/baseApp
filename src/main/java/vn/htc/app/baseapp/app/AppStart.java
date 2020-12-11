package vn.htc.app.baseapp.app;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.AsteriskInfo;
import ch.loway.oss.ari4java.tools.ARIException;
import java.util.HashMap;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.MyConfig;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.db.PoolMng;
import vn.htc.app.baseapp.thread.MoniterApp;
import vn.htc.app.baseapp.thread.ReloadAble;

/**
 * *
 * Lop Main Start xu ly
 *
 * @author PLATUAN - CongNX(Lienhoa) updated
 */
public class AppStart {

    private static final Logger logger = Logger.getLogger(AppStart.class);
    public static boolean isRuning = true;
    public static int TPS_LOG = 100;

    public static final HashMap<String, Integer> USER_TPS_CURRENT_CALL = new HashMap<>();//Cache TPS cua cac user

    public static ARI ari = null;

    static {
        try {
            // Log4j
            MyConfig.initLog4j();
            // Load Config
            MyConfig.loadConfig();
            // -- 

            //***********KHOI TAO ConnectionPoolManager**************
            if (!PoolMng.CreatePool()) {
                Tool.out("Khong khoi tao duoc ket noi DB");
                System.exit(1);
            }

            //-----------------------------------------
        } catch (Exception ex) {
            logger.error("Thong so gateway chua du..." + Tool.getLogMessage(ex));
            System.exit(1);
        }
    }

    public static WebServer websever;
    public static ReloadAble reload;

    public static void appStop() {
        if (websever != null) {
            websever.stop();
        }

        if (reload != null) {
            reload.shutDown();
        }
    }

    public static void reloadCnf() {
        MyConfig.loadConfig();
    }

    //App nay xu li toan bo logic
    public static void main(String[] args) {
        try {
            //Webserver cung cap api don cuoc goi
//            connect();

            System.out.println("---------Bat dau khoi tao webserver");
            websever = new WebServer();
            websever.start();
            //Thuc hien khoi dong webserver phuc vu cac api call vao he thong

            MoniterApp moa = new MoniterApp();
            moa.start();

            reload = new ReloadAble();
            reload.start();

            System.out.println("Hello World!");
        } catch (Exception e) {
            logger.error("Appstart.main:" + Tool.getLogMessage(e));
        }
    }

    public static void connect() throws ARIException {

        System.out.println("Connecting to: " + MyConfig.SERVER_REMOTE[0].getRemote_ip()
                + " as " + MyConfig.SERVER_REMOTE[0].getRemote_user() + ":" + MyConfig.SERVER_REMOTE[0].getRemote_pass());

        ari = ARI.build("http://" + MyConfig.SERVER_REMOTE[0].getRemote_ip() + ":" + MyConfig.SERVER_REMOTE[0].getRemote_port() + "/", MyConfig.SERVER_REMOTE[0].getRemote_app(),
                MyConfig.SERVER_REMOTE[0].getRemote_user(), MyConfig.SERVER_REMOTE[0].getRemote_pass(),
                AriVersion.ARI_4_0_0);

        System.out.println("Connected through ARI: " + ari.getVersion());

        // let's raise an exeption if the connection is not valid
        AsteriskInfo ai = ari.asterisk().getInfo("");
        System.out.println("Hey! We're connected! Asterisk Version: " + ai.getSystem().getVersion());

    }

    public static void removeBridge(String bridgeId) throws ARIException {
        System.out.println("Removing bridge...." + bridgeId);
        ari.bridges().destroy(bridgeId, null);
    }

}
