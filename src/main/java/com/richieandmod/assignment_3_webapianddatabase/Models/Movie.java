package com.richieandmod.assignment_3_webapianddatabase.Models;


import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(name="movieTitle")
    private String movieTitle;

    @Column(name="genre")
    private String genre;

    @Column(name="releaseYear")
    private Integer releaseYear;

    @Column(name="picture")
    private String picture;

    @Column(name="trailer")
    private String trailer;
}
