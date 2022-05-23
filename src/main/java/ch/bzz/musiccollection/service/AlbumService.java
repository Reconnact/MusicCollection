package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Album;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * service for reading, changing and deleting albums
 */
@Path("album")
public class AlbumService {

    /**
     * function for showing all albums in JSON format
     * @return
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAlbums () {
        List<Album> albumMap = DataHandler.getInstance().readAllAlbums();
        Response response = Response
                .status(200)
                .entity(albumMap)
                .build();
        return response;
    }

    /**
     * function for showing one album chosen by uuid in JSON format
     * @param albumUUID
     * @return
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response readSong (
            @QueryParam("uuid") String albumUUID
    ){
        Album album = null;
        int httpStatus;

        try {
            UUID.fromString(albumUUID);
            album =  DataHandler.getInstance().readAlbumByUUID(albumUUID);
            if (album == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argEx){
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(album)
                .build();
        return response;
    }
}
