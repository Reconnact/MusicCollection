package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Song;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        @NotEmpty
        @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
        @QueryParam("uuid") String songUUID
    ){
        int httpStatus = 200;
        Song song =  DataHandler.readSongByUUID(songUUID);
        if (song == null) {
            httpStatus = 410;
        }

        Response response = Response
                .status(httpStatus)
                .entity(song)
                .build();
        return response;
    }

    /**
     * creates a new song
     * @param song
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertSong(
            @Valid @BeanParam Song song
    ){
        song.setSongUUID(UUID.randomUUID().toString());
        DataHandler.insertSong(song);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates an existing song
     * @param song
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateSong(
            @Valid @BeanParam Song song
    ){
        int httpStatus = 200;
        Song oldSong = DataHandler.readSongByUUID(song.getSongUUID());
        if (oldSong != null) {
            oldSong.setTitle(song.getTitle());
            oldSong.setLength(song.getLength());
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
            @NotEmpty
            @Pattern(regexp= "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
