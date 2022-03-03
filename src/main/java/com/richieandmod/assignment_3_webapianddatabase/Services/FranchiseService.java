package com.richieandmod.assignment_3_webapianddatabase.Services;

import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;

import java.util.List;

public interface FranchiseService {
    List<Movie> updateMoviesInFranchise(Integer franchiseId, Integer [] movieIds);
    List<String> getAllMoviesInFranchise(String name);
    List<String> getAllActorsInFranchise(String name);
}
