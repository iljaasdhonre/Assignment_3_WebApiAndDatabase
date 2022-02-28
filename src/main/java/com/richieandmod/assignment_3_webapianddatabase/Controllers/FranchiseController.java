package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
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
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

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
    @GetMapping("/byId/{id}")
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

    //Create franchise and save to DB
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
                for (Movie movie : newFranchise.movies) {
                    franchise.movies.add(movie);
                }
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
