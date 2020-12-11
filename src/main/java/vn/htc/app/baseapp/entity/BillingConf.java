package vn.htc.app.baseapp.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.db.DBPool;

public class BillingConf {

    final Logger logger = Logger.getLogger(BillingConf.class);
    public static final HashMap<String, Integer> CACHE = new HashMap<>();

    static {
        BillingConf dao = new BillingConf();
        ArrayList<BillingConf> cache = dao.getBillingPriceOnBoot();
        for (BillingConf one : cache) {
            CACHE.put(one.getTelco().toUpperCase(), one.getPrice());
        }
    }

    public static void reload() {
        BillingConf dao = new BillingConf();
        ArrayList<BillingConf> cache = dao.getBillingPriceOnBoot();
        if (cache.size() > 0) {
            synchronized (CACHE) {
                CACHE.clear();
                for (BillingConf one : cache) {
                    CACHE.put(one.getTelco().toUpperCase(), one.getPrice());
                }
                CACHE.notify();
            }
        }
        Tool.out("|===> BillingConf is reloaded...");
    }

    private ArrayList<BillingConf> getBillingPriceOnBoot() {
        ArrayList all = new ArrayList();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT TELCO,PRICE FROM BILLINGCONF";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();
            while (rs.next()) {
                BillingConf acc = new BillingConf();
                acc.setTelco(rs.getString("TELCO"));
                acc.setPrice(rs.getInt("PRICE"));
                all.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return all;
    }

    String telco;
    int price;

    public String getTelco() {
        return telco;
    }

    public void setTelco(String telco) {
        this.telco = telco;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
