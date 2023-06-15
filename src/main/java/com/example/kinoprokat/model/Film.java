package com.example.kinoprokat.model;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.Date;
import java.util.List;


@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Date data;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Genres> genres;
    private Integer ageRestriction;
    private String director;
    private String length;
    private String about;
    private String filename;


    public Film(String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about, String filename) {
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
    }

    public Film() {
    }

    public Film(String name, Date data, List<Genres> genres, Integer ageRestriction, String director, String length, String about) {
        this.name = name;
        this.data = data;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
        this.director = director;
        this.length = length;
        this.about = about;
        this.filename = filename;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getStrGenres() {

        return genres.toString().replace("[", "").replace("]", "");
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    // public List<User> getUserList() {
    //     return userList;
    // }
//
    // public void setUserList(List<User> userList) {
    //     this.userList = userList;
    // }
}
