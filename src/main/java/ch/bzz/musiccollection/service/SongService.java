package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.model.Song;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * service for reading, changing and deleting songs
 */
@Path("song")
public class SongService {

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks () {
        List<Song> songMap = DataHandler.getInstance().readAllSongs();
        Response response = Response
                .status(200)
                .entity(songMap)
                .build();
        return response;
    }
}
