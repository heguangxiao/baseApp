package vn.htc.app.baseapp.app;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import vn.htc.app.baseapp.common.MyConfig;
import vn.htc.app.baseapp.common.MyLog;
import vn.htc.app.baseapp.common.Tool;

public final class WebServer {

    final Logger logger = Logger.getLogger(WebServer.class);
    Server server;
    private String name;

    public WebServer() {
        this.name = "WebServer[" + System.currentTimeMillis() + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void start() {
        logger.setLevel(Level.INFO);
        try {
            ServletHolder sh = new ServletHolder(ServletContainer.class);
            sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
            sh.setInitParameter("com.sun.jersey.config.property.packages", "vn.htc.app.baseapp.resource.http");
            sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
            sh.setInitOrder(1);
            server = new Server(MyConfig.web_port);
            Context context = new Context(server, MyConfig.contextPath, Context.REQUEST);
            context.addServlet(sh, "/*");
            MyLog.logDebug("Start Restful Web Server: contextPath: " + MyConfig.contextPath + " | Port: " + MyConfig.web_port);
            Tool.out("Start Restful Web Server: contextPath: " + MyConfig.contextPath + " | Port: " + MyConfig.web_port);
            server.start();
        } catch (Exception ex) {
            logger.error(ex.getStackTrace());
        }
    }

    public void stop() {
        try {
            server.stop();
            MyLog.logDebug("STOP Restful Web Server: contextPath: " + MyConfig.contextPath + " | Port: " + MyConfig.web_port);
            Tool.out("STOP Restful Web Server: contextPath: " + MyConfig.contextPath + " | Port: " + MyConfig.web_port);
        } catch (Exception e) {
            logger.error(Tool.getLogMessage(e));
        }
    }
}
