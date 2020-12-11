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

public class TrunkOut {

    final Logger logger = Logger.getLogger(TrunkOut.class);
    public static final HashMap<String, TrunkOut> CACHE = new HashMap<>();

    static {
        TrunkOut dao = new TrunkOut();
        ArrayList<TrunkOut> cache = dao.getAllTrunkOut();
        for (TrunkOut one : cache) {
            CACHE.put(one.getTrunkname(), one);
        }
    }

    public static void reload() {
        TrunkOut dao = new TrunkOut();
        ArrayList<TrunkOut> cache = dao.getAllTrunkOut();
        if (cache.size() > 0) {
            synchronized (CACHE) {
                CACHE.clear();
                for (TrunkOut one : cache) {
                    CACHE.put(one.getTrunkname(), one);
                }
                CACHE.notify();
            }
        }
        Tool.out("|===> TrunkOut is reloaded...");
    }

    public TrunkOut() {
    }

    private ArrayList<TrunkOut> getAllTrunkOut() {
        ArrayList all = new ArrayList();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM TRUNKOUT WHERE 1 = 1";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                TrunkOut acc = new TrunkOut();
                acc.setTrunkname(rs.getString("TRUNKNAME"));
                acc.setIp_sip(rs.getString("IP_SIP"));
                acc.setPort_sip(rs.getInt("PORT_SIP"));
                acc.setIp_rtp(rs.getString("IP_RTP"));
                acc.setPort_rtp(rs.getString("PORT_RTP"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setContext(rs.getString("CONTEXT"));
                acc.setTps_call(rs.getInt("TPS_CALL"));
                acc.setPrefix(rs.getString("PREFIX"));
                all.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return all;
    }

    String trunkname;
    String ip_sip;
    int port_sip;
    String ip_rtp;
    String port_rtp;
    String username;
    String password;
    String context;
    int tps_call;
    String prefix;

    public String getTrunkname() {
        return trunkname;
    }

    public void setTrunkname(String trunkname) {
        this.trunkname = trunkname;
    }

    public String getIp_sip() {
        return ip_sip;
    }

    public void setIp_sip(String ip_sip) {
        this.ip_sip = ip_sip;
    }

    public int getPort_sip() {
        return port_sip;
    }

    public void setPort_sip(int port_sip) {
        this.port_sip = port_sip;
    }

    public String getIp_rtp() {
        return ip_rtp;
    }

    public void setIp_rtp(String ip_rtp) {
        this.ip_rtp = ip_rtp;
    }

    public String getPort_rtp() {
        return port_rtp;
    }

    public void setPort_rtp(String port_rtp) {
        this.port_rtp = port_rtp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getTps_call() {
        return tps_call;
    }

    public void setTps_call(int tps_call) {
        this.tps_call = tps_call;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}
