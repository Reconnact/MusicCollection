package ch.bzz.musiccollection.model;

import ch.bzz.musiccollection.util.LocalDateDeserializer;
import ch.bzz.musiccollection.util.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * A artist who has zero to multiple albums
 */
public class Artist {
    private String artistUUID;
    private String firstName;
    private String lastName;
    private String artistName;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthday;

    /**
     * gets artistUUID
     * @return
     */
    public String getArtistUUID() {
        return artistUUID;
    }

    /**
     * sets artistUUID
     * @param artistUUID
     */
    public void setArtistUUID(String artistUUID) {
        this.artistUUID = artistUUID;
    }

    /**
     * gets firstName
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets lastName
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets lastName
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets artistName
     * @return
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * sets artistName
     * @param artistName
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * gets birthday
     * @return
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * sets birthday with a LinkedHashMap
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
       this.birthday = birthday;
    }
}
