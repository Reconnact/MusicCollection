package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Album;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
     * @param album
     * @param artistUUID
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertAlbum(
            @Valid @BeanParam Album album,
            @Pattern(regexp= "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("artistUUID") String artistUUID,
            @FormParam("releaseDate") String releaseDate,
            @FormParam("songUUIDList") List<String> songUUIDList
    ){
        album.setAlbumUUID(UUID.randomUUID().toString());
        album.setArtistUUID(artistUUID);
        album.setReleaseDateWithString(releaseDate);
        album.setSongUUIDListWithList(songUUIDList);
        DataHandler.insertAlbum(album);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates existing album
     * important: the releaseDate has to follow the ISO_LOCAL_DATE format (yyyy-mm-dd)
     * @param album
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateAlbum(
            @Valid @BeanParam Album album,
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("artistUUID") String artistUUID,
            @FormParam("releaseDate") String releaseDate,
            @FormParam("songUUIDList") List<String> songUUIDList
    ){
        int httpStatus = 200;
        Album oldAlbum = DataHandler.readAlbumByUUID(album.getAlbumUUID());
        if (oldAlbum != null) {
            oldAlbum.setTitle(album.getTitle());
            oldAlbum.setArtistUUID(artistUUID);
            oldAlbum.setSongList(album.getSongList());
            oldAlbum.setReleaseDateWithString(releaseDate);
            oldAlbum.setSongUUIDListWithList(songUUIDList);
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
            @NotEmpty
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
