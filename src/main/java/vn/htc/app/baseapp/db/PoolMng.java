/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.db;

import java.io.File;
import org.apache.log4j.Logger;
import snaq.db.ConnectionPool;
import snaq.db.ConnectionPoolManager;
import vn.htc.app.baseapp.common.Tool;

/**
 *
 * @author TUANPLA
 */
public class PoolMng {

    static final Logger logger = Logger.getLogger(PoolMng.class);
    static ConnectionPoolManager connPoolMng = null;

    public static boolean CreatePool() {
        try {
            File fcf = new File("../config/dbpool.properties");
            connPoolMng = ConnectionPoolManager.getInstance(fcf, "UTF-8");
            connPoolMng.registerShutdownHook();
            return Boolean.TRUE;
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
            return Boolean.FALSE;
        }
    }

    public static ConnectionPool getConnPool(String poolName) {
        return connPoolMng.getPool(poolName);
    }

//    public static void release() {
//        connPoolMng.release();
//    }
}
