package com.richieandmod.assignment_3_webapianddatabase.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

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

    @Column(length = 200)
    public String description;

    @OneToMany(mappedBy = "franchise", fetch = FetchType.LAZY)
    public List<Movie> movies = new ArrayList<>();

    @JsonGetter("movies")
    public List<String> getMoviesList(){
        return movies.stream()
                .map(movie -> {
                    return "/api/movie/" + movie.id;
                }).collect(Collectors.toList());
    }
}
