package ch.bzz.musiccollection.model;

/**
 * A song in the collection
 */
public class Song {
    private String songUUID;
    private String title;
    private String length;

    public String getSongUUID() {
        return songUUID;
    }

    public void setSongUUID(String songUUID) {
        this.songUUID = songUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}
