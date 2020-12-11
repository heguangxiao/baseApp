/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.htc.app.baseapp.resource.http;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.mortbay.jetty.HttpStatus;
import vn.htc.app.baseapp.entity.rest.ResponseMessage;

/**
 *
 * @author HTC-PC
 */
@Path("/hello")
public class HelloWorldService {

    static final Logger logger = Logger.getLogger(HelloWorldService.class);
    @Context
    HttpServletRequest request;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPostJson(String data) {
        ResponseMessage _rpmeReturn = new ResponseMessage();
        _rpmeReturn.setMessage("Hello World! " + data);
        return Response.status(HttpStatus.ORDINAL_200_OK).entity(_rpmeReturn.toJson()).build();
    }
    
    @GET
    public String hello() {
        return "Hello World!";
    }
}
