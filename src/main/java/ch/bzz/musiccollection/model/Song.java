package ch.bzz.musiccollection.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * A song in the collection
 */
public class Song {
    @FormParam("songUUID")
    @Pattern(regexp= "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String songUUID;

    @FormParam("title")
    @NotEmpty
    @Size(min=1, max=40)
    private String title;

    @FormParam("length")
    @NotEmpty
    private String length;

    /**
     * gets songUUID
     * @return
     */
    public String getSongUUID() {
        return songUUID;
    }

    /**
     * sets songUUID
     * @param songUUID
     */
    public void setSongUUID(String songUUID) {
        this.songUUID = songUUID;
    }

    /**
     * gets title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets length
     * @return
     */
    public String getLength() {
        return length;
    }

    /**
     * sets length
     * @param length
     */
    public void setLength(String length) {
        this.length = length;
    }
}
