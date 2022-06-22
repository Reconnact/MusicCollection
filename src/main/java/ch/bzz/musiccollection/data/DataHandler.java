package ch.bzz.musiccollection.data;

import ch.bzz.musiccollection.model.Album;
import ch.bzz.musiccollection.model.Artist;
import ch.bzz.musiccollection.model.Song;
import ch.bzz.musiccollection.model.User;
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
    private static List<User> userList;


    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {

    }

    /**
     * initializes the lists
     */
    public static void initLists() {
        DataHandler.setAlbumList(null);
        DataHandler.setSongList(null);
        DataHandler.setArtistList(null);
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
     * inserts a new album into the albumList
     *
     * @param album the album to be saved
     */
    public static void insertAlbum(Album album) {
        getAlbumList().add(album);
        writeAlbumJSON();
    }

    /**
     * updates the artistList
     */
    public static void updateAlbum() {
        writeAlbumJSON();
    }

    /**
     * deletes an album identified by the albumUUID
     * @param albumUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteAlbum(String albumUUID) {
        Album album = readAlbumByUUID(albumUUID);
        if (album != null) {
            getAlbumList().remove(album);
            writeAlbumJSON();
            return true;
        } else {
            return false;
        }
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
     * inserts a new artist into the artistList
     *
     * @param artist the artist to be saved
     */
    public static void insertArtist(Artist artist) {
        getArtistList().add(artist);
        writeArtistJSON();
    }

    /**
     * updates the artistList
     */
    public static void updateArtist() {
        writeArtistJSON();
    }

    /**
     * deletes an artist identified by the artistUUID
     * @param artistUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteArtist(String artistUUID) {
        Artist artist = readArtistByUUID(artistUUID);
        if (artist != null) {
            getArtistList().remove(artist);
            writeArtistJSON();
            return true;
        } else {
            return false;
        }
    }

    public String readUserRole(String username, String password) {
        for (User user : getUserList()) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                return user.getUsername();
            }
        }
        return "guest";
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
     * writes the albumList to the JSON-file
     */
    private static void writeAlbumJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String albumPath = Config.getProperty("albumJSON");
        try {
            fileOutputStream = new FileOutputStream(albumPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getAlbumList());
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
     * writes the artistList to the JSON-file
     */
    private static void writeArtistJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String artistPath = Config.getProperty("artistJSON");
        try {
            fileOutputStream = new FileOutputStream(artistPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getArtistList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the users from the JSON-file
     */
    private static void readUserJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("userJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(jsonData, User[].class);
            for (User user : users) {
                getUserList().add(user);
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

    /**
     * gets userList
     *
     * @return value of userList
     */

    public static List<User> getUserList() {
        if (DataHandler.userList == null) {
            DataHandler.setUserList(new ArrayList<>());
            readUserJSON();
        }
        return userList;
    }

    /**
     * sets userList
     *
     * @param userList the value to set
     */

    public static void setUserList(List<User> userList) {
        DataHandler.userList = userList;
    }
}
