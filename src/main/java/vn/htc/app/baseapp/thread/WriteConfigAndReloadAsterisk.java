/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.thread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.MyConfig;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.entity.Agent;
import vn.htc.app.baseapp.entity.TrunkIn;
import vn.htc.app.baseapp.entity.TrunkOut;

/**
 *
 * @author CongNX(Lienhoa)
 */
public class WriteConfigAndReloadAsterisk {

    private final Logger logger = Logger.getLogger(WriteConfigAndReloadAsterisk.class);
    public static final String AST_DIR = MyConfig.AST_DIR;
    public static final String FILE_SIP_TRUNK_OUT = "sip_trunk_out.conf";
    public static final String FILE_SIP_TRUNK_IN = "sip_trunk_in.conf";
    public static final String FILE_SIP_AGENTS = "sip_agents.conf";
    public static final String FILE_SIP_AGENT_SIM_DEVICE = "sip_agent_sim_device.conf";

    private boolean stop = false;

    public void shutDown() {
        stop = true;
    }

    public WriteConfigAndReloadAsterisk() {
    }

    public static void WriteAndReload() {
//        writeTrunkIn();
//        writeTrunkOut();
//        writeAgentSip();
        writeAgentSimDevice();
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(WriteConfigAndReloadAsterisk.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Chỗ này chắc để cho linux
//        reloadAst_Cfg();
        Tool.out("|===> WriteConfigAndReloadAsterisk is reloaded...");
    }

    private static String builtTrunkOutCfg(TrunkOut trunkout) {
        String result = "[" + trunkout.getTrunkname() + "]\n"
                + "type=peer\n"
                + "qualify=yes\n"
                + "nat=yes\n"
                + "dtmfmode=inband\n"
                + "insecure=port,invite\n"
                + "canreinvite=no\n"
                + "host=" + trunkout.getIp_sip() + "\n"
                + "port=" + trunkout.getPort_sip() + "\n"
                + "disallow=all\n"
                + "allow=alaw,ulaw,g729\n"
                + "context=from-trunk";
        return result;
    }

    private static String builtTrunkInCfg(TrunkIn trunkin) {
        String result = "[" + trunkin.getTrunkname() + "]\n"
                + "type=peer\n"
                + "qualify=yes\n"
                + "nat=yes\n"
                + "dtmfmode=RFC2833\n"
                + "insecure=port,invite\n"
                + "canreinvite=no\n"
                + "host=" + trunkin.getIp_sip() + "\n"
                + "port=" + trunkin.getPort_sip() + "\n"
                + "disallow=all\n"
                + "allow=alaw,ulaw,g729\n"
                + "context=voicebrandname";
        return result;
    }

    private static String builtAgentsCfg(Agent agent) {
        String result = "[" + agent.getAgentnumber() + "]\n"
                + "deny=0.0.0.0/0.0.0.0\n"
                + "secret=" + agent.getAgentpass() + "\n"
                + "dtmfmode=rfc2833\n"
                + "canreinvite=no\n"
                + "context=voicebrandname\n"
                + "host=dynamic\n"
                + "type=friend\n"
                + "nat=yes\n"
                + "port=11103\n"
                + "qualify=yes\n"
                + "callgroup=\n"
                + "pickupgroup=\n"
                + "dial=SIP/" + agent.getAgentnumber() + "\n"
                /////////////////////////////////
                + "accountcode=" + "accountCode" + "\n"
                + "mailbox=" + agent.getAgentnumber() + "@device\n"
                + "permit=0.0.0.0/0.0.0.0\n"
                /////////////////////////////////
                + "callerid=" + "brandId" + " <" + agent.getAgentnumber() + ">\n"
                + "callcounter=yes\n"
                + "faxdetect=no";
        return result;
    }

    private static String builtAgentSimDeviceCfg(Agent agent) {
        String result = "agentname=" + agent.getAgentname() + "\n"
                + "agentnumber=" + agent.getAgentnumber() + "\n"
                + "agentpass=" + agent.getAgentpass()+ "\n"
                + "simname=" + agent.getSimname()+ "\n"
                + "simcode=" + agent.getSimcode()+ "\n"
                + "simdescription=" + agent.getSimdescription()+ "\n"
                + "simoper=" + agent.getSimoper()+ "\n"
                + "simlocation=" + agent.getSimlocation()+ "\n"
                + "devicename=" + agent.getDevicename()+ "\n"
                + "devicecode=" + agent.getDevicecode()+ "\n"
                + "devicedescription=" + agent.getDevicedescription()+ "\n"
                + "deviceport=" + agent.getDeviceport()+ "\n"
                + "deviceip=" + agent.getDeviceip()+ "\n"
                + "";
        return result;
    }

    private static void writeAgentSimDevice() {

        try {
            // Create new file
            String path = AST_DIR + FILE_SIP_AGENT_SIM_DEVICE;
            File file = new File(path);
            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // Write in file
            Collection<Agent> values = Agent.CACHE.values();
            if (values != null) {
                for (Agent one : values) {
                    String oneTrunkText = builtAgentSimDeviceCfg(one) + "\n\n\n";
                    bw.write(oneTrunkText);
                }
            }
            // Close connection
            bw.close();
            System.out.println("Write TrunkIn successfull");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void writeTrunkIn() {

        try {
            // Create new file
            String path = AST_DIR + FILE_SIP_TRUNK_IN;
            File file = new File(path);
            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // Write in file
            Collection<TrunkIn> values = TrunkIn.CACHE.values();
            if (values != null) {
                for (TrunkIn one : values) {
                    String oneTrunkText = builtTrunkInCfg(one) + "\n\n\n";
                    bw.write(oneTrunkText);
                }
            }
            // Close connection
            bw.close();
            System.out.println("Write TrunkIn successfull");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void writeTrunkOut() {
        try {
            // Create new file
            String path = AST_DIR + FILE_SIP_TRUNK_OUT;
            File file = new File(path);
            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // Write in file
            Collection<TrunkOut> values = TrunkOut.CACHE.values();
            if (values != null) {
                for (TrunkOut one : values) {
                    String oneTrunkText = builtTrunkOutCfg(one) + "\n\n\n";
                    bw.write(oneTrunkText);
                }
            }
            bw.close();
            System.out.println("Write TrunkOut successfull");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void writeAgentSip() {
        try {
            // Create new file
            String path = AST_DIR + FILE_SIP_AGENTS;
            File file = new File(path);
            // If file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            // Write in file
            Collection<Agent> values = Agent.CACHE.values();
            if (values != null) {
                for (Agent one : values) {
                    //                System.out.println(one.getAccountcode()+": user="+one.getUsername()+": balance="+one.getBalance());
                    String oneTrunkText = builtAgentsCfg(one) + "\n\n\n";
                    bw.write(oneTrunkText);
                }
            }
            // Close connection
            bw.close();
            System.out.println("Write Agent successfull");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void reloadAst_Cfg() {
        int iExitValue = 0;
        CommandLine executeAstReload = new CommandLine("rasterisk");
        executeAstReload.addArgument(" -x");
        executeAstReload.addArgument(" reload");
        DefaultExecutor oDefaultExecutor = new DefaultExecutor();
        oDefaultExecutor.setExitValue(0);
        try {
            iExitValue = oDefaultExecutor.execute(executeAstReload);
        } catch (IOException e) {
            System.err.println("Execution failed.");
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String path = AST_DIR + FILE_SIP_AGENTS;
        System.out.println("path : " + path);
        File file = new File(path);
        // If file doesn't exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
    }

}
