package com.richieandmod.assignment_3_webapianddatabase.Models;


import com.fasterxml.jackson.annotation.JsonGetter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "movie")
public class Movie {

    //fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    public String movieTitle;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    public String genre;

    @NotBlank
    @Size(max = 4)
    @Column(nullable = false)
    public Integer releaseYear;

    @Size(max = 200)
    @Column(length = 200)
    public String picture;

    @Size(max = 200)
    @Column(length = 200)
    public String trailer;

    //relation with franchise
    @ManyToOne()
    @JoinTable(
            name = "franchise_movies",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "franchise_id")}
    )
    public Franchise franchise;

    //show relation and link to franchise by id and name
    @JsonGetter("franchise")
    public String getFranchise() {
        if (franchise != null) {
            return "/api/franchises/" + franchise.id + ',' + franchise.name;
        } else {
            return null;
        }
    }

    //relation with actor
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "actor_movie",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<Actor> actors = getActors();

    //show relation and link to actor by id
    @JsonGetter("actors")
    public List<String> getActorsList() {
        return actors.stream()
                .map(actor -> {
                    return "/api/actors/" + actor.id;
                }).collect(Collectors.toList());
    }

    //getters and setters
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}

