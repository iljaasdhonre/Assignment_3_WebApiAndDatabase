package com.richieandmod.assignment_3_webapianddatabase.Services;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;

import java.util.List;

public interface MovieService {
    List<Actor> updateActorsInMovie(Integer movieId, Integer [] actorIds);
}
