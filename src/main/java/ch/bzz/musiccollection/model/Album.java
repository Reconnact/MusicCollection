package ch.bzz.musiccollection.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * A song in the collection
 */
public class Album {
    private String albumUUID;
    private String title;
    private String releaseDate;
    private Artist artist;

    public String getAlbumUUID() {
        return albumUUID;
    }

    public void setAlbumUUID(String albumUUID) {
        this.albumUUID = albumUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
