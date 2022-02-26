package com.richieandmod.assignment_3_webapianddatabase.Models;


import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer movieId;

    @Column(nullable=false, length=50)
    public String movieTitle;

    @Column(nullable=false, length=50)
    public String genre;

    @Column(nullable=false)
    public Integer releaseYear;

    @Column(length=200)
    public String picture;

    @Column(length=200)
    public String trailer;
}
