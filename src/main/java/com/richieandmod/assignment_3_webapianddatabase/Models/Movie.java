package com.richieandmod.assignment_3_webapianddatabase.Models;


import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, length = 50)
    public String movieTitle;

    @Column(nullable = false, length = 50)
    public String genre;

    @Column(nullable = false)
    public Integer releaseYear;

    @Column(length = 200)
    public String picture;

    @Column(length = 200)
    public String trailer;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    public Franchise franchise;

    @JsonGetter("franchise")
    public String getFranchise() {
        if (franchise != null) {
            return "/api/franchise/" + franchise.id;
        } else {
            return null;
        }
    }

    @ManyToMany
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    public List<Actor> actors;

    @JsonGetter("actors")
    public List<String> getActors() {
        return actors.stream()
                .map(actor -> {
                    return "/api/actor/" + actor.id;
                }).collect(Collectors.toList());
    }
}
