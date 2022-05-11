package ch.bzz.musiccollection.model;

import java.time.LocalDate;
import java.util.List;

/**
 * A album of one or multiple songs
 */
public class Album {
    private String albumUUID;
    private String title;
    private LocalDate releaseDate;
    private List<Song> songList;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
