package com.engineer.authorize;

/**
 * Created by Konrad on 19.09.2015.
 */


import java.security.GeneralSecurityException;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Stateless(name = "DemoBusinessRESTResource", mappedName = "ejb/DemoBusinessRESTResource")
public class DemoBusinessRESTResource implements DemoBusinessRESTResourceProxy {

    private static final long serialVersionUID = -6663599014192066936L;


    public Response login(@Context HttpHeaders httpHeaders,
                          @FormParam("username") String username,
                          @FormParam("password") String password) {

        AuthenticatorHelper authenticatorHelper = AuthenticatorHelper.getInstance();

        String serviceKey = httpHeaders.getRequestHeader(DemoHTTPHeaderNames.SERVICE_KEY).get(0);

        try {
            String authToken = authenticatorHelper.login(serviceKey, username, password);

            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("auth_token", authToken);
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();

        } catch (final LoginException ex) {
            JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
            jsonObjBuilder.add("message", "Problem matching service key, username and password");
            JsonObject jsonObj = jsonObjBuilder.build();

            return getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity(jsonObj.toString()).build();
        }
    }

    public Response demoGetMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add("message", "Executed demoGetMethod");
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder(Response.Status.OK).entity(jsonObj.toString()).build();
    }


    public Response demoPostMethod() {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add("message", "Executed demoPostMethod");
        JsonObject jsonObj = jsonObjBuilder.build();

        return getNoCacheResponseBuilder(Response.Status.ACCEPTED).entity(jsonObj.toString()).build();
    }

    public Response logout(@Context HttpHeaders httpHeaders) {
        try {
            AuthenticatorHelper authenticatorHelper = AuthenticatorHelper.getInstance();
            String serviceKey = httpHeaders.getRequestHeader(DemoHTTPHeaderNames.SERVICE_KEY).get(0);
            String authToken = httpHeaders.getRequestHeader(DemoHTTPHeaderNames.AUTH_TOKEN).get(0);

            authenticatorHelper.logout(serviceKey, authToken);

            return getNoCacheResponseBuilder(Response.Status.NO_CONTENT).build();
        } catch (final GeneralSecurityException ex) {
            return getNoCacheResponseBuilder(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Response.ResponseBuilder getNoCacheResponseBuilder(Response.Status status) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);

        return Response.status(status).cacheControl(cc);
    }
}