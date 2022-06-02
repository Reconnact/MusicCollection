package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Album;

import ch.bzz.musiccollection.model.Song;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
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
        List<Album> albumMap = DataHandler.readAllAlbums();
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
    public  Response readAlbum (
            @NotEmpty
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String albumUUID
    ){
        int httpStatus = 200;
        Album album =  DataHandler.readAlbumByUUID(albumUUID);
        if (album == null) {
            httpStatus = 410;
        }

        Response response = Response
                .status(httpStatus)
                .entity(album)
                .build();
        return response;
    }

    /**
     * creates new album
     * important: the releaseDate has to follow the ISO_LOCAL_DATE format (yyyy-mm-dd)
     * @param title
     * @param releaseDate
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertAlbum(
            @FormParam("title") String title,
            @FormParam("releaseDate") String releaseDate
    ){
        Album album = new Album();
        album.setAlbumUUID(UUID.randomUUID().toString());
        album.setTitle(title);
        //album.setReleaseDate(LocalDate.parse(releaseDate));
        DataHandler.insertAlbum(album);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates existing album
     * important: the releaseDate has to follow the ISO_LOCAL_DATE format (yyyy-mm-dd)
     * @param albumUUID
     * @param title
     * @param releaseDate
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAlbum(
            @FormParam("albumUUID") String albumUUID,
            @FormParam("title") String title,
            @FormParam("releaseDate") String releaseDate
    ){
        int httpStatus = 200;
        Album album = DataHandler.readAlbumByUUID(albumUUID);
        if (album != null){
            album.setTitle(title);
            //album.setReleaseDate(LocalDate.parse(releaseDate));

            DataHandler.updateAlbum();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes an album identified by its uuid
     * @param albumUUID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAlbum(
            @QueryParam("uuid") String albumUUID
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteAlbum(albumUUID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
