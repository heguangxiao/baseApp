/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.app.AppStart;
import vn.htc.app.baseapp.common.DateProc;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.primarywork.MonitorWorker;

public class MoniterApp extends Thread {

    final Logger logger = Logger.getLogger(MoniterApp.class);

    public MoniterApp() {
        this.setName("MoniterApp [" + DateProc.createTimestamp() + "]");
        MonitorWorker.addDemonName(this.getName());
    }

    @Override
    public void run() {
        Tool.out("|===> " + this.getName() + " is started...");
        while (true) {
            try {
                InputStreamReader inR = new InputStreamReader(System.in);
                BufferedReader bR = new BufferedReader(inR);
                String input = bR.readLine();
                if (input != null) {
                    if (input.equalsIgnoreCase("reload")) {
                        AppStart.reloadCnf();
                        Tool.out("Reloaded Config file Completed....");
                    } else if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                        AppStart.appStop();
                    } else if (input.equals("writeConfig")) {
                        WriteConfigAndReloadAsterisk.WriteAndReload();
                    } else {
                        Tool.out("-------------\n");
                        Tool.out("An: Q de thoat khoi chuong trinh...");
                        Tool.out("An: [reload] de load lai config file...");
                        Tool.out("An: [writeConfig] viết lai config file...");
                    }
                }
            } catch (Exception ex) {
                logger.error(Tool.getLogMessage(ex));
            }
        }
    }

    
}
