package ch.bzz.musiccollection.model;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.util.LocalDateDeserializer;
import ch.bzz.musiccollection.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.*;

/**
 * A song in the collection
 */
public class Album {
    private String albumUUID;
    private String title;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate releaseDate;
    private ArrayList<Song> songList = new ArrayList<Song>();
    @JsonIgnore
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
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSongUUIDList(ArrayList<String> songUUIDList){
        ListIterator<String> iterator = songUUIDList.listIterator();
        while (iterator.hasNext()){
            Song song = DataHandler.readSongByUUID(iterator.next());
            songList.add(song);
        }
    }

    /**
     * gets songList
     * @return
     */
    public ArrayList<Song> getSongList() {
        return songList;
    }

    /**
     * sets songList
     * @param songList
     */
    public void setSongList(ArrayList<Song> songList) {
        this.songList = songList;
    }

    public void setArtistUUID(String artistUUID){
        setArtist(new Artist());
        Artist artist = DataHandler.readArtistByUUID(artistUUID);
        this.artist = artist;
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
