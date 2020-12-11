/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.entity.rest.CallMessage;

/**
 *
 * @author @author LienHoa(CongNX)
 */
public class VbsTpsCall {

    static final Logger logger = Logger.getLogger(CallMessage.class);
    static final ObjectMapper mapper = new ObjectMapper();

    int vte;
    int vms;
    int gpc;
    int vnm;
    int bl;
    int ddg;

    public VbsTpsCall() {
    }

    public int getVte() {
        return vte;
    }

    public void setVte(int vte) {
        this.vte = vte;
    }

    public int getVms() {
        return vms;
    }

    public void setVms(int vms) {
        this.vms = vms;
    }

    public int getGpc() {
        return gpc;
    }

    public void setGpc(int gpc) {
        this.gpc = gpc;
    }

    public int getVnm() {
        return vnm;
    }

    public void setVnm(int vnm) {
        this.vnm = vnm;
    }

    public int getBl() {
        return bl;
    }

    public void setBl(int bl) {
        this.bl = bl;
    }

    public int getDdg() {
        return ddg;
    }

    public void setDdg(int ddg) {
        this.ddg = ddg;
    }

    public static VbsTpsCall json2Objec(String str) {
        VbsTpsCall result = null;
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            result = mapper.readValue(str, VbsTpsCall.class);
            if (result == null) {
                result = new VbsTpsCall();
            }
        } catch (Exception e) {
            result = new VbsTpsCall();
//            e.printStackTrace();
            result = new VbsTpsCall();
            logger.error(Tool.getLogMessage(e));
        }
        return result;
    }

    public String toJson() {
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            String jsonInString = mapper.writeValueAsString(this);
            return jsonInString;
        } catch (JsonProcessingException e) {
            logger.error(Tool.getLogMessage(e));
            return "Error RequestMessage Json Objec:" + e.getMessage();
        }
    }
}
