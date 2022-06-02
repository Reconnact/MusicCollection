package ch.bzz.musiccollection.service;

import ch.bzz.musiccollection.data.DataHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * test service
 */
@Path("test")
public class TestService {

    /**
     * test
     * @return
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Test erfolgreich")
                .build();   
    }

    /**
     * restores the json-files
     * @return Response
     */
    @GET
    @Path("restore")
    @Produces(MediaType.TEXT_PLAIN)
    public Response restore() {
        try {
            java.nio.file.Path path = Paths.get(Config.getProperty("songJSON"));
            String filename = path.getFileName().toString();
            String folder = path.getParent().toString();

            byte[] songJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            FileOutputStream fileOutputStream = new FileOutputStream(Config.getProperty("songJSON"));
            fileOutputStream.write(songJSON);

            path = Paths.get(Config.getProperty("albumJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] albumJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("albumJSON"));
            fileOutputStream.write(albumJSON);

            path = Paths.get(Config.getProperty("artistJSON"));
            filename = path.getFileName().toString();
            folder = path.getParent().toString();

            byte[] artistJSON = Files.readAllBytes(Paths.get(folder, "backup", filename));
            fileOutputStream = new FileOutputStream(Config.getProperty("artistJSON"));
            fileOutputStream.write(artistJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataHandler.initLists();
        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}
