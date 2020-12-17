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

public class Agent {

    final Logger logger = Logger.getLogger(Agent.class);
    public static final HashMap<String, Agent> CACHE = new HashMap<>();

    static {
        Agent dao = new Agent();
        ArrayList<Agent> cache = dao.getAllAgent();
        for (Agent one : cache) {
            CACHE.put(one.getAgentnumber(), one);
        }
    }

    public static void reload() {
        Agent dao = new Agent();
        ArrayList<Agent> cache = dao.getAllAgent();
        if (cache.size() > 0) {
            synchronized (CACHE) {
                CACHE.clear();
                for (Agent one : cache) {
                    CACHE.put(one.getAgentnumber(), one);
                }
                CACHE.notify();
            }
        }
        Tool.out("|===> Agent is reloaded...");
    }

    public Agent() {
    }

    private ArrayList<Agent> getAllAgent() {
        ArrayList all = new ArrayList();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT "
                + "	a.NAME AS agentname, "
                + "	a.DESCRIPTION AS agentnumber, "
                + "	a.PASSWORD AS agentpass, "
                + "	s.NAME AS simname, "
                + "	s.CODE AS simcode, "
                + "	s.DESCRIPTION AS simdescription, "
                + "	s.OPER AS simoper, "
                + "	s.LOCATION AS simlocation, "
                + "	d.NAME AS devicename, "
                + "	d.CODE AS devicecode, "
                + "	d.DESCRIPTION AS devicedescription, "
                + "	d.IP AS deviceip, "
                + "	d.PORT AS deviceport "
                + "     FROM agent a "
                + "	LEFT JOIN sim s ON a.SIM_ID = s.ID "
                + "	LEFT JOIN device d ON s.LOCATION LIKE CONCAT( d.CODE, '%' );";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();
            while (rs.next()) {
                Agent acc = new Agent();
                acc.setAgentname(rs.getString("agentname"));
                acc.setAgentnumber(rs.getString("agentnumber"));
                acc.setAgentpass(rs.getString("agentpass"));
                acc.setSimname(rs.getString("simname"));
                acc.setSimcode(rs.getString("simcode"));
                acc.setSimdescription(rs.getString("simdescription"));
                acc.setSimoper(rs.getString("simoper"));
                acc.setSimlocation(rs.getString("simlocation"));
                acc.setDevicename(rs.getString("devicename"));
                acc.setDevicecode(rs.getString("devicecode"));
                acc.setDevicedescription(rs.getString("devicedescription"));
                acc.setDeviceip(rs.getString("deviceip"));
                acc.setDeviceport(rs.getString("deviceport"));
                all.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return all;
    }

    String agentname;
    String agentnumber;
    String agentpass;
    String simname;
    String simcode;
    String simdescription;
    String simoper;
    String simlocation;
    String devicename;
    String devicecode;
    String devicedescription;
    String deviceip;
    String deviceport;

    public String getSimname() {
        return simname;
    }

    public void setSimname(String simname) {
        this.simname = simname;
    }

    public String getSimcode() {
        return simcode;
    }

    public void setSimcode(String simcode) {
        this.simcode = simcode;
    }

    public String getSimdescription() {
        return simdescription;
    }

    public void setSimdescription(String simdescription) {
        this.simdescription = simdescription;
    }

    public String getSimoper() {
        return simoper;
    }

    public void setSimoper(String simoper) {
        this.simoper = simoper;
    }

    public String getSimlocation() {
        return simlocation;
    }

    public void setSimlocation(String simlocation) {
        this.simlocation = simlocation;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    public String getDevicedescription() {
        return devicedescription;
    }

    public void setDevicedescription(String devicedescription) {
        this.devicedescription = devicedescription;
    }

    public String getDeviceip() {
        return deviceip;
    }

    public void setDeviceip(String deviceip) {
        this.deviceip = deviceip;
    }

    public String getDeviceport() {
        return deviceport;
    }

    public void setDeviceport(String deviceport) {
        this.deviceport = deviceport;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getAgentnumber() {
        return agentnumber;
    }

    public void setAgentnumber(String agentnumber) {
        this.agentnumber = agentnumber;
    }

    public String getAgentpass() {
        return agentpass;
    }

    public void setAgentpass(String agentpass) {
        this.agentpass = agentpass;
    }

}
