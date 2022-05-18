package ch.bzz.musiccollection.model;

import java.time.LocalDate;

/**
 * A artist who has zero to multiple albums
 */
public class Artist {
    private String artistUUID;
    private String firstName;
    private String lastName;
    private String artistName;
    private LocalDate birthday;

    public String getArtistUUID() {
        return artistUUID;
    }

    public void setArtistUUID(String artistUUID) {
        this.artistUUID = artistUUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
