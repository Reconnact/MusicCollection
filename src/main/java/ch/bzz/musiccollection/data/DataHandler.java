package ch.bzz.musiccollection.data;

import ch.bzz.musiccollection.model.Album;
import ch.bzz.musiccollection.model.Artist;
import ch.bzz.musiccollection.model.Song;
import ch.bzz.musiccollection.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */
public class DataHandler {
    private static List<Artist> artistList;
    private static List<Song> songList;
    private static List<Album> albumList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {

    }

    /**
     * reads all songs
     * @return list of songs
     */
    public static List<Song> readAllSongs() {
        return getSongList();
    }

    /**
     * reads a song its uuid
     * @param songUUID
     * @return the Song (null=not found)
     */
    public static Song readSongByUUID(String songUUID) {
        Song song = null;
        for (Song entry : getSongList()) {
            if (entry.getSongUUID().equals(songUUID)) {
                song = entry;
            }
        }
        return song;
    }

    /**
     * inserts a new song into the songList
     *
     * @param song the song to be saved
     */
    public static void insertSong(Song song) {
        getSongList().add(song);
        writeSongJSON();
    }

    /**
     * updates the songList
     */
    public static void updateSong() {
        writeSongJSON();
    }

    /**
     * deletes a song identified by the songUUID
     * @param songUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteSong(String songUUID) {
        Song song = readSongByUUID(songUUID);
        if (song != null) {
            getSongList().remove(song);
            writeSongJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all albums
     * @return list of albums
     */
    public static List<Album> readAllAlbums() {
        return getAlbumList();
    }

    /**
     * reads a album by its uuid
     * @param albumUUID
     * @return the album (null=not found)
     */
    public static Album readAlbumByUUID(String albumUUID) {
        Album album = null;
        for (Album entry : getAlbumList()) {
            if (entry.getAlbumUUID().equals(albumUUID)) {
                album = entry;
            }
        }
        return album;
    }

    /**
     * reads all artists
     * @return list of artists
     */
    public static List<Artist> readAllArtists() {
        return getArtistList();
    }

    /**
     * reads a artist by its uuid
     * @param artistUUID
     * @return the artist (null=not found)
     */
    public static Artist readArtistByUUID(String artistUUID) {
        Artist artist = null;
        for (Artist entry : getArtistList()) {
            if (entry.getArtistUUID().equals(artistUUID)) {
                artist = entry;
            }
        }
        return artist;
    }

    /**
     * reads the songs the JSON-file
     */
    private static void readSongJSON() {
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
     * writes the songList to the JSON-file
     */
    private static void writeSongJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String songPath = Config.getProperty("songJSON");
        try {
            fileOutputStream = new FileOutputStream(songPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getSongList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the albums from the JSON-file
     */
    private static void readAlbumJSON() {
        try {
            String path = Config.getProperty("albumJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
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
     * reads the artists the JSON-file
     */
    private static void readArtistJSON() {
        try {
            String path = Config.getProperty("artistJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Artist[] artists = objectMapper.readValue(jsonData, Artist[].class);
            for (Artist artist : artists) {
                getArtistList().add(artist);
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
    private static List<Song> getSongList() {
        if (songList == null) {
            setSongList(new ArrayList<>());
            readSongJSON();
        }
        return songList;
    }

    /**
     * sets songList
     *
     * @param songList the value to set
     */
    private static void setSongList(List<Song> songList) {
        DataHandler.songList = songList;
    }

    /**
     * gets albumList
     *
     * @return value of albumList
     */
    private static List<Album> getAlbumList() {
        if (albumList == null) {
            setAlbumList(new ArrayList<>());
            readAlbumJSON();
        }
        return albumList;
    }

    /**
     * sets albumList
     *
     * @param albumList the value to set
     */
    private static void setAlbumList(List<Album> albumList) {
        DataHandler.albumList = albumList;
    }

    /**
     * gets artistList
     *
     * @return value of artistList
     */
    private static List<Artist> getArtistList() {
        if (artistList == null) {
            setArtistList(new ArrayList<>());
            readArtistJSON();
        }
        return artistList;
    }

    /**
     * sets artistList
     *
     * @param artistList the value to set
     */
    private static void setArtistList(List<Artist> artistList) {
        DataHandler.artistList = artistList;
    }
}
