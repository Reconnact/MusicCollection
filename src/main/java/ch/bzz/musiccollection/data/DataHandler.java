package ch.bzz.musiccollection.data;

import ch.bzz.musiccollection.model.Album;
import ch.bzz.musiccollection.model.Song;
import ch.bzz.musiccollection.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    //TODO: ARTIST
    private static DataHandler instance = null;
    private List<Song> songList;
    private List<Album> albumList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setSongList(new ArrayList<>());
        readAlbumJSON();
        setSongList(new ArrayList<>());
        readSongJSON();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }

    /**
     * reads all songs
     * @return list of songs
     */
    public List<Song> readAllSongs() {
        return getSongList();
    }

    /**
     * reads a song its uuid
     * @param songUUID
     * @return the Song (null=not found)
     */
    public Song readSongByUUID(String songUUID) {
        Song song = null;
        for (Song entry : getSongList()) {
            if (entry.getSongUUID().equals(songUUID)) {
                song = entry;
            }
        }
        return song;
    }

    /**
     * reads all albums
     * @return list of albums
     */
    public List<Album> readAllAlbums() {

        return getAlbumList();
    }

    /**
     * reads a album by its uuid
     * @param albumUUID
     * @return the album (null=not found)
     */
    public Album readAlbumByUUID(String albumUUID) {
        Album album = null;
        for (Album entry : getAlbumList()) {
            if (entry.getAlbumUUID().equals(albumUUID)) {
                album = entry;
            }
        }
        return album;
    }

    /**
     * reads the songs the JSON-file
     */
    private void readSongJSON() {
        try {
            String path = Config.getProperty("songJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Song[] songs = objectMapper.readValue(jsonData, Song[].class);
            for (Song song : songs) {
                getSongList().add(song);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the albums from the JSON-file
     */
    private void readAlbumJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("albumJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Album[] albums = objectMapper.readValue(jsonData, Album[].class);
            for (Album album : albums) {
                getAlbumList().add(album);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * gets songList
     *
     * @return value of songList
     */
    private List<Song> getSongList() {
        return songList;
    }

    /**
     * sets songList
     *
     * @param songList the value to set
     */
    private void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    /**
     * gets albumList
     *
     * @return value of albumList
     */
    private List<Album> getAlbumList() {
        return albumList;
    }

    /**
     * sets albumList
     *
     * @param albumList the value to set
     */
    private void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }
}
