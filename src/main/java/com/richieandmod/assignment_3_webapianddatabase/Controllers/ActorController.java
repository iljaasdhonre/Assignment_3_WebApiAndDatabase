package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;


    //Get all actors
    @GetMapping("/all")
    public ResponseEntity<List<Actor>> getAllActors() {
        List<Actor> actors = actorRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(actors, status);
    }

    //Get actor by id
    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActor(@PathVariable Integer id) {
        Actor returnActor = new Actor();
        HttpStatus status;

        if (actorRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnActor = actorRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnActor, status);
    }


    //Create actor and save in DB
    @PostMapping("/")
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        HttpStatus status;

        if (actor.id != null) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(actor, status);
        }
        status = HttpStatus.OK;
        actor = actorRepository.save(actor);
        return new ResponseEntity<>(actor, status);
    }

    //Update existing actor
    @PatchMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Integer id,
                                             @RequestBody Actor newActor) {
        Actor actor = new Actor();
        HttpStatus status;

        if (actorRepository.existsById(id)) {
            Optional<Actor> actorRepo = actorRepository.findById(id);
            actor = actorRepo.get();

            if (newActor.name != null) {
                actor.name = newActor.name;
            }
            if (newActor.alias != null) {
                actor.alias = newActor.alias;
            }
            if (newActor.gender != null) {
                actor.gender = newActor.gender;
            }
            if (newActor.picture != null) {
                actor.picture = newActor.picture;
            }
            if (newActor.movies != null && !newActor.movies.isEmpty()) {
                for (Movie movie : newActor.movies) {
                    actor.movies.add(movie);
                }
            }
            status = HttpStatus.OK;
            actorRepository.save(actor);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(actor, status);
    }

    //Delete actor
    @DeleteMapping("/{id}")
    public ResponseEntity<Actor> deleteActor(@PathVariable Integer id) {
        HttpStatus status;

        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);

    }

}

