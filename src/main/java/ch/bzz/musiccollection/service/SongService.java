package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Song;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * service for reading, changing and deleting songs
 */
@Path("song")
public class SongService {

    /**
     * function for showing all songs in JSON format
     * @return
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listSongs () {
            List<Song> songMap = DataHandler.readAllSongs();
            Response response = Response
                    .status(200)
                    .entity(songMap)
                    .build();
            return response;
    }

    /**
     * function for showing one song chosen by uuid in JSON format
     * @param songUUID
     * @return
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response readSong (
        @QueryParam("uuid") String songUUID
    ){
        Song song = null;
        int httpStatus;

        try {
            UUID.fromString(songUUID);
            song =  DataHandler.readSongByUUID(songUUID);
            if (song == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argEx){
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(song)
                .build();
        return response;
    }

    /**
     * creates a new song
     * @param title
     * @param length
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertSong(
            @FormParam("title") String title,
            @FormParam("length") String length
    ){
        Song song = new Song();
        song.setSongUUID(UUID.randomUUID().toString());
        song.setTitle(title);
        song.setLength(length);

        DataHandler.insertSong(song);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates an existing song
     * @param songUUID
     * @param title
     * @param length
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSong(
            @FormParam("songUUID") String songUUID,
            @FormParam("title") String title,
            @FormParam("length") String length
    ){
        int httpStatus = 200;
        Song song = DataHandler.readSongByUUID(songUUID);
        if (song != null){
            song.setTitle(title);
            song.setLength(length);

            DataHandler.updateSong();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a song identified by its uuid
     * @param songUUID
     * @return
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteSong(
            @QueryParam("uuid") String songUUID
    ){
        int httpStatus = 200;
        if (!DataHandler.deleteSong(songUUID)){
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}
