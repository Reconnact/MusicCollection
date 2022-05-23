package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Artist;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response listArtists () {
        List<Artist> artistsMap = DataHandler.getInstance().readAllArtists();
        Response response = Response
                .status(200)
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
    public  Response readSong (
            @QueryParam("uuid") String artistUUID
    ){
        Artist artist = null;
        int httpStatus;

        try {
            UUID.fromString(artistUUID);
            artist =  DataHandler.getInstance().readArtistByUUID(artistUUID);
            if (artist == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argEx){
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(artist)
                .build();
        return response;
    }
}
