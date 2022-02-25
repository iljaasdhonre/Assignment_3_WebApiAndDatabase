package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/movie")
    public Movie createMovie(@RequestBody Movie movie) {
        movie = movieRepository.save(movie);
        return movie;
    }

    @GetMapping("/movie/{id}")
    public Movie getmovie(@PathVariable Integer movieId) {
        Movie movie = null;
        if(movieRepository.existsById(movieId)) {
            movie = movieRepository.findById(movieId).get();
        }
        return movie;
    }
}
