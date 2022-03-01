package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Command;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    //Get all movies
    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllMovies(HttpServletRequest request) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = movieRepository.findAll();
        commonResponse.message = "All movies";

        HttpStatus resp = HttpStatus.OK;

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get movie by id
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getMovieById(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            commonResponse.data = movieRepository.findById(id);
            commonResponse.message = "Movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "movie not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Create movie and save to DB
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createMovie(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestBody Movie movie) {
        Command cmd = new Command(request);

        movie = movieRepository.save(movie);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = movie;
        commonResponse.message = "New movie created, with id: " + movie.id;

        HttpStatus resp = HttpStatus.CREATED;

        response.addHeader("Location", "/movie/" + movie.id);

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Update movie and save to DB
    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updateMovie(HttpServletRequest request, @RequestBody Movie newMovie,
                                                      @PathVariable Integer id) {

        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (!id.equals(movieRepository.getById(id))) {
            resp = HttpStatus.BAD_REQUEST;
            commonResponse.message = "You can't update de movie id";
            return new ResponseEntity<>(commonResponse, resp);

        } else {
            if (movieRepository.existsById(id)) {
                Optional<Movie> movieRepo = movieRepository.findById(id);
                Movie movie = movieRepo.get();

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

                commonResponse.data = movie;
                commonResponse.message = "Updated movie with id: " + movie.id;
                resp = HttpStatus.OK;
            } else {
                commonResponse.message = "Movie with id " + id + " not found";
                resp = HttpStatus.NOT_FOUND;
            }
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Delete movie
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> deleteMovie(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            commonResponse.message = "Deleted movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Movie with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }
}
