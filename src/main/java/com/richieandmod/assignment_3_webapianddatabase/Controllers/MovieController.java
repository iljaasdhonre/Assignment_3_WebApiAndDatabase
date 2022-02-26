package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    //Create movie and save to DB
    @PostMapping("/movie")
    public Movie createMovie(@RequestBody Movie movie) {
        movie = movieRepository.save(movie);
        return movie;
    }

    //Get movie by id
    @GetMapping("/movie/{id}")
    public Movie getMovie(@PathVariable Integer id) {
        Movie movie = null;
        if(movieRepository.existsById(id)) {
            movie = movieRepository.findById(id).get();
        }
        return movie;
    }

    //Get all movies
    @GetMapping("/movie/all")
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
}
