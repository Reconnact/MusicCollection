package ch.bzz.musiccollection.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * A album of one or multiple songs
 */
public class Album {
    private String albumUUID;
    private String title;
    private LocalDate releaseDate;
    private List<Song> songList;
    private Artist artist;

    /**
     * get albumUUID
     *
     * @return
     */
    public String getAlbumUUID() {
        return albumUUID;
    }

    /**
     * sets albumUUID
     *
     * @param albumUUID
     */
    public void setAlbumUUID(String albumUUID) {
        this.albumUUID = albumUUID;
    }

    /**
     * gets title
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets release date
     *
     * @return
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * sets release date
     *
     * @param releaseDate
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * gets song list
     *
     * @return
     */
    public List<Song> getSongList() {
        return songList;
    }

    /**
     * sets song list
     *
     * @param songList
     */
    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    /**
     * adds Song to songList
     *
     * @param song
     */
    public void addSong(Song song){
        songList.add(song);
    }

    /**
     * gets artist
     *
     * @return
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * sets artist
     *
     * @param artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
