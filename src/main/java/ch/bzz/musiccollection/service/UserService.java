package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.UserData;
import ch.bzz.musiccollection.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * services for authentication and authorization of users
 */
@Path("user")
public class UserService {

    @POST
    @Path("login")
    @Produces
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password
    )
    {
        int httpStatus;
        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }

        NewCookie roleCookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );

        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(roleCookie)
                .build();
        return  response;

    }

    @DELETE
    @Path("logoff")
    @Produces
    public Response login()
    {
        NewCookie roleCookie = new NewCookie(
                "",
                "",
                "/",
                "",
                "Logout-Cookie",
                1,
                false
        );

        Response response = Response
                .status(200)
                .entity("")
                .header("Access-Control-Allow-Origin", "*")
                .cookie(roleCookie)
                .build();
        return  response;

    }
}