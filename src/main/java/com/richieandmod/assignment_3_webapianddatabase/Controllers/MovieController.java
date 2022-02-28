package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    //Get all movies
    @GetMapping("/movie/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    //Get movie by id
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer id) {
        Movie returnMovie = new Movie();
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnMovie = movieRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMovie, status);
    }

    //Create movie and save to DB
    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        HttpStatus status;

        if (movie.id != null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(movie, status);
        }
        status = HttpStatus.OK;
        movie = movieRepository.save(movie);
        return new ResponseEntity<>(movie, status);
    }

    //Update movie and save to DB
    @PatchMapping("/movie/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie newMovie, @PathVariable Integer id) {

        Movie movie = new Movie();
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            Optional<Movie> movieRepo = movieRepository.findById(id);
            movie = movieRepo.get();

            if (newMovie.movieTitle != null) {
                movie.movieTitle = newMovie.movieTitle;
            }

            if (newMovie.genre != null) {
                movie.genre = newMovie.genre;
            }

            if (newMovie.releaseYear != null) {
                movie.releaseYear = newMovie.releaseYear;
            }

            movieRepository.save(movie);
            status = HttpStatus.OK;

        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(newMovie, status);
    }

    //Delete movie
    @DeleteMapping("/movie/{id}")
    public ResponseEntity<Movie> deleteBook(@PathVariable Integer id) {
        HttpStatus status;

        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
