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

    @Column
    public String movieTitle;

    @Column
    public String genre;

    @Column
    public Integer releaseYear;

    @Column
    public String picture;

    @Column
    public String trailer;
}
