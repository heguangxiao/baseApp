package vn.htc.app.baseapp.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.db.DBPool;

public class ClientAccount {

    final Logger logger = Logger.getLogger(ClientAccount.class);
    public static HashMap<String, ClientAccount> CACHE = new HashMap<>();

    static {
        ClientAccount dao = new ClientAccount();
        ArrayList<ClientAccount> cache = dao.getClientUserOnBoot();
        for (ClientAccount one : cache) {
            CACHE.put(one.getUsername(), one);
        }
    }

    public static void reload() {
        ClientAccount dao = new ClientAccount();

        ArrayList<ClientAccount> cache = dao.getClientUserOnBoot();

        if (cache.size() > 0) {
            synchronized (CACHE) {
                CACHE.clear();
                for (ClientAccount one : cache) {
                    CACHE.put(one.getUsername(), one);
                }
                CACHE.notify();
            }
        }
        Tool.out("|===> ClientAccount is reloaded...");
    }

    public static ClientAccount getAccount4Cache(String user) {
        synchronized (CACHE) {
            return CACHE.get(user);
        }
    }

    public static ClientAccount getAccount4CacheWithAccountCode(String code) {
        synchronized (CACHE) {
            Collection<ClientAccount> values = CACHE.values();
            for (ClientAccount one : values) {
                if (one != null) {
//                    System.out.println("Gia tri can so sanh="+one.getAccountcode()+" with username="+one.getUsername());
                    if (one.getCode() != null) {
                        if (one.getCode().equals(code)) {
                            return CACHE.get(one.getUsername());
                        }
                    }

                }

            }
            CACHE.notify();
        }
        return null;
    }

    private static void updateBalance4User(String username, long currentBalance) {
        boolean flag = true;
    }

    /**
     * Bao Gom ca Tai Khoan Bi Khoa
     *
     * @return
     */
    private ArrayList<ClientAccount> getClientUserOnBoot() {
        ArrayList all = new ArrayList();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE 1 = 1 AND TYPE = 2";

        try {
            conn = DBPool.getConnection();
            pstm = conn.prepareStatement(sql);

            rs = pstm.executeQuery();
            while (rs.next()) {
                ClientAccount acc = new ClientAccount();
                acc.setId(rs.getInt("ID"));
                acc.setStatus(rs.getInt("STATUS"));
                acc.setCreatedBy(rs.getInt("CREATEDBY"));
                acc.setUpdatedBy(rs.getInt("UPDATEDBY"));
                acc.setType(rs.getInt("TYPE"));
                acc.setUpdatedAt(rs.getLong("UPDATEDAT"));
                acc.setCreatedAt(rs.getLong("CREATEDAT"));
                acc.setUsername(rs.getString("USERNAME"));
                acc.setPassword(rs.getString("PASSWORD"));
                acc.setFullname(rs.getString("FULLNAME"));
                acc.setAddress(rs.getString("ADDRESS"));
                acc.setPhone(rs.getString("PHONE"));
                acc.setEmail(rs.getString("EMAIL"));
                acc.setCode(rs.getString("CODE"));
                all.add(acc);
            }
        } catch (SQLException ex) {
            logger.error(Tool.getLogMessage(ex));
        } finally {
            DBPool.freeConn(rs, pstm, conn);
        }
        return all;
    }

    private int id;
    private String username;
    private String password;
    private String fullname;
    private String address;
    private String phone;
    private String email;
    private int status;
    private int createdBy;
    private long createdAt;
    private int updatedBy;
    private long updatedAt;
    private int type;
    private String code;

    public static HashMap<String, ClientAccount> getCACHE() {
        return CACHE;
    }

    public static void setCACHE(HashMap<String, ClientAccount> CACHE) {
        ClientAccount.CACHE = CACHE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
