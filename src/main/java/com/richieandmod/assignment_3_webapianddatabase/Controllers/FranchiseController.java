package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

    //create franchise and save to DB
    @PostMapping("/franchise")
    public Franchise createFranchise(@RequestBody Franchise franchise) {
        franchise = franchiseRepository.save(franchise);
        return franchise;
    }

    //Get franchise by id
    @GetMapping("/franchise/{id}")
    public Franchise getFranchise(@PathVariable Integer id) {
        Franchise franchise = null;
        if(franchiseRepository.existsById(id)) {
            franchise = franchiseRepository.findById(id).get();
        }
        return franchise;
    }

    //Get all franchises
    @GetMapping("/franchise/all")
    public List<Franchise> getAllFranchises(){
        return franchiseRepository.findAll();
    }
}
