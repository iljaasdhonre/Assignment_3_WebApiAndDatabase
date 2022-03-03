package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import com.richieandmod.assignment_3_webapianddatabase.Services.MovieServiceImpl;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Command;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Operation(summary = "Get all movies that are present in db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all movies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
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
    @Operation(summary = "Get a movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "One movie has been found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getMovieById(@Parameter(description = "id of the movie that needs to be searched")
                                                               HttpServletRequest request, @PathVariable Integer id) {
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

    //Get all actors in a given movie by its title
    @Operation(summary = "Get all actors in a movie by movie title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The following actors are starring in this movie",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))})
    })
    @GetMapping("/{title}/actors")
    public ResponseEntity<CommonResponse> getAllActorsInMovieByTitle(@Parameter(description = "title of the movie that needs to be searched")
                                                                                 HttpServletRequest request,
                                                                     @PathVariable String title) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<String> actorNames;

        if (movieRepository.existsMovieByMovieTitle(title)) {
            actorNames = movieServiceImpl.getAllActorsInMovie(title);
            commonResponse.data = actorNames;
            commonResponse.message = "All actors starring in: " + title;
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

    //Update actors in movie, double actors are skipped
    @Operation(summary = "Update the actors in a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The actors have been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))})
    })
    @PutMapping("/{id}/actors/update")
    public ResponseEntity<CommonResponse> updateActorsInMovie(@Parameter(description = "id of the movie that needs to be searched")HttpServletRequest request,
                                                              @PathVariable Integer id, @RequestBody Integer[] movieId) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            commonResponse.data = movieServiceImpl.updateActorsInMovie(id, movieId);
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
    @Operation(summary = "Create new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new movie",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))})
    })
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
    @Operation(summary = "Update the existing movie on id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The movie has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))})
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updateMovie(@Parameter(description = "id of the movie that needs to be updated")
                                                              HttpServletRequest request, @RequestBody Movie movie,
                                                      @PathVariable Integer id) {

        Movie returnMovie;

        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (!id.equals(movie.id)) {
            resp = HttpStatus.BAD_REQUEST;
            commonResponse.message = "You can't update the movie id";
            return new ResponseEntity<>(commonResponse, resp);
        } else {
            if (movieRepository.existsById(id)) {
                Optional<Movie> movieRepo = movieRepository.findById(id);
                returnMovie = movieRepo.get();

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
                commonResponse.data = null;
                commonResponse.message = "Movie with id " + id + " not found";
                resp = HttpStatus.NOT_FOUND;
            }
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Delete movie
    @Operation(summary = "Delete the movie on id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The movie has been deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Movie.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommonResponse.class))})
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> deleteMovie(@Parameter(description = "id of the movie that needs to be deleted")
                                                              HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
            commonResponse.message = "Deleted movie with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Movie with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }
}
