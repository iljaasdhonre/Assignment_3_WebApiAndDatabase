package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import com.richieandmod.assignment_3_webapianddatabase.Services.FranchiseServiceImpl;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Command;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    private FranchiseServiceImpl franchiseServiceImpl;

    //Get all franchises
    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllFranchises(HttpServletRequest request) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = franchiseRepository.findAll();
        commonResponse.message = "All franchises";

        HttpStatus resp = HttpStatus.OK;

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get franchise by id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getFranchiseById(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            commonResponse.data = franchiseRepository.findById(id);
            commonResponse.message = "Franchise with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get all movies in a given franchise by its name
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{name}/movies")
    public ResponseEntity<CommonResponse> getAllMoviesInFranchiseByTitle(HttpServletRequest request,
                                                                         @PathVariable String name) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<String> movieNames;

        if (franchiseRepository.existsFranchiseByName(name)) {
            movieNames = franchiseServiceImpl.getAllMoviesInFranchise(name);
            commonResponse.data = movieNames;
            commonResponse.message = "All movies published by franchise: " + name;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get all actors in a given franchise by its name
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/{name}/actors/all")
    public ResponseEntity<CommonResponse> getAllActorsInFranchise(HttpServletRequest request,
                                                                  @PathVariable String name) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<String> actorsInFranchise;

        if (franchiseRepository.existsFranchiseByName(name)) {
            actorsInFranchise = franchiseServiceImpl.getAllActorsInFranchise(name);
            commonResponse.data = actorsInFranchise;
            commonResponse.message = "All actors active at franchise: " + name;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }


    //Update movies in franchise by its id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/{id}/movies/update/")
    public ResponseEntity<CommonResponse> updateMoviesInFranchise(HttpServletRequest request,
                                                                  @PathVariable Integer id, @RequestBody Integer[] franchiseId) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            commonResponse.data = franchiseServiceImpl.updateMoviesInFranchise(id, franchiseId);
            commonResponse.message = "movies in franchise with id: " + id + " have been updated";
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

    //Create franchise and save to DB
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createFranchise(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestBody Franchise franchise) {
        Command cmd = new Command(request);

        franchise = franchiseRepository.save(franchise);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = franchise;
        commonResponse.message = "New franchises created, with id: " + franchise.id;

        HttpStatus resp = HttpStatus.CREATED;

        response.addHeader("Location", "/franchises/" + franchise.id);

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Update existing franchise by id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updateFranchise(HttpServletRequest request, @PathVariable Integer id,
                                                          @RequestBody Franchise newFranchise) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            Optional<Franchise> franchiseRepo = franchiseRepository.findById(id);
            Franchise franchise = franchiseRepo.get();

            if (newFranchise.name != null) {
                franchise.name = newFranchise.name;
            }
            if (newFranchise.description != null) {
                franchise.description = newFranchise.description;
            }
            if (newFranchise.movies != null && !newFranchise.movies.isEmpty()) {
                franchise.movies.addAll(newFranchise.movies);
            }
            franchiseRepository.save(franchise);

            commonResponse.data = franchise;
            commonResponse.message = "Updated franchise with id: " + franchise.id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Franchise with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Delete franchise by id
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> deleteFranchise(HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            franchiseRepository.deleteById(id);
            commonResponse.message = "Deleted franchise with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Franchise with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }
}
