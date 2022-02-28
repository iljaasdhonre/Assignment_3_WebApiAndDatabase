package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Models.Movie;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

    //Get all franchises
    @GetMapping("/all")
    public ResponseEntity<List<Franchise>> getAllFranchises() {
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises, status);
    }

    //Get franchise by id
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable Integer id) {
        Franchise returnFranchise = new Franchise();
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnFranchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnFranchise, status);
    }

    //Create franchise and save to DB
    @PostMapping("/")
    public ResponseEntity<Franchise> createFranchise(@RequestBody Franchise franchise) {
        HttpStatus status;
        Franchise returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    //Update existing franchise by id
    @PatchMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Integer id,
                                                     @RequestBody Franchise newFranchise) {
        Franchise franchise = new Franchise();
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            Optional<Franchise> franchiseRepo = franchiseRepository.findById(id);
            franchise = franchiseRepo.get();

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
            status = HttpStatus.OK;
            franchiseRepository.save(franchise);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(franchise, status);
    }

    //Delete franchise by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable Integer id) {
        HttpStatus status;

        if (franchiseRepository.existsById(id)) {
            franchiseRepository.deleteById(id);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(new Franchise(), status);
    }
}
