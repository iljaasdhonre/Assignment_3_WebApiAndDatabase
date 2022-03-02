package com.richieandmod.assignment_3_webapianddatabase.Models;


import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import java.util.ArrayList;
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


    @ManyToOne()
    @JoinTable(
            name = "franchise_movies",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "franchise_id")}
    )
    public Franchise franchise;

    @JsonGetter("franchise")
    public String getFranchise() {
        if (franchise != null) {
            return "/api/franchises/" + franchise.id + ',' + franchise.name;
        } else {
            return null;
        }
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<Actor> actors = getActors();

    @JsonGetter("actors")
    public List<String> getActorsList() {
        return actors.stream()
                .map(actor -> {
                    return "/api/actors/" + actor.id;
                }).collect(Collectors.toList());
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
