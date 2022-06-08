package ch.bzz.musiccollection.model;

import ch.bzz.musiccollection.util.LocalDateDeserializer;
import ch.bzz.musiccollection.util.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.util.LinkedHashMap;

/**
 * A artist who has zero to multiple albums
 */
public class Artist {
    @FormParam("bookUUID")
    @Pattern(regexp= "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String artistUUID;

    @FormParam("firstName")
    @NotEmpty
    private String firstName;

    @FormParam("lastName")
    @NotEmpty
    private String lastName;

    @FormParam("artistName")
    @NotEmpty
    private String artistName;

    @FormParam("birthday")
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
