package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    //Get all movies
    @GetMapping("/movie/all")
    public ResponseEntity<Movie> getAllMovies(){
        movieRepository.findAll();

        HttpStatus status = HttpStatus.OK;

        return new ResponseEntity<>(status);
    }

    //Get movie by id
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Integer id) {

        HttpStatus status;

        if(movieRepository.existsById(id)) {
            movieRepository.findById(id).get();
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }

    //Create movie and save to DB
    @PostMapping("/movie")
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {

        if(movie.id != null) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(movie, status);
        }

        movie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movie, status);
    }

    //Update movie and save to DB
    @PatchMapping("/movie/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie newMovie, @PathVariable Integer id) {

        HttpStatus status;

        if(movieRepository.existsById(id)) {
            Optional<Movie> movieRepo = movieRepository.findById(id);
            Movie movie = movieRepo.get();

            if(newMovie.movieTitle != null) {
                movie.movieTitle = newMovie.movieTitle;
            }

            if(newMovie.genre != null) {
                movie.genre = newMovie.genre;
            }

            if(newMovie.releaseYear != null) {
                movie.releaseYear = newMovie.releaseYear;
            }

            movieRepository.save(movie);
            status = HttpStatus.OK;

        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(newMovie, status);
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<Movie> deleteBook(@PathVariable Integer id) {

        //process
        HttpStatus status;

        if(movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
}
