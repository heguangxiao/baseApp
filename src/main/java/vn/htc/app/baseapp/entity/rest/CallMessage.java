/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.entity.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.Tool;

/**
 *
 * @author Company
 */
public class CallMessage {

    static final Logger logger = Logger.getLogger(CallMessage.class);
    static final ObjectMapper mapper = new ObjectMapper();
    String account;
    String password;
    String audio;
    String typeAudio;
    String brandname;
    String phone;
    String campId;
    String route2;

    public CallMessage() {
        // Must have no-argument constructor
    }

    public String toJsonStr() {
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            String jsonInString = mapper.writeValueAsString(this);
            return jsonInString;
        } catch (JsonProcessingException e) {
            logger.error(Tool.getLogMessage(e));
            return "Error RequestMessage Json Objec:" + e.getMessage();
        }
    }

    public static CallMessage toObject(String jsonStr) {
        CallMessage result = null;
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            result = mapper.readValue(jsonStr, CallMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
        return result;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTypeAudio() {
        return typeAudio;
    }

    public void setTypeAudio(String typeAudio) {
        this.typeAudio = typeAudio;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getRoute2() {
        return route2;
    }

    public void setRoute2(String route2) {
        this.route2 = route2;
    }

}
