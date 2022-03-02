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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, length = 50)
    public String name;

    @Column(length = 600)
    public String description;

    @OneToMany(mappedBy = "franchise", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Movie> movies = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> getMoviesList() {
        return movies.stream()
                .map(movie -> {
                    return "/api/movies/" + movie.id + ',' + movie.movieTitle;
                }).collect(Collectors.toList());
    }
}
