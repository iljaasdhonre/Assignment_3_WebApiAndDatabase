package com.richieandmod.assignment_3_webapianddatabase.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "franchise")
public class Franchise {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, length = 50)
    public String name;

    @Column(length = 600)
    public String description;

    //relation with movie
    @OneToMany(mappedBy = "franchise", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Movie> movies = getMovies();

    //show relation and link to movie by id and title
    @JsonGetter("movies")
    public List<String> getMoviesList() {
        return movies.stream()
                .map(movie -> {
                    return "/api/movies/" + movie.id + ',' + movie.movieTitle;
                }).collect(Collectors.toList());
    }

    //getters and setters
    public List<Movie> getMovies() { return movies; }

    public void setMovies(List<Movie> movies) { this.movies = movies; }
}
