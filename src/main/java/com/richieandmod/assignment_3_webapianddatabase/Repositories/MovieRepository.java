package com.richieandmod.assignment_3_webapianddatabase.Repositories;

import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    //check if movie exists by title
    boolean existsMovieByMovieTitle(String title);

    //get the first occurrence of movie by title
    Movie findFirstByMovieTitle(String title);
}
