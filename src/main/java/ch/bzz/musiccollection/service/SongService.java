package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Song;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
            List<Song> songMap = DataHandler.getInstance().readAllSongs();
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
            song =  DataHandler.getInstance().readSongByUUID(songUUID);
            if (song == null){
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalAccessError argEx){
            httpStatus = 400;
        }

        Response response = Response
                .status(httpStatus)
                .entity(song)
                .build();
        return response;
    }
}
