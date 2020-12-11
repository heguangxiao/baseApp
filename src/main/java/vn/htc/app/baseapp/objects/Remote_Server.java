/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.objects;

/**
 *
 * @author LienHoa(CongNX)
 */
public class Remote_Server {

    String name;
    String remote_ip;
    int remote_port;
    String remote_user;
    String remote_pass;
    String remote_app;

    public Remote_Server() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemote_ip() {
        return remote_ip;
    }

    public void setRemote_ip(String remote_ip) {
        this.remote_ip = remote_ip;
    }

    public int getRemote_port() {
        return remote_port;
    }

    public void setRemote_port(int remote_port) {
        this.remote_port = remote_port;
    }

    public String getRemote_user() {
        return remote_user;
    }

    public void setRemote_user(String remote_user) {
        this.remote_user = remote_user;
    }

    public String getRemote_pass() {
        return remote_pass;
    }

    public void setRemote_pass(String remote_pass) {
        this.remote_pass = remote_pass;
    }

    public String getRemote_app() {
        return remote_app;
    }

    public void setRemote_app(String remote_app) {
        this.remote_app = remote_app;
    }

}
