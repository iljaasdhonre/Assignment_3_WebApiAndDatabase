package com.richieandmod.assignment_3_webapianddatabase.Models;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    public String name;

    @Size(max = 50)
    @Column(length = 50)
    public String alias;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Size(max = 200)
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
    public List<String> getMoviesList() {
        return movies.stream()
                .map(movie -> {
                    return "/api/movies/" + movie.id;
                }).collect(Collectors.toList());
    }
}
