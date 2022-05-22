package ch.bzz.musiccollection.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * A song in the collection
 */
public class Album {
    private String albumUUID;
    private String title;
    private LocalDate releaseDate;
    private Artist artist;

    /**
     * gets albumUUID
     * @return
     */
    public String getAlbumUUID() {
        return albumUUID;
    }

    /**
     * sets albumUUID
     * @param albumUUID
     */
    public void setAlbumUUID(String albumUUID) {
        this.albumUUID = albumUUID;
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
     * gets releaseDate
     * @return
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * sets releaseDate with a LinkedHashMap
     * @param releaseDate
     */
    public void setReleaseDate (LinkedHashMap<String, Integer> releaseDate) {
        this.releaseDate = LocalDate.of(releaseDate.get("year"), releaseDate.get("month"), releaseDate.get("day"));
    }

    /**
     * gets artist
     * @return
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * sets artist
     * @param artist
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
