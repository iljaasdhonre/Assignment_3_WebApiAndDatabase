package com.richieandmod.assignment_3_webapianddatabase.Services;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FranchiseServiceImpl implements FranchiseService {

    @Autowired
    public FranchiseRepository franchiseRepository;

    @Autowired
    public MovieRepository movieRepository;

    //Update movies in a franchise with given id. If the movie already exists in a franchise do nothing
    @Override
    public List<Movie> updateMoviesInFranchise(Integer franchiseId, Integer[] moviesIds) {
        Franchise franchise = franchiseRepository.getById(franchiseId);
        List<Movie> movies = franchise.getMovies();
        boolean contains;

        for (int id : moviesIds) {
            contains = movies.stream().anyMatch(movie -> movie.id == id);
            if (!contains) {
                if (movieRepository.existsById(id)) {
                    Optional<Movie> movie = movieRepository.findById(id);
                    movies.add(movie.orElse(null));
                }
            }
        }
        franchise.setMovies(movies);

        franchiseRepository.save(franchise);
        return franchise.getMovies();
    }

    @Override
    public List<String> getAllMoviesInFranchise(String name) {
        List<Movie> moviesInFranchise = franchiseRepository.findFirstByName(name).getMovies();
        List<String> movieTitles = new ArrayList<>();
        moviesInFranchise.stream().map(
                movie -> movieTitles.add(movie.movieTitle)).collect(Collectors.toList());

        return movieTitles;
    }

    @Override
    public List<String> getAllActorsInFranchise(String name) {
        List<Movie> moviesInFranchise = franchiseRepository.findFirstByName(name).getMovies();
        List<Actor> actorsInFranchise = new ArrayList<>();
        List<String> actorNames = new ArrayList<>();

        moviesInFranchise.stream().map(
                movie -> actorsInFranchise.addAll(movie.getActors())
        ).collect(Collectors.toList());

        actorsInFranchise.stream().distinct().map(
                        actor -> actorNames.add(actor.name))
                .collect(Collectors.toList());

        return actorNames;
    }
}
