package com.richieandmod.assignment_3_webapianddatabase.Services;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.ActorRepository;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public ActorRepository actorRepository;

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

}