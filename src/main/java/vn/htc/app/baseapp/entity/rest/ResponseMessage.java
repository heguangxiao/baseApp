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
 * @author @author LienHoa(CongNX)
 */
public class ResponseMessage {

    static final Logger logger = Logger.getLogger(CallMessage.class);
    static final ObjectMapper mapper = new ObjectMapper();

    int code;
    String message;

    public ResponseMessage() {
        code = 0;
        message = "Default error(null)";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseMessage json2Objec(String str) {
        ResponseMessage result = null;
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            result = mapper.readValue(str, ResponseMessage.class);
            if (result == null) {
                result = new ResponseMessage();
            }
        } catch (IOException e) {
            result = new ResponseMessage();
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
