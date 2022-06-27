package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Album;
import ch.bzz.musiccollection.model.Artist;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;
import java.util.UUID;

@Path("artist")
public class ArtistService {

    /**
     * function for showing all artists in JSON format
     * @return
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listArtists (
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        }
        List<Artist> artistsMap = DataHandler.readAllArtists();
        Response response = Response
                .status(httpStatus)
                .entity(artistsMap)
                .build();
        return response;
    }

    /**
     * function for showing one artist chosen by uuid in JSON format
     * @param artistUUID
     * @return
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response readArtist (
            @NotEmpty
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String artistUUID,
            @CookieParam("userRole") String userRole
    ){
        Artist artist = null;
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest")){
            httpStatus = 403;
        } else {
            try {
                UUID.fromString(artistUUID);
                artist = DataHandler.readArtistByUUID(artistUUID);
                if (artist == null) {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        }

        Response response = Response
                .status(httpStatus)
                .entity(artist)
                .build();
        return response;
    }

    /**
     * creates a new artist
     * important: the birthday has to follow the ISO_LOCAL_DATE format (yyyy-mm-dd)
     * @param artist
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertArtist(
            @Valid @BeanParam Artist artist,
            @FormParam("birthday") String birthday,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") ||userRole.equals("user")){
            httpStatus = 403;
        } else {
            artist.setArtistUUID(UUID.randomUUID().toString());
            artist.setBirthdayWithString(birthday);
            DataHandler.insertArtist(artist);
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }


    /**
     * updates existing artist
     * important: the birthday has to follow the ISO_LOCAL_DATE format (yyyy-mm-dd)
     * @param artist
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateArtist(
            @Valid @BeanParam Artist artist,
            @FormParam("birthday") String birthday,
            @CookieParam("userRole") String userRole
    ){
        int httpStatus = 200;
        Artist oldArtist = DataHandler.readArtistByUUID(artist.getArtistUUID());
        if (userRole == null || userRole.equals("guest") ||userRole.equals("user")){
            httpStatus = 403;
        } else {
            if (oldArtist != null) {
                oldArtist.setArtistName(artist.getArtistName());
                oldArtist.setBirthday(artist.getBirthday());
                oldArtist.setLastName(artist.getLastName());
                oldArtist.setFirstName(artist.getFirstName());
                oldArtist.setBirthdayWithString(birthday);
                DataHandler.updateArtist();
            } else {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes artist identified by its uuid
     * @param artistUUID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteArtist(
            @NotEmpty
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String artistUUID,
            @CookieParam("userRole") String userRole
            ){
        int httpStatus = 200;
        if (userRole == null || userRole.equals("guest") || userRole.equals("user")){
            httpStatus = 403;
        } else {
            if (!DataHandler.deleteArtist(artistUUID)) {
                httpStatus = 410;
            }
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
