package ch.bzz.musiccollection.model;

/**
 * A song in the collection
 */
public class Song {
    private String songUUID;
    private String title;
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
