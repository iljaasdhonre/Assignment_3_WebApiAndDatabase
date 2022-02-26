package com.richieandmod.assignment_3_webapianddatabase.Controllers;


import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    //create actor and save in DB
    @PostMapping("/actor")
    public Actor createActor(@RequestBody Actor actor) {
        actor = actorRepository.save(actor);
        return actor;
    }

    //Get actor by id
    @GetMapping("/actor/{id}")
    public Actor getActor(@PathVariable Integer id) {
        Actor actor = null;
        if(actorRepository.existsById(id)) {
            actor = actorRepository.findById(id).get();
        }
        return actor;
    }

    //Get all actors
    @GetMapping("/actor/all")
    public List<Actor> getAllActors(){
        return actorRepository.findAll();
    }
}

