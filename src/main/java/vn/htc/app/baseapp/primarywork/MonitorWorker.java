/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.primarywork;

import java.util.LinkedList;
import vn.htc.app.baseapp.common.MyConfig;
import vn.htc.app.baseapp.common.Tool;

/**
 *
 * @author TUANPLA
 */
public class MonitorWorker {

    private static final LinkedList<String> DEMON_THREAD_LIST = new LinkedList<>();            // Dem So Demon Thread
    private static final LinkedList<WorkQueue> WORKS = new LinkedList<>();
    int delay;

    public static void addWorkQueue(final WorkQueue work) {
        synchronized (WORKS) {
            WORKS.add(work);
            WORKS.notify();
        }
    }

    public static void addDemonName(String name) {
        synchronized (DEMON_THREAD_LIST) {
            DEMON_THREAD_LIST.add(name);
            DEMON_THREAD_LIST.notify();
        }

    }

    public static void removeDemonName(String name) {
        synchronized (DEMON_THREAD_LIST) {
            DEMON_THREAD_LIST.remove(name);
            DEMON_THREAD_LIST.notify();
            Tool.out("|==> " + name + " ended...");
        }
    }

    public static int getDemonSize() {
        synchronized (DEMON_THREAD_LIST) {
            return DEMON_THREAD_LIST.size();
        }
    }

    public static void showDemon() {
        synchronized (DEMON_THREAD_LIST) {
            int i = 1;
            for (String one : DEMON_THREAD_LIST) {
                Tool.out((i++) + ". " + one + " is runing");
            }
            DEMON_THREAD_LIST.notify();
        }
    }

    public static void ShowMonitor() {
        synchronized (WORKS) {
            if (!WORKS.isEmpty()) {
                Tool.out("-------------MonitorWorker [" + MyConfig.LB_NODE + "]-----------");
                for (WorkQueue work : WORKS) {
                    Tool.out(
                            String.format("M-Worker [" + work.getName() + "] [%d] Active: %d, Wait %d, Completed: %d, Task: %d",
                                    work.getMaxPoolSize(),
                                    work.getActiveCount(),
                                    work.getWaitTaskCount(),
                                    work.getCompletedTaskCount(),
                                    work.getTaskCount()
                            )
                    );
                }
                showDemon();
            }
            WORKS.notify();
        }
    }
}
