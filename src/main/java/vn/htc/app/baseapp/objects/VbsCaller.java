/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.objects;

/**
 *
 * @author @author LienHoa(CongNX)
 */
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import vn.htc.app.baseapp.common.Tool;
import vn.htc.app.baseapp.entity.rest.CallMessage;

public class VbsCaller {

    static final Logger logger = Logger.getLogger(CallMessage.class);
    static final ObjectMapper mapper = new ObjectMapper();

    String name;
    String number;

    public VbsCaller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static VbsCaller json2Objec(String str) {
        VbsCaller result = null;
        try {
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            result = mapper.readValue(str, VbsCaller.class);
            if (result == null) {
                result = new VbsCaller();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new VbsCaller();
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
