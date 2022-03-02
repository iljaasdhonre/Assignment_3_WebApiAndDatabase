package com.richieandmod.assignment_3_webapianddatabase.Services;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.ActorRepository;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    //Update actors in a movie with given id. If actors already exist in movie do nothing
    @Override
    public List<Actor> updateActorsInMovie(Integer movieId, Integer[] actorIds) {
        Movie movie = movieRepository.getById(movieId);
        List<Actor> actors = movie.getActors();
        boolean contains;

        for (int id : actorIds) {
            contains = actors.stream().anyMatch(actor -> actor.id == id);
            if (!contains) {
                if (actorRepository.existsById(id)) {
                    Optional<Actor> actor = actorRepository.findById(id);
                    actors.add(actor.orElse(null));
                }
            }
        }
        movie.setActors(actors);

        movieRepository.save(movie);
        return movie.getActors();
    }

    @Override
    public List<String> getAllActorsInMovie(String movieTitle) {
        List<Actor> actorsInMovie;
        List<String> actorNames = new ArrayList<>();
        Movie movie = movieRepository.findFirstByMovieTitle(movieTitle);

        actorsInMovie = movie.getActors();
        actorsInMovie.stream().map(
                actor -> actorNames.add(actor.name)).collect(Collectors.toList());

        return actorNames;
    }
}
