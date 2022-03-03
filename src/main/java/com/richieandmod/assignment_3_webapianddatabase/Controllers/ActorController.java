package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Actor;
import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.ActorRepository;
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
@RequestMapping("/api/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    //Get all actors
    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllActors(HttpServletRequest request) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = actorRepository.findAll();
        commonResponse.message = "All actors";

        HttpStatus resp = HttpStatus.OK;

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get actor by id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getActorById(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (actorRepository.existsById(id)) {
            commonResponse.data = actorRepository.findById(id);
            commonResponse.message = "Actor with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Actor not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }


    //Create actor and save in DB
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createActor(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestBody Actor actor) {
        Command cmd = new Command(request);

        actor = actorRepository.save(actor);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = actor;
        commonResponse.message = "New actor created, with id: " + actor.id;

        HttpStatus resp = HttpStatus.CREATED;

        response.addHeader("Location", "/actor/" + actor.id);

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Update existing actor
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updateActor(HttpServletRequest request, @PathVariable Integer id,
                                             @RequestBody Actor newActor) {

        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (actorRepository.existsById(id)) {
            Optional<Actor> actorRepo = actorRepository.findById(id);
            Actor actor = actorRepo.orElse(null);

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
            actorRepository.save(actor);

            commonResponse.data = actor;
            commonResponse.message = "Updated actor with id: " + actor.id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Actor with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Delete actor
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> deleteActor(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
            commonResponse.message = "Deleted actor with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Actor with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }



}

