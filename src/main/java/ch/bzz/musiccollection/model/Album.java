package ch.bzz.musiccollection.model;

import ch.bzz.musiccollection.data.DataHandler;
import ch.bzz.musiccollection.util.LocalDateDeserializer;
import ch.bzz.musiccollection.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.util.*;

/**
 * A song in the collection
 */
public class Album {
    @FormParam("albumUUID")
    @Pattern(regexp= "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String albumUUID;

    @FormParam("title")
    @NotEmpty
    @Size(min=1, max=40)
    private String title;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate releaseDate;

    @JsonIgnore
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
     * sets releaseDate
     * @param releaseDate
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * sets the releasedate with a String
     * @param releaseDate
     */
    public void setReleaseDateWithString(String releaseDate) {
        this.releaseDate = LocalDate.parse(releaseDate);
    }

    /**
     * sets the songList with a ArrayList with UUIDs
     * @param songUUIDList
     */
    public void setSongUUIDList(ArrayList<String> songUUIDList){
        ListIterator<String> iterator = songUUIDList.listIterator();
        while (iterator.hasNext()){
            Song song = DataHandler.readSongByUUID(iterator.next());
            songList.add(song);
        }
    }

    /**
     * gets songUUIDList (ArrayList of the songUUIDs)
     * @return
     */
    public ArrayList<String> getSongUUIDList(){
        ArrayList<String> songUUIDList = new ArrayList<>();
        ListIterator<Song> iterator = songList.listIterator();
        while (iterator.hasNext()){
            String songUUID = iterator.next().getSongUUID();
            songUUIDList.add(songUUID);
        }
        return songUUIDList;
    }

    /**
     * sets songUUIList (ArrayList) with a List
     * @param songUUIDList
     */
    public void setSongUUIDListWithList(List<String> songUUIDList){
        ArrayList<String> arrayList = new ArrayList<>();
        ListIterator<String> iterator = songUUIDList.listIterator();
        while (iterator.hasNext()){
            arrayList.add(iterator.next());
        }
        setSongUUIDList(arrayList);
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

    public String getArtistUUID(){
        if (getArtist()== null) return null;
        return getArtist().getArtistUUID();
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
