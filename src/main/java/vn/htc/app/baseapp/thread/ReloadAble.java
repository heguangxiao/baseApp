/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.thread;

import org.apache.log4j.Logger;
import vn.htc.app.baseapp.app.AppStart;
import vn.htc.app.baseapp.common.DateProc;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.entity.Agent;
import vn.htc.app.baseapp.entity.ClientAccount;
import vn.htc.app.baseapp.entity.TrunkIn;
import vn.htc.app.baseapp.entity.TrunkOut;
import vn.htc.app.baseapp.primarywork.MonitorWorker;

/**
 *
 * @author CongNX(Lienhoa)
 */
public class ReloadAble extends Thread {

    static Logger logger = Logger.getLogger(ReloadAble.class);

    public ReloadAble() {
        this.setName("ReloadAble [" + DateProc.createTimestamp() + "]");
        MonitorWorker.addDemonName(this.getName());
    }
    
    boolean stop = false;

    public void shutDown() {
        stop = true;
    }

    @Override
    public void run() {
        Tool.out("|===> " + this.getName() + " is started...");
        while(AppStart.isRuning && !stop){
            try {
                Thread.sleep(120*1000);
                Tool.out("|===> Resource is Reloading...");
                TrunkIn.reload();
                TrunkOut.reload();
                Agent.reload();
                ClientAccount.reload();
                WriteConfigAndReloadAsterisk.WriteAndReload();                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                logger.error(Tool.getLogMessage(ex));
            }
        }
    }
}
