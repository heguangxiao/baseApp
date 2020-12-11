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
        String sql = "SELECT * FROM AGENT WHERE 1 = 1";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();
            while (rs.next()) {
                Agent acc = new Agent();
                acc.setAgentname(rs.getString("NAME"));
                acc.setAgentnumber(rs.getString("DESCRIPTION"));
                acc.setAgentpass(rs.getString("PASSWORD"));
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
