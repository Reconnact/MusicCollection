package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.UserData;
import ch.bzz.musiccollection.model.User;

import javax.ws.rs.*;
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
        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return  response;

    }

    @DELETE
    @Path("logout")
    @Produces
    public Response logout()
    {
        Response response = Response
                .status(200)
                .entity("")
                .build();
        return  response;

    }
}
