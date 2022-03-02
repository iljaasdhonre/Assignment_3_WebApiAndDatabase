package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import com.richieandmod.assignment_3_webapianddatabase.Services.MovieServiceImpl;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Command;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieServiceImpl movieServiceImpl;

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

    //Get all actors in a given movie by string
    @GetMapping("/{title}/characters")
    public ResponseEntity<CommonResponse> getAllCharactersInMovieByTitle(HttpServletRequest request,
                                                                         @PathVariable String title){
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<Actor> actorsInMovie = new ArrayList<>();

        if(movieRepository.existsByMovieTitle(title)){
            //actorsInMovie = movieRepository.findByTitle(title).getActors();
            commonResponse.data = actorsInMovie;
            commonResponse.message ="All actors starring in: " + title;
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

    //Update characters in movie
    @PutMapping("/{id}/characters/update/")
    public ResponseEntity<CommonResponse> updateCharactersInMovie(HttpServletRequest request,
                                                                  @PathVariable Integer id, @RequestBody Integer [] movieId){
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {commonResponse.data = movieServiceImpl.updateActorsInMovie(id, movieId);
            commonResponse.message = "Actors in movie with id: " + id + " have been updated";
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
    public ResponseEntity<CommonResponse> updateMovie(HttpServletRequest request, @RequestBody Movie movie,
                                                      @PathVariable Integer id) {

        Movie returnMovie;

        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (!id.equals(movie.id)) {
            resp = HttpStatus.BAD_REQUEST;
            commonResponse.message = "You can't update the movie id";
            return new ResponseEntity<>(commonResponse, resp);
        } else
        {
            //TODO: deze check is eigenlijk niet nodig
            if (movieRepository.existsById(id)) {
                Optional<Movie> movieRepo = movieRepository.findById(id);
                returnMovie = movieRepo.orElse(null);

                if (movie.movieTitle != null) {
                    returnMovie.movieTitle = movie.movieTitle;
                }

                if (movie.genre != null) {
                    returnMovie.genre = movie.genre;
                }

                if (movie.releaseYear != null) {
                    returnMovie.releaseYear = movie.releaseYear;
                }

               returnMovie = movieRepository.save(returnMovie);

                commonResponse.data = returnMovie;
                commonResponse.message = "Updated movie with id: " + returnMovie.id;
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
