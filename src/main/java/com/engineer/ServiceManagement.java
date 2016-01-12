package com.engineer;

import com.engineer.model.User;
import com.engineer.model.messageJson.JsonMessage;
import sun.security.util.Resources_sv;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;

/**
 * Created by Konrad on 30.09.2015.
 */


@Local
@Path( "ServiceManagement" )
public interface ServiceManagement {

    @POST
    @Path("login")
    @Produces( MediaType.APPLICATION_JSON )
    Response login(@HeaderParam("email") String username,
                   @HeaderParam("password") String password);

    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    Response addUser(@HeaderParam("name") String name,
                     @HeaderParam("email") String email,
                     @HeaderParam("password") String password);
    @POST
    @Path("logout")
    @Produces(MediaType.APPLICATION_JSON)
    Response logout(@HeaderParam("email") String email,
                    @HeaderParam("token") String token);

    @POST
    @Path("remind_password")
    @Produces(MediaType.APPLICATION_JSON)
    Response remindPassword(@HeaderParam("email") String email);

    @POST
    @Path("addTokenUser")
    @Produces(MediaType.APPLICATION_JSON)
    Response addTokenToUser(@HeaderParam("email") String email,
                            @HeaderParam("tokenGCM") String tokenGCM,
                            @HeaderParam("tokenAuth") String tokenAuth);

    @POST
    @Path("getAllServiceTasks")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllUserServicesTasks(@HeaderParam("email") String email,
                                     @HeaderParam("tokenAuth") String tokenAuth);

    @POST
    @Path("getServiceTask")
    @Produces(MediaType.APPLICATION_JSON)
    Response getSpecificUserServiceTask(@HeaderParam("email") String email,
                                     @HeaderParam("task_id") String serviceTaskId,
                                     @HeaderParam("tokenAuth") String tokenAuth);

    @GET
    @Path("getAllUserBikes")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllUserBikes(@HeaderParam("email") String email,
                             @HeaderParam("tokenAuth") String tokenAuth);

    @GET
    @Path("getUserBikes")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUserBike(@HeaderParam("email") String email,
                         @HeaderParam("tokenAuth") String tokenAuth,
                         @HeaderParam("bike_id") String bikeId);

    @POST
    @Path("addUserBikes")
    @Produces(MediaType.APPLICATION_JSON)
    Response addUserBike(@HeaderParam("email") String email,
                         @HeaderParam("tokenAuth") String tokenAuth,
                         @HeaderParam("bike_name") String bikeName,
                         @HeaderParam("bike_desc") String bikeDesc,
                         @HeaderParam("bike_amount") String bikeAmount,
                         @HeaderParam("bike_range") String bikeRange);

    @POST
    @Path("updateUserBike")
    @Produces(MediaType.APPLICATION_JSON)
    Response updateUserBike(@HeaderParam("email") String email,
                         @HeaderParam("tokenAuth") String tokenAuth,
                            @HeaderParam("bike_id") String id,
                         @HeaderParam("bike_name") String bikeName,
                         @HeaderParam("bike_desc") String bikeDesc,
                         @HeaderParam("bike_amount") String bikeAmount,
                         @HeaderParam("bike_range") String bikeRange);

    @POST
    @Path("addServiceTask")
    @Produces(MediaType.APPLICATION_JSON)
    Response addServiceTask(@HeaderParam("email") String email,
                            @HeaderParam("tokenAuth") String tokenAuth,
                            @HeaderParam("task_name") String taskName);

    @POST
    @Path("serviceTaskDone")
    @Produces(MediaType.APPLICATION_JSON)
    Response markServiceTaskDone(@HeaderParam("email") String email,
                                 @HeaderParam("tokenAuth") String tokenAuth,
                                 @HeaderParam("task_id") String taskId,
                                 @HeaderParam("task_desc") String bikeDesc);


    @POST
    @Path("addUserTest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response addUserTest(JsonMessage<User> userJsonMessage);

    @POST
    @Path("addUserTestToken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response addUserTest_header(@HeaderParam("token") String token, User user);

    @GET
    @Path("isEmailFree")
    Response isEmailFree(@HeaderParam("email") String email);

    @GET
    @Path("addCountry")
    @Produces(MediaType.APPLICATION_JSON)
    Response addCountry(@Context UriInfo uriInfo);

    @GET
    @Path("addImage")
    @Produces(MediaType.APPLICATION_JSON)
    Response addImage();//TODO:


    @GET
    @Path("getImage/{id}")
    @Produces("image/png")
    Response getImage(@Context UriInfo uriInfo);


    @GET
    @Path("addBrand")
    @Produces(MediaType.TEXT_PLAIN)
    Response addBrand(@Context UriInfo uriInfo);

    @GET
    @Path("addManufacturer")
    @Produces(MediaType.TEXT_PLAIN)
    Response addManufacturer(@Context UriInfo uriInfo);


    @GET
    @Path("addBrandToManufacturer")
    @Produces(MediaType.TEXT_PLAIN)
    Response addBrandToManufacturer(@Context UriInfo uriInfo);

    @GET
    @Path("showBrands")
    @Produces(MediaType.TEXT_PLAIN)
    Response getManufactuer(@Context UriInfo uriInfo);

    @GET
    @Path("addBike")
    @Produces(MediaType.APPLICATION_JSON)
    Response addBike(@Context UriInfo uriInfo);

    @GET
    @Path("addPart")
    @Produces(MediaType.APPLICATION_JSON)
    Response addPart(@Context UriInfo uriInfo);

    @GET
    @Path("removeCountry")
    @Produces(MediaType.TEXT_PLAIN)
    Response removeCountry(@Context UriInfo uriInfo);

    @GET
    @Path("removeBike")
    @Produces(MediaType.TEXT_PLAIN)
    Response removeBike(@Context UriInfo uriInfo);

    @GET
    @Path("addRole")
    @Produces(MediaType.TEXT_PLAIN)
    Response addRole(@Context UriInfo uriInfo);

    @GET
    @Path("addEmployee")
    @Produces(MediaType.TEXT_PLAIN)
    Response addEmployee(@Context UriInfo uriInfo);

    @GET
    @Path("getEmployee")
    @Produces(MediaType.TEXT_PLAIN)
    Response getEmployee(@Context UriInfo uriInfo);

    @GET
    @Path("addShopDetails")
    @Produces(MediaType.TEXT_PLAIN)
    Response addShopDetails(@Context UriInfo uriInfo);

    @GET
    @Path("getShopDetails")
    @Produces(MediaType.TEXT_PLAIN)
    Response addShopDetails();

    @GET
    @Path("addTransaction")
    @Produces(MediaType.TEXT_PLAIN)
    Response addTransaction();

    @GET
    @Path("getTransaction/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    Response getTransaction(@Context UriInfo uriInfo);

    @GET
    @Path("removeTransaction/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    Response removeTransaction(@Context UriInfo uriInfo);




}
