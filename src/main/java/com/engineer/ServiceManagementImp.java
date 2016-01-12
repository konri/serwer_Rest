package com.engineer;

import com.engineer.authorize.AuthenticatorHelper;
import com.engineer.encryption.EncryptionUtils;
import com.engineer.enumeration.WeightEnum;
import com.engineer.exceptions.ErrorsEnum;
import com.engineer.hibernate.DatabaseService;
import com.engineer.hibernate.DatabaseServiceImp;
import com.engineer.model.*;

import javax.print.attribute.standard.Media;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import com.engineer.model.POJO.TransactionDetailsPOJO;
import com.engineer.model.POJO.UserPOJO;
import com.engineer.model.messageJson.JsonMessage;
import com.engineer.utilites.SendGCMNotification;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.jboss.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Konrad on 24.09.2015.
 */

public class ServiceManagementImp implements ServiceManagement{
    private final DatabaseService databaseService = new DatabaseServiceImp();


    @Override
    public Response addUser(@HeaderParam("name") String name,
                            @HeaderParam("email") String email,
                            @HeaderParam("password") String password) {
        email = email.toLowerCase();
        JSONObject jsonObject = new JSONObject();
        Logger.getLogger(this.getClass()).info("Registration: email is: " + email + " name: " + name);

        if (!databaseService.isEmailExistInDatabase(email)) {
            Logger.getLogger(this.getClass()).info("Registration continuing email is not existed in db");
            String [] name_surname = name.split(" ");
            User mUser;
            if(name_surname.length > 1) {
                mUser = databaseService.addUser(email, password, name_surname[0], name_surname[1], "Polska");
            } else {
                mUser = databaseService.addUser(email, password, name, null, "Polska");
            }

            if (mUser != null) {
                jsonObject.put("error", false);
                jsonObject.put("user", new UserPOJO(mUser));
            } else {
                jsonObject.put("error", true);
                jsonObject.put("error_code", ErrorsEnum.INTERNAL_ERROR.ordinal());
            }
        } else {
            jsonObject.put("error", true);
            jsonObject.put("error_code", ErrorsEnum.USER_IS_ALREADY_IN_DATABASE.ordinal());
            Logger.getLogger(this.getClass()).info("Registration Failed email is in a database: " + email);
        }
        Logger.getLogger(this.getClass()).info("Registration complete: " + jsonObject.toJSONString());

        return Response.ok(jsonObject,MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response logout(@HeaderParam("email") String email, @HeaderParam("token") String token) {
        JSONObject jsonObject = new JSONObject();
//        if (token is in list_token) {
        jsonObject.put("error", false);
//        list_token.remove(token);
//        } else {
//        jsonObject.put("error", true);
//        jsonObject.put("error_code", ErrorsEnum.INTERNAL_ERROR);

        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response remindPassword(@HeaderParam("email") String email) {
        JSONObject jsonObject = new JSONObject();
        if (databaseService.isEmailExistInDatabase(email)) {
            jsonObject.put("error", false);
        } else {
            jsonObject.put("error", true);
            jsonObject.put("error_code", ErrorsEnum.INTERNAL_ERROR);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addTokenToUser(@HeaderParam("email") String email,
                                   @HeaderParam("tokenGCM") String tokenGCM,
                                   @HeaderParam("tokenAuth") String tokenAuth) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            if (databaseService.addTokenToUser(email,tokenGCM) != null) {
                jsonObject.put("error", false);
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response getAllUserServicesTasks(@HeaderParam("email") String email,
                                            @HeaderParam("tokenAuth") String tokenAuth) {
        JSONArray jsonArray = new JSONArray();
        JSONObject isError = new JSONObject();

        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            List<ServiceTask> serviceTaskList = databaseService.getServiceTasks(email);
            if (serviceTaskList != null && serviceTaskList.size() > 0) {
                isError.put("error",false);
                jsonArray.add(isError);
                jsonArray.addAll(serviceTaskList);
            } else {
                isError.put("error",true);
                jsonArray.add(isError);
            }
        } else {
            isError.put("error", true);
            jsonArray.add(isError);
        }
        return Response.ok(jsonArray,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response getSpecificUserServiceTask(@HeaderParam("email") String email,
                                            @HeaderParam("task_id") String serviceTaskId,
                                            @HeaderParam("tokenAuth") String tokenAuth) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            ServiceTask serviceTask = null;
            if ( (serviceTask = databaseService.getServiceTask(Integer.parseInt(serviceTaskId))) != null) {
                jsonObject.put("error", false);
                jsonObject.put("service_task", serviceTask);
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response getAllUserBikes(@HeaderParam("email") String email,
                                    @HeaderParam("tokenAuth") String tokenAuth) {
        JSONArray jsonArray = new JSONArray();
        JSONObject isError = new JSONObject();

        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            List<UserBike> userBikeList = databaseService.getUserBikes(email);
            if (userBikeList != null && userBikeList.size() > 0) {
                JSONObject jsonObject = new JSONObject();
                isError.put("error",false);
                jsonArray.add(isError);
                jsonArray.addAll(userBikeList);
            } else {
                isError.put("error",true);
                jsonArray.add(isError);
            }
        } else {
            isError.put("error", true);
            jsonArray.add(isError);
        }
        return Response.ok(jsonArray,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response getUserBike(@HeaderParam("email") String email,
                                @HeaderParam("tokenAuth") String tokenAuth,
                                @HeaderParam("bike_id") String bikeId) {

        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            UserBike userBike = null;
            if ( (userBike = databaseService.getUserBike(Integer.parseInt(bikeId))) != null) {
                jsonObject.put("error", false);
                jsonObject.put("user_bike", userBike);
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addUserBike(@HeaderParam("email") String email,
                                @HeaderParam("tokenAuth") String tokenAuth,
                                @HeaderParam("bike_name") String bikeName,
                                @HeaderParam("bike_desc") String bikeDesc,
                                @HeaderParam("bike_amount") String bikeAmount,
                                @HeaderParam("bike_range") String bikeRange) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            UserBike userBike;
            if ((userBike = databaseService.addUserBike(email, bikeName, bikeDesc, Integer.parseInt(bikeAmount), Integer.parseInt(bikeRange))) != null) {
                jsonObject.put("error", false);
                jsonObject.put("id", userBike.getId());
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }


    @Override
    public Response updateUserBike(@HeaderParam("email") String email,
                                @HeaderParam("tokenAuth") String tokenAuth,
                                   @HeaderParam("bike_id") String id,
                                @HeaderParam("bike_name") String bikeName,
                                @HeaderParam("bike_desc") String bikeDesc,
                                @HeaderParam("bike_amount") String bikeAmount,
                                @HeaderParam("bike_range") String bikeRange) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            UserBike userBike;
            if ((userBike = databaseService.updateUserBike(Integer.parseInt(id), email, bikeName, bikeDesc, Integer.parseInt(bikeAmount), Integer.parseInt(bikeRange))) != null) {
                jsonObject.put("error", false);
                jsonObject.put("id", userBike.getId());
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addServiceTask(@HeaderParam("email") String email,
                                   @HeaderParam("tokenAuth") String tokenAuth,
                                   @HeaderParam("task_name") String taskName) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            ServiceTask serviceTask;
            if ((serviceTask = databaseService.addServiceTask(email, taskName, "Dodano do systemu.")) != null) {
                jsonObject.put("error", false);
                jsonObject.put("id_task", serviceTask.getId());

                JSONObject data = new JSONObject();
                data.put("task_id",serviceTask.getId());
                data.put("task_type","add");
                String token = serviceTask.user.getToken();
                new Thread(new SendGCMNotification(token,data)).start();
            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response markServiceTaskDone(@HeaderParam("email") String email,
                                        @HeaderParam("tokenAuth") String tokenAuth,
                                        @HeaderParam("task_id") String taskId,
                                        @HeaderParam("task_desc") String taskDesc) {
        JSONObject jsonObject = new JSONObject();
        if (AuthenticatorHelper.getInstance().isAuthTokenValid(email, tokenAuth)) {
            if (taskDesc == null || taskDesc.length() == 0) {
                taskDesc = null;
            }
            ServiceTask serviceTask;
            if ((serviceTask = databaseService.chagneServiceTask(Integer.parseInt(taskId), taskDesc)) != null) {
                jsonObject.put("error", false);
                jsonObject.put("serviceTask", serviceTask);
                JSONObject data = new JSONObject();
                data.put("task_id",serviceTask.getId());
                data.put("task_type","end");
                String token = serviceTask.user.getToken();
                new Thread(new SendGCMNotification(token,data)).start();

            } else {
                jsonObject.put("error",true);
            }
        } else {
            jsonObject.put("error", true);
        }
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();

    }

    @Override
    public Response addUserTest(JsonMessage<User> userJsonMessage) {
        Logger.getLogger(this.getClass()).info("username is: " + userJsonMessage.getUsername() + " token is: " + userJsonMessage.getToken());
        User mUser = databaseService.addUser(userJsonMessage.getObject());
        return Response.ok(mUser, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addUserTest_header(@HeaderParam("token") String token, User user) {
        Logger.getLogger(this.getClass()).info("token is: " + token);
        User mUser = databaseService.addUser(user);
        return Response.ok(mUser, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response isEmailFree(@HeaderParam("email") String email) {
        boolean checkEmailExsist = databaseService.checkEmailExsist(email.toLowerCase());
        Logger.getLogger(this.getClass()).info("email is: " + email + " check email is: " + checkEmailExsist);
        Response ret;
        if (!checkEmailExsist) {
            ret = Response.status(Response.Status.OK).build();
        } else {
            ret = Response.status(Response.Status.CONFLICT).build();
        }
        return ret;
    }


    @Override
    public Response addCountry(@Context UriInfo uriInfo) {
        String country = uriInfo.getQueryParameters().getFirst("country");
        Country country1 = databaseService.addCountry(country);
        return Response.ok(country1, MediaType.APPLICATION_JSON).build();
    }

    @Override
    public Response login(@HeaderParam("email") String email,
                          @HeaderParam("password") String password) {
        email = email.toLowerCase();
        User user = databaseService.isLoginPasswordCorrect(email,password);
        JSONObject jsonObject = new JSONObject();
        Logger.getLogger(this.getClass()).info("username is: " + email + " password is: " + password);

        if (user != null && user.getEmail() != null && user.getPassword() != null) {
            jsonObject.put("error", false);
            jsonObject.put("token", "defaultToken"); //todo: change this for auth
            jsonObject.put("user", new UserPOJO(user));
        } else if (user == null) {
            jsonObject.put("error", true);
            jsonObject.put("error_code", ErrorsEnum.NO_USER_IN_DATABASE.ordinal());
        }  else {
            jsonObject.put("error", true);
            jsonObject.put("error_code", ErrorsEnum.INTERNAL_ERROR.ordinal());
        }

        return Response.ok(jsonObject, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addImage() {

        File file = new File("/Users/Konrad/Dropbox/Workspace/javaEE/PostGetExample/src/main/resources/images/test.jpg");

        if (file == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        byte[] imageByte = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(imageByte);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageShopObject savedImage = databaseService.addImage("testowy", imageByte);
        return Response.ok("saved image id: " + savedImage.getId(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response getImage(@Context UriInfo uriInfo) {
        String idString =  uriInfo.getPathParameters().get("id").get(0);
        ImageShopObject imageShopObject = databaseService.getImage(Integer.parseInt(idString));
        if (imageShopObject != null) {
            Response.ResponseBuilder response = Response.ok(imageShopObject.getImageData());
            response.header("Content-Type: image/png",
                    "attachment; filename=img_server.png");
            return response.build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    @Override
    public Response addBrand(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
        Brand brand = databaseService.addBrand(name);
        return Response.ok(brand.getId() + " : " + brand.getName(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addManufacturer(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
//        String name, String address,
//                City city, String postcode, Country country,
//                String email, String telephone
        String address = uriInfo.getQueryParameters().getFirst("address");
        String city = uriInfo.getQueryParameters().getFirst("city");
        String postcode = uriInfo.getQueryParameters().getFirst("postcode");
        String country = uriInfo.getQueryParameters().getFirst("country");
        String email = uriInfo.getQueryParameters().getFirst("email"); //todo: validate email
        String telephone = uriInfo.getQueryParameters().getFirst("telephone"); // todo: validate telephone

        Set<Brand> brands = new HashSet<>();
        brands.add(databaseService.getBrand("KonradBrand"));
        brands.add(databaseService.getBrand("kornda"));

        Manufacturer manufacturer = databaseService.addManufactuer(name, address, city, postcode, country, email, telephone, brands);
        return Response.ok(manufacturer.getId() + " : " + manufacturer.getName(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addBrandToManufacturer(@Context UriInfo uriInfo) {
        String brandName = uriInfo.getQueryParameters().getFirst("brand");
        String manufacturerName = uriInfo.getQueryParameters().getFirst("manufacturer");
        Set<Brand> brands = new HashSet<>();
        brands.add(databaseService.getBrand("Nowa marka"));
        databaseService.addBrandsToManufactuer(3, brands);

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Override
    public Response getManufactuer(@Context UriInfo uriInfo) {
        String manufactuerName = uriInfo.getQueryParameters().getFirst("manufactuer");
        String manufactuerId = uriInfo.getQueryParameters().getFirst("id");
        Manufacturer manufacturer = null;
        if (manufactuerId != null)
            manufacturer = databaseService.getManufactuer(Integer.parseInt(manufactuerId));
        else if (manufactuerName != null)
            manufacturer = databaseService.getManufactuer(manufactuerName);

        String show = "";
        show += "manufactuer name: " + manufacturer.getName();
        for (Brand brand : manufacturer.getBrands()){
            show += " brand: + " + brand.getName() + "\n";
        }
        return Response.ok(show, MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addBike(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
        String description = uriInfo.getQueryParameters().getFirst("description");
        String barcode = uriInfo.getQueryParameters().getFirst("barcode");
        String brand = uriInfo.getQueryParameters().getFirst("brand");
        String price = uriInfo.getQueryParameters().getFirst("price");
        String bikeType = uriInfo.getQueryParameters().getFirst("bikeType");
        String weight = uriInfo.getQueryParameters().getFirst("weight");
        String vat = uriInfo.getQueryParameters().getFirst("vat");
        Integer mBrand = databaseService.getBrand(brand).getId();
        Integer mBikeType = databaseService.getBikeType(bikeType).getId();
        Integer mVat = databaseService.getVat(Integer.parseInt(vat)).getId();
        Bike bike = databaseService.addBike(name, description, barcode, mBrand, BigDecimal.valueOf(Double.parseDouble(price)), mVat, mBikeType, BigDecimal.valueOf(Double.parseDouble(weight)));
        String bikeDescription = bike.getName() + " brand is: " + bike.getBrand().getName();
        return Response.ok(bikeDescription, MediaType.APPLICATION_JSON_TYPE).build();
    }

    @Override
    public Response addPart(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
        String description = uriInfo.getQueryParameters().getFirst("description");
        String barcode = uriInfo.getQueryParameters().getFirst("barcode");
        String brand = uriInfo.getQueryParameters().getFirst("brand");
        String price = uriInfo.getQueryParameters().getFirst("price");
        String vat = uriInfo.getQueryParameters().getFirst("vat");
        String partType = uriInfo.getQueryParameters().getFirst("partType");
        Integer mBrand = databaseService.getBrand(brand).getId();
        Integer mPartType = databaseService.getPartType(partType).getId();
        Integer mVat = databaseService.getVat(Integer.parseInt(vat)).getId();
        Part part = databaseService.addPart(name, description, barcode, mBrand, BigDecimal.valueOf(Double.parseDouble(price)), mVat, mPartType);
        String partDescription = part.getName() + " brand is: " + part.getBrand().getName();
        return Response.ok(partDescription, MediaType.TEXT_PLAIN).build();
    }

    @Override
    public Response removeCountry(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
        Country country = databaseService.getCountry(name);
        Country deletedCountry = databaseService.removeCountry(country);
        return Response.ok(deletedCountry.getName() + " was deleted!", MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response removeBike(@Context UriInfo uriInfo) {
        String id = uriInfo.getQueryParameters().getFirst("id");
        Bike bike = databaseService.getBike(Integer.parseInt(id));
        Bike bikeDeleted = databaseService.removeBike(bike);
        return Response.ok("Bike id: " + bikeDeleted.getId() + " name: " + bikeDeleted.getName() + " brand: " + bikeDeleted.getBrand().getName() + " was deleted!", MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addRole(@Context UriInfo uriInfo) {
        String name = uriInfo.getQueryParameters().getFirst("name");
        String weight = uriInfo.getQueryParameters().getFirst("weight");
        WeightEnum weightEnum = WeightEnum.fromOrdinal(Integer.parseInt(weight));
        Role role = databaseService.addRole(name, weightEnum);
        return Response.ok(role.getName() + " wass added to database", MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addEmployee(@Context UriInfo uriInfo) {
        String login = uriInfo.getQueryParameters().getFirst("login");
        String password = uriInfo.getQueryParameters().getFirst("password");
        String name = uriInfo.getQueryParameters().getFirst("name");
        String surname = uriInfo.getQueryParameters().getFirst("surname");
        String address = uriInfo.getQueryParameters().getFirst("address");
        String postcode = uriInfo.getQueryParameters().getFirst("postcode");
        String city = uriInfo.getQueryParameters().getFirst("city");
        String country = uriInfo.getQueryParameters().getFirst("country");
        String email = uriInfo.getQueryParameters().getFirst("email");
        String telephone = uriInfo.getQueryParameters().getFirst("telephone");
        String roleId = uriInfo.getQueryParameters().getFirst("role_id");

        Role role = databaseService.getRole(Integer.parseInt(roleId));
        Employee employee = databaseService.addEmployee(login, password, name, surname, address, postcode, city, country, email, telephone, role);
        return Response.ok(employee.getLogin() + " : " + employee.getPassword(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response getEmployee(@Context UriInfo uriInfo) {
        String idEmploy = uriInfo.getQueryParameters().getFirst("id");
        Employee employee = databaseService.getEmployee(Integer.parseInt(idEmploy));
        String encodedPassword = EncryptionUtils.getInstance().getDecryptedPassword(employee.getPassword());
        return Response.ok(employee.getName() + " : " + employee.getLogin() + " : " + encodedPassword, MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addShopDetails(@Context UriInfo uriInfo) {

//        String name, String address, String postcode, String city, String country,
//                String telephone, String email, String regon, String nip
        String name = uriInfo.getQueryParameters().getFirst("name");
        String address = uriInfo.getQueryParameters().getFirst("address");
        String postcode = uriInfo.getQueryParameters().getFirst("postcode");
        String city = uriInfo.getQueryParameters().getFirst("city");
        String country = uriInfo.getQueryParameters().getFirst("country");
        String email = uriInfo.getQueryParameters().getFirst("email");
        String telephone = uriInfo.getQueryParameters().getFirst("telephone");
        String regon = uriInfo.getQueryParameters().getFirst("regon");
        String nip = uriInfo.getQueryParameters().getFirst("nip");
        ShopDetails shopDetails = databaseService.addShopDetails(name, address, postcode, city, country,
                                                                 email, telephone, regon, nip);
        return Response.ok(shopDetails.getName() + " address: " + shopDetails.getAddress(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addShopDetails() {
        ShopDetails shopDetails = databaseService.getShopDetails();
        return Response.ok(shopDetails.getName() + " address: " + shopDetails.getAddress() + " city: " +
                           shopDetails.getCity().getName(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response addTransaction() {
      Map<TransactionDetailsPOJO,ShopObject> orders = new HashMap<>();
        orders.put(new TransactionDetailsPOJO(10), databaseService.getBike(18));
        orders.put(new TransactionDetailsPOJO(5), databaseService.getPart(11));


        Double bruttoPrice = 0.0;
        Double nettoPrice = 0.0;
        Double vat = 0.0;
        Logger.getLogger(this.getClass()).info("before for:");

        for (Map.Entry<TransactionDetailsPOJO, ShopObject> entry : orders.entrySet()) {
            Logger.getLogger(this.getClass()).info("in for:");
            Logger.getLogger(this.getClass()).info("part: " + entry.getValue().getName());

            Double bruttoEntry = entry.getValue().getPrice().doubleValue();

            Logger.getLogger(this.getClass()).info(entry.getValue().getClass().toString());

            if(entry.getValue().getClass() == Part.class){
                Logger.getLogger(this.getClass()).info("jest true");
            }

            Logger.getLogger(this.getClass()).info("bruttoEntry: " + bruttoEntry);

            Double vatEntry = entry.getValue().getPrice().doubleValue() *
                              (entry.getValue().getVat().getValue().doubleValue() / 100) / (123 / 100);
            Logger.getLogger(this.getClass()).info("vatEntry: " + vatEntry);
            Double nettoEntry = entry.getValue().getPrice().doubleValue() - vatEntry;
            bruttoPrice += entry.getKey().getAmount() * bruttoEntry;
            nettoPrice += entry.getKey().getAmount() * nettoEntry;
            vat += entry.getKey().getAmount() * vatEntry;

        }

//        DecimalFormat df = new DecimalFormat("#.##");
//        bruttoPrice = Double.valueOf(df.format(bruttoPrice));
//        nettoPrice = Double.valueOf(df.format(nettoPrice));
        Logger.getLogger(this.getClass()).info("vatAll: " + vat);
//        vat = Double.valueOf(df.format(vat));
//        Integer idEmployee, BigDecimal netto, BigDecimal brutto,
//                BigDecimal give, BigDecimal change, Map<Integer, ShopObject> orders
        TransactionModule transactionModule = databaseService.addTransaction(2, nettoPrice, vat, bruttoPrice, 250.0, 250 - bruttoPrice, orders);
        return Response.ok("id: " + transactionModule.getId() + "brutto: " + transactionModule.getBrutto() + " netto: " + transactionModule.getNetto(), MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response getTransaction(@Context UriInfo uriInfo) {
        String idString =  uriInfo.getPathParameters().get("id").get(0);
        TransactionModule transactionModule = databaseService.getTransaction(Integer.parseInt(idString));
        Map<TransactionDetails, ShopObject> objects = transactionModule.getOrders();
        String orders = "";
        for (Map.Entry<TransactionDetails, ShopObject> entry : objects.entrySet()){
            orders += entry.getKey().getAmount() + " * object: " + entry.getValue().getName() + "\n";
        }
        return Response.ok(orders, MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Override
    public Response removeTransaction(@Context UriInfo uriInfo) {
        String idString =  uriInfo.getPathParameters().get("id").get(0);
        TransactionModule transactionModule = databaseService.removeTransaction(Integer.parseInt(idString));
        for (Map.Entry<TransactionDetails, ShopObject> entry : transactionModule.getOrders().entrySet()) {
//            entry.getValue().addAmount(entry.getKey().getAmount());
            Logger.getLogger(this.getClass()).info("Object: " + entry.getValue().getName() + " add amount: " + entry.getKey().getAmount());
        }
        return Response.ok("Transaction id: " + transactionModule.getId() + " was deleted", MediaType.TEXT_PLAIN_TYPE ).build();

    }


}
