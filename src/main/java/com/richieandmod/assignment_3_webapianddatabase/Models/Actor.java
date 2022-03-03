package com.richieandmod.assignment_3_webapianddatabase.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "actor")
public class Actor {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, length = 50)
    public String name;

    @Column(length = 50)
    public String alias;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Column(length = 200)
    public String picture;

    //relation with movie
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "actor_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    public List<Movie> movies = new ArrayList<>();

    //show relation and link to movie by id and title
    @JsonGetter("movies")
    public List<String> getMovies(){
        return movies.stream()
                .map(movie -> {
                    return "/api/movies/" + movie.id + ',' + movie.movieTitle;
                }).collect(Collectors.toList());
    }
}
